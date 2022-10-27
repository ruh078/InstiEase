package com.dbms.insti.models;
import java.sql.Date;
public class Laundary_orders {
	private int order_id;
	private int cost;
	private Date order_date;
	private int number_uppers;
	private int number_sheets;
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getNumber_uppers() {
		return number_uppers;
	}
	public void setNumber_uppers(int number_uppers) {
		this.number_uppers = number_uppers;
	}
	public int getNumber_sheets() {
		return number_sheets;
	}
	public void setNumber_sheets(int number_sheets) {
		this.number_sheets = number_sheets;
	}
	public int getNumber_lowers() {
		return number_lowers;
	}
	public void setNumber_lowers(int number_lowers) {
		this.number_lowers = number_lowers;
	}
	public int getTo_wash() {
		return to_wash;
	}
	public void setTo_wash(int to_wash) {
		this.to_wash = to_wash;
	}
	public int getTo_iron() {
		return to_iron;
	}
	public void setTo_iron(int to_iron) {
		this.to_iron = to_iron;
	}
	public String getStatus_of_orders() {
		return status_of_orders;
	}
	public void setStatus_of_orders(String status_of_orders) {
		this.status_of_orders = status_of_orders;
	}
	public int getStudent_roll_no() {
		return student_roll_no;
	}
	public void setStudent_roll_no(int student_roll_no) {
		this.student_roll_no = student_roll_no;
	}
	public int getWasher_id() {
		return washer_id;
	}
	public void setWasher_id(int washer_id) {
		this.washer_id = washer_id;
	}
	private int number_lowers;
	private int to_wash;
	private int to_iron;
	private String status_of_orders;
	private int student_roll_no;
	private int washer_id;
}
