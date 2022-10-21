package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.WardenDao;
import com.dbms.insti.models.Warden;

@Service
public class WardenServiceUtil implements WardenService {
    @Autowired
    WardenDao wardendao;
    @Override
    public List<Warden> listAllWardens() {
        return wardendao.listAllWardens();
    }

    @Override
    public void save(Warden warden) {
        wardendao.save(warden);
    }

    @Override
    public Warden findbyHostelId(int hostel_id) {
        return wardendao.findbyHostelId(hostel_id);
    }
}