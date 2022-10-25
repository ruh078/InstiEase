package com.dbms.insti.models;

import java.sql.Date;

public class Complaints {
	private int complaint_id;
	private int student_roll_no;
	private String description;
	private int type;
	private int isPrivate;
	private String status;
	private Date complain_date;
	public int getComplaint_id() {
		return complaint_id;
	}
	public Date getComplain_date() {
        return complain_date;
    }
    public void setComplain_date(Date complain_date) {
        this.complain_date = complain_date;
    }
    public void setComplaint_id(int complaint_id) {
		this.complaint_id = complaint_id;
	}
	public int getStudent_roll_no() {
		return student_roll_no;
	}
	public void setStudent_roll_no(int student_roll_no) {
		this.student_roll_no = student_roll_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
