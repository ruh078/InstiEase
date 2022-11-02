package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.UserDao;
import com.dbms.insti.dao.WardenDao;
import com.dbms.insti.models.Warden;

@Service
public class WardenServiceUtil implements WardenService {
    @Autowired
    WardenDao wardendao;
    @Autowired
    UserDao userdao;
    
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
    @Override
    public Warden findbyUserId(int user_id) {
        return wardendao.findbyUserId(user_id);
    }

	@Override
	public int delete(int warden_id) {
		// TODO Auto-generated method stub
		int user_id = wardendao.findbyWardenId(warden_id).getUser_id();
		wardendao.delete(warden_id);
		try {
			userdao.delete(user_id);
			return 1;
		}
		catch(Exception e) {
			return 0;
		}
	}
}