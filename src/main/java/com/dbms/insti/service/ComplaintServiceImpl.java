package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.ComplaintsDao;
import com.dbms.insti.models.Complaints;

@Service
public class ComplaintServiceImpl implements ComplaintService{
    @Autowired
    ComplaintsDao complaintsdao;
    
    @Override
    public List<Complaints> listAllComplaints(int hostel_id) {
        return complaintsdao.listAllComplaints(hostel_id);
    }
    
    @Override
    public List<Complaints> listComplaintsofStudent(int roll_number){
        return complaintsdao.listComplaintsofStudent(roll_number);
    }
    @Override
    public List<Complaints> listResolvedComplaints(int hostel_id) {
        return complaintsdao.listResolvedComplaints(hostel_id);
    }

    @Override
    public List<Complaints> listApprovedandUnresolvedComplaints(int hostel_id) {
        return complaintsdao.listApprovedandUnresolvedComplaints(hostel_id);
    }

    @Override
    public List<Complaints> listUnapprovedComplaints(int hostel_id) {
        return complaintsdao.listUnapprovedComplaints(hostel_id);
    }

    @Override
    public List<Complaints> listPublicComplaints(int hostel_id) {
        return complaintsdao.listPublicComplaints(hostel_id);
    }

    @Override
    public List<Complaints> listPrivateComplaints(int hostel_id) {
        return complaintsdao.listPrivateComplaints(hostel_id);
    }

    @Override
    public void save(Complaints complaints) {
        complaintsdao.save(complaints);
        
    }
    

}
