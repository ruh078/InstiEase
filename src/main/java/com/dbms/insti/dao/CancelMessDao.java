package com.dbms.insti.dao;

import java.sql.Date;
import java.util.List;

import com.dbms.insti.models.Cancel_mess;

public interface CancelMessDao {
	public void save(Cancel_mess cancel_mess);
	public void edit(Cancel_mess cancel_mess);
	public void delete(int request_id);
	public List<Cancel_mess> CancellationofStudent(int roll_number);
	public List<Cancel_mess> CancellationofHostel(int hostel_id);
	public Cancel_mess getRequest(int roll_number, Date cancel_date);
}
