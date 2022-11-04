package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Washerman;

public interface WashermanService {
    public List<Washerman> listAllWasherman();
    public void save(Washerman washerman);
    public void edit(Washerman washerman);
    public Washerman findByWasherId(int washer_id);
    public Washerman findByUserId(int user_id);
    public List<Washerman>listAllWashermanofHostel(int hostel_id);
    public int delete(int washer_id);
    public int edithostel(int washer_id, int hostel_id);
}
