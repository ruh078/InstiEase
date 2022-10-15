package com.dbms.insti.models;
import java.sql.Date;
public class Users {
	private int user_id;
	private String email_id;
	private String psw;
	private int role;
	private String name;
	private String contact;
	private Date dob;
	private String gender;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date date) {
		this.dob = date;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
	