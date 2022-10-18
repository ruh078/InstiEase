package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Hostel;

public interface HostelDao {
    public List<Hostel> listAllHostels();
    public void save(Hostel hostel);
    public Hostel getHostelbyId(int hostel_id);
}
