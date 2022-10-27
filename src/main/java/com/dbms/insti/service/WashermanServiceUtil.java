package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.WashermanDao;
import com.dbms.insti.models.Washerman;

@Service
public class WashermanServiceUtil implements WashermanService {
    @Autowired
    WashermanDao washermandao;
    @Override
    public List<Washerman> listAllWasherman() {
        return washermandao.listAllWasherman();
    }

    @Override
    public void save(Washerman washerman) {
        washermandao.save(washerman);
    }
    
    @Override
    public void edit(Washerman washerman) {
       washermandao.edit(washerman);
    }
    
    @Override
    public Washerman findByWasherId(int washer_id) {
        return washermandao.findByWasherId(washer_id);
    }
    
    @Override
	public List<Washerman> listAllWashermanofHostel(int hostel_id) {
		return washermandao.listAllWashermanofHostel(hostel_id);
	}

}