package com.dbms.insti.models;
import java.sql.Date;
public class Appointment {
	private int appointment_id;
	private int student_roll_no;
	private Date appointment_date;
	private int slot;
	private String problem;
	public int getAppointment_id() {
		return appointment_id;
	}
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}
	public int getStudent_roll_no() {
		return student_roll_no;
	}
	public void setStudent_roll_no(int student_roll_no) {
		this.student_roll_no = student_roll_no;
	}
	public Date getAppointment_date() {
		return appointment_date;
	}
	public void setAppointment_date(Date appointment_date) {
		this.appointment_date = appointment_date;
	}
	public int getSlot() {
		return slot;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
}
