package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Warden;

public interface WardenDao {
    public List<Warden> listAllWardens();
    public void save(Warden warden);
}