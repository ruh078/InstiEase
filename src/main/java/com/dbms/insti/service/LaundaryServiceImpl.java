package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.LaundaryDao;
import com.dbms.insti.dao.StudentDao;
import com.dbms.insti.dao.WashermanDao;
import com.dbms.insti.models.Laundary_orders;
import com.dbms.insti.models.Student;
import com.dbms.insti.models.Washerman;
@Service
public class LaundaryServiceImpl implements LaundaryService{
	@Autowired
	LaundaryDao laundarydao;
	@Autowired
	WashermanDao washermandao;
	@Autowired
	StudentDao studentdao;
	
	@Override
	public List<Laundary_orders> listOrdersofStudent(int roll_number, int washer_id, int status) {
		return laundarydao.listOrdersofStudent(roll_number, washer_id, status);
	}

	@Override
	public List<Laundary_orders> listAllOrdersofStudent(int roll_number, int status) {
		return laundarydao.listAllOrdersofStudent(roll_number, status);
	}

	@Override
	public void save(Laundary_orders order) {
		if(order.getTo_iron()==0 && order.getTo_wash()==0)
			return;
		if(order.getNumber_lowers()==0 && order.getNumber_uppers()==0 && order.getNumber_sheets()==0)
			return;
		Washerman w = washermandao.findByWasherId(order.getWasher_id());
		int cost = order.getTo_wash() * (order.getNumber_lowers()*w.getCost_lower_wash() + order.getNumber_uppers()*w.getCost_upper_wash() + order.getNumber_sheets()*w.getCost_sheet_wash());
		cost += order.getTo_iron() * (order.getNumber_lowers()*w.getCost_lower_iron() + order.getNumber_uppers()*w.getCost_upper_iron() + order.getNumber_sheets()*w.getCost_sheet_iron());
		order.setCost(cost);
		laundarydao.save(order);
		Student s = studentdao.getStudentbyId(order.getStudent_roll_no());
		studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() + cost);
	}

}
