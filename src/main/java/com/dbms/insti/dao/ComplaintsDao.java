package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Complaints;

public interface ComplaintsDao {
	public List<Complaints> listAllComplaints(int hostel_id);
	public List<Complaints> listResolvedComplaints(int hostel_id);
	public List<Complaints> listApprovedandUnresolvedComplaints(int hostel_id);
	public List<Complaints> listUnapprovedComplaints(int hostel_id);
	public List<Complaints> listPublicComplaints(int hostel_id);
	public List<Complaints> listPrivateComplaints(int hostel_id);
	public  List<Complaints> listComplaintsofStudent(int roll_number);
	public List<Complaints> listComplaintsofType(int hostel_id, int type);
	public List<Complaints> listComplaintsofTypeforall(int type);
	public void  edit(Complaints complaints);
	public void save(Complaints complaints);
}
