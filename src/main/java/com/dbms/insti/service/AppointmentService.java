package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Appointment;

public interface AppointmentService {
 	public List<Appointment> listCurrentAppointments();
 	public List<Appointment> listPreviousAppointments();
 	public List<Appointment> listAppointmentsStudent(int student_roll_no);
    public void save(Appointment appointment);
    public Appointment getAppointmentbyId(int appointment_id);
    public void updateAppointmentDesc(int appointment_id, String desc);
}
