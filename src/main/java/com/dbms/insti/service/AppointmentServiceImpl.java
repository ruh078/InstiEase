package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.AppointmentDao;
import com.dbms.insti.models.Appointment;
@Service
public class AppointmentServiceImpl implements AppointmentService{
	@Autowired
	AppointmentDao appointmentdao;
	
	@Override
	public List<Appointment> listCurrentAppointments() {
		return appointmentdao.listCurrentAppointments();
	}

	@Override
	public List<Appointment> listPreviousAppointments() {
		return appointmentdao.listPreviousAppointments();
	}

	@Override
	public List<Appointment> listAppointmentsStudent(int student_roll_no) {
		return appointmentdao.listAppointmentsStudent(student_roll_no);
	}

	@Override
	public void save(Appointment appointment) {
		appointmentdao.save(appointment);
	}

	@Override
	public Appointment getAppointmentbyId(int appointment_id) {
		return appointmentdao.getAppointmentbyId(appointment_id);
	}

	@Override
	public void updateAppointmentDesc(int appointment_id, String desc) {
		appointmentdao.updateAppointmentDesc(appointment_id, desc);
		
	}

}
