package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Hostel;

public interface HostelService {
    public List<Hostel> listAllHostels();
    public void save(Hostel hostel);
    public Hostel getHostelbyId(int hostel_id);
}
