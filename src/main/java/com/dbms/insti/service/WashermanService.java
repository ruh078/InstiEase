package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Washerman;

public interface WashermanService {
    public List<Washerman> listAllWasherman();
    public void save(Washerman washerman);
    public void edit(Washerman washerman);
    public Washerman findByWasherId(int washer_id);
    public List<Washerman>listAllWashermanofHostel(int hostel_id);
}
