package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Complaints;

public interface ComplaintService {
    public List<Complaints> listAllComplaints(int hostel_id);
    public List<Complaints> listResolvedComplaints(int hostel_id);
    public List<Complaints> listApprovedandUnresolvedComplaints(int hostel_id);
    public List<Complaints> listUnapprovedComplaints(int hostel_id);
    public List<Complaints> listPublicComplaints(int hostel_id);
    public List<Complaints> listPrivateComplaints(int hostel_id);
    public List<Complaints> listComplaintsofStudent(int roll_number);
    public void save(Complaints complaints);
}
