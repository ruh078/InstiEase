package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Prescription;

public interface PrescriptionService {
	public void save(Prescription prescription);
	public void update(Prescription prescription);
	public void delete(int appointment_id, int med_id);
	public List<Prescription> getPrescriptionsOfAppointment(int appointment_id);
}
