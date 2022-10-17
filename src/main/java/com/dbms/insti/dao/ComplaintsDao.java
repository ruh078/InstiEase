package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Complaints;

public interface ComplaintsDao {
	public List<Complaints> listAllComplaints();
	public void save(Complaints complaints);
}
