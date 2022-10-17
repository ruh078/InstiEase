package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Washerman;

public interface WashermanDao {
    public List<Washerman> listAllWasherman();
    public void save(Washerman washerman);
}