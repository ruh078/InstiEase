package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.DayMenuDao;
import com.dbms.insti.dao.Mess_inchargeDao;
import com.dbms.insti.dao.UserDao;
import com.dbms.insti.models.Mess_incharge;
@Service
public class MessServiceUtil implements MessService{
	@Autowired
	Mess_inchargeDao mess_inchargedao;
	@Autowired
    UserDao userdao;
	@Autowired
	DayMenuDao daymenudao;

	@Override
	public void save(Mess_incharge mess) {
		mess_inchargedao.save(mess);
	}

	@Override
	public List<Mess_incharge> listAllMesses() {
		return mess_inchargedao.listAllMesses();
	}

	@Override
	public Mess_incharge findbyhostelid(int hostel_id) {
		return mess_inchargedao.findbyhostelid(hostel_id);
	}

	@Override
	public Mess_incharge findbyuserid(int user_id) {
		return mess_inchargedao.findbyuserid(user_id);
	}

	@Override
	public int delete(int mess_id) {
		// TODO Auto-generated method stub
		int user_id = mess_inchargedao.findbymessid(mess_id).getUser_id();
		daymenudao.delete(mess_id);
		try {
			mess_inchargedao.delete(mess_id);
			userdao.delete(user_id);
			return 1;
		}
		catch(Exception e) {
			return 0;
		}
	}

}
