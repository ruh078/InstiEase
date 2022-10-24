package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.Mess_inchargeDao;
import com.dbms.insti.models.Mess_incharge;
@Service
public class MessServiceUtil implements MessService{
	@Autowired
	Mess_inchargeDao mess_inchargedao;

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

}
