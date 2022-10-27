package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Washerman;

public interface WashermanDao {
    public List<Washerman> listAllWasherman();
    public void save(Washerman washerman);
    public void edit(Washerman washerman);
    public Washerman findByWasherId(int washer_id);
}