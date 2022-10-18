package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.MedicineDao;
import com.dbms.insti.models.Medicine;
@Service
public class MedicineServiceUtil implements MedicineService{
	@Autowired
	MedicineDao medicinedao;
	@Override
	public List<Medicine> listAllMedicine() {
		return medicinedao.listAllMedicine();
	}

	@Override
	public void save(Medicine medicine) {
		medicinedao.save(medicine);
	}

	@Override
	public Medicine getMedicinebyId(int medicine_id) {
		return medicinedao.getMedicinebyId(medicine_id);
	}

}
