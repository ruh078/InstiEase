package com.dbms.insti.models;
import java.sql.Date;
public class Cancel_mess {
	private int request_id;
	private Date cancel_date;
	private int is_breakfast;
	private int is_lunch;
	private int is_dinner;
	private int student_roll_no;
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public Date getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(Date cancel_date) {
		this.cancel_date = cancel_date;
	}
	public int getIs_breakfast() {
		return is_breakfast;
	}
	public void setIs_breakfast(int is_breakfast) {
		this.is_breakfast = is_breakfast;
	}
	public int getIs_lunch() {
		return is_lunch;
	}
	public void setIs_lunch(int is_lunch) {
		this.is_lunch = is_lunch;
	}
	public int getIs_dinner() {
		return is_dinner;
	}
	public void setIs_dinner(int is_dinner) {
		this.is_dinner = is_dinner;
	}
	public int getStudent_roll_no() {
		return student_roll_no;
	}
	public void setStudent_roll_no(int student_roll_no) {
		this.student_roll_no = student_roll_no;
	}
	
}
