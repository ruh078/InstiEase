package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.LaundaryDao;
import com.dbms.insti.dao.UserDao;
import com.dbms.insti.dao.WashermanDao;
import com.dbms.insti.models.Laundary_orders;
import com.dbms.insti.models.Washerman;

@Service
public class WashermanServiceUtil implements WashermanService {
    @Autowired
    WashermanDao washermandao;
    @Autowired
    UserDao userdao;
    @Autowired
	LaundaryDao laundarydao;
    
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

	@Override
	public Washerman findByUserId(int user_id) {
		return washermandao.findByUserId(user_id);
	}

	@Override
	public int delete(int washer_id) {
		int user_id = washermandao.findByWasherId(washer_id).getUser_id();
		List<Laundary_orders>l1 = laundarydao.listOrdersofWasherman(washer_id, 1);
		List<Laundary_orders>l2 = laundarydao.listOrdersofWasherman(washer_id, 2);
		if(l1.isEmpty()&&l2.isEmpty()) {
			try {
				washermandao.delete(washer_id);
				userdao.delete(user_id);
				return 1;
			}
			catch(Exception e) {
				return 0;
			}
		}
		else
			return 0;
	}

}