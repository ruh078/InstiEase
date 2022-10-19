package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Warden;

public interface WardenService {
    public List<Warden> listAllWardens();
    public void save(Warden warden);
    public Warden findbyHostelId(int hostel_id);
}
