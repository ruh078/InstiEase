package com.dbms.insti.dao;

import java.util.List;

import com.dbms.insti.models.Warden;

public interface WardenDao {
    public List<Warden> listAllWardens();
    public void save(Warden warden);
    public Warden findbyHostelId(int hostel_id);
    public Warden findbyUserId(int user_id);
}