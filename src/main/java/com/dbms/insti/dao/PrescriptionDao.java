package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Prescription;

public interface PrescriptionDao {
	public void save(Prescription prescription);
	public List<Prescription> getPrescriptionsOfAppointment(int appointment_id);
	public Prescription findPrescription(int appointment_id, int med_id);
	public void update(Prescription prescription);
	public int delete(int appointment_id, int med_id);
}
