package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Appointment;

public interface AppointmentService {
 	public List<Appointment> listCurrentAppointments();
 	public List<Appointment> listPreviousAppointments();
 	public List<Appointment> listCurrentAppointmentsStudent(int roll_number);
 	public List<Appointment> listPreviousAppointmentsStudent(int roll_number);
 	public List<Appointment> listAppointmentsStudent(int student_roll_no);
    public int save(Appointment appointment);
    public int edit(Appointment appointment);
    public int delete(int appointment_id);
    public Appointment getAppointmentbyId(int appointment_id);
    public void updateAppointmentDesc(int appointment_id, String desc);
}
