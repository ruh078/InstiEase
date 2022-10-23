package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.MedicineDao;
import com.dbms.insti.dao.PrescriptionDao;
import com.dbms.insti.models.Medicine;
import com.dbms.insti.models.Prescription;
@Service
public class PrescriptionServiceUtil implements PrescriptionService{
	@Autowired
	PrescriptionDao prescriptiondao;
	@Autowired
	MedicineDao medicinedao;
	
	@Override
	public void save(Prescription prescription) {
		int x = prescription.getMorning() + prescription.getAfternoon() + prescription.getNight();
		x = x*prescription.getNumber_of_days();
		Medicine m = medicinedao.getMedicinebyId(prescription.getMed_id());
		if(x<=m.getQuantity_in_stock()) {
			prescription.setTotal_quantity_to_purchase(0);
			medicinedao.updateStock(m.getMedicine_id(), m.getQuantity_in_stock()-x);
		}
		else {
			prescription.setTotal_quantity_to_purchase(x-m.getQuantity_in_stock());
			medicinedao.updateStock(m.getMedicine_id(), 0);
		}
		prescriptiondao.save(prescription);
	}

	@Override
	public List<Prescription> getPrescriptionsOfAppointment(int appointment_id) {
		return prescriptiondao.getPrescriptionsOfAppointment(appointment_id);
	}

	@Override
	public void update(Prescription prescription) {
		Prescription p = prescriptiondao.findPrescription(prescription.getAppointment_id(), prescription.getMed_id());
		int x = prescription.getMorning() + prescription.getAfternoon() + prescription.getNight();
		x = x*prescription.getNumber_of_days();
		int y = p.getMorning() + p.getAfternoon() + p.getNight();
		y = y*p.getNumber_of_days();
		Medicine m = medicinedao.getMedicinebyId(prescription.getMed_id());
		
		if(x<y) {
			int d = y-x;
			if(p.getTotal_quantity_to_purchase()==0) {
				prescription.setTotal_quantity_to_purchase(0);
				medicinedao.updateStock(m.getMedicine_id(), m.getQuantity_in_stock()+d);
			}
			else {
				if(p.getTotal_quantity_to_purchase()>=d) {
					prescription.setTotal_quantity_to_purchase(p.getTotal_quantity_to_purchase()-d);
				}
				else {
					prescription.setTotal_quantity_to_purchase(0);
					medicinedao.updateStock(m.getMedicine_id(), m.getQuantity_in_stock()+d-p.getTotal_quantity_to_purchase());
				}
			}
		}
		else if(x>y) {
			int d = x-y;
			if(d<=m.getQuantity_in_stock()) {
				prescription.setTotal_quantity_to_purchase(p.getTotal_quantity_to_purchase());
				medicinedao.updateStock(m.getMedicine_id(), m.getQuantity_in_stock()-d);
			}
			else {
				prescription.setTotal_quantity_to_purchase(p.getTotal_quantity_to_purchase() + d - m.getQuantity_in_stock());
				medicinedao.updateStock(m.getMedicine_id(), 0);
			}
		}
		
		prescriptiondao.update(prescription);
		
		
	}

	@Override
	public void delete(int appointment_id, int med_id) {
		Prescription p = prescriptiondao.findPrescription(appointment_id, med_id);
		Medicine m = medicinedao.getMedicinebyId(p.getMed_id());

		int y = p.getMorning() + p.getAfternoon() + p.getNight();
		y = y*p.getNumber_of_days();
		int f = prescriptiondao.delete(appointment_id, med_id);
		if(f==0)
			return;
		if(p.getTotal_quantity_to_purchase()==0) {
			medicinedao.updateStock(m.getMedicine_id(), m.getQuantity_in_stock()+y);
		}
		else {
			if(p.getTotal_quantity_to_purchase()<y) {
				medicinedao.updateStock(m.getMedicine_id(), m.getQuantity_in_stock()+y-p.getTotal_quantity_to_purchase());
			}
			
		}
	}

}
