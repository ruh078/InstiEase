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
		//Student s = studentdao.getStudentbyId(order.getStudent_roll_no());
		//studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() + cost);
	}

	@Override
	public int delete(int order_id) {
		Laundary_orders order= laundarydao.getByOrderId(order_id);
		if(order.getStatus_of_orders().equals("Unwashed")) {
			Student s = studentdao.getStudentbyId(order.getStudent_roll_no());
			int cost = order.getCost();
			laundarydao.delete(order_id);
		//	studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() - cost);
			return 1;
		}
		return 0;
	}

	@Override
	public Laundary_orders getByOrderId(int order_id) {
		return laundarydao.getByOrderId(order_id);
	}

	@Override
	public int edit(Laundary_orders order) {
		Laundary_orders prevorder= laundarydao.getByOrderId(order.getOrder_id());
		if(order.getTo_iron()==0 && order.getTo_wash()==0)
			return 0;
		if(order.getNumber_lowers()==0 && order.getNumber_uppers()==0 && order.getNumber_sheets()==0)
			return 0;
		if(prevorder.getStatus_of_orders().equals("Unwashed")) {
			
			Student s = studentdao.getStudentbyId(prevorder.getStudent_roll_no());
			Washerman w = washermandao.findByWasherId(prevorder.getWasher_id());
			//int prevcost = prevorder.getCost();
			int cost = order.getTo_wash() * (order.getNumber_lowers()*w.getCost_lower_wash() + order.getNumber_uppers()*w.getCost_upper_wash() + order.getNumber_sheets()*w.getCost_sheet_wash());
			cost += order.getTo_iron() * (order.getNumber_lowers()*w.getCost_lower_iron() + order.getNumber_uppers()*w.getCost_upper_iron() + order.getNumber_sheets()*w.getCost_sheet_iron());
			order.setCost(cost);
			laundarydao.edit(order);
		//	studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() - prevcost + cost);
			return 1;
		}
		return 0;
	}

	@Override
	public int duecharges(int roll_number, int washer_id) {
		return laundarydao.duecharges(roll_number, washer_id);
	}

	@Override
	public List<Laundary_orders> listOrdersofWasherman(int washer_id, int status) {
		return laundarydao.listOrdersofWasherman(washer_id, status);
	}

	@Override
	public void editstatus(Laundary_orders order) {
		Laundary_orders prev = laundarydao.getByOrderId(order.getOrder_id());
		Student s = studentdao.getStudentbyId(prev.getStudent_roll_no());
		Washerman w = washermandao.findByWasherId(prev.getWasher_id());
		if(prev.getStatus_of_orders().equals("Unwashed")) {
			if(order.getStatus_of_orders().equals("Washed and Unpaid")) {
				studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() + prev.getCost());
			}
			else if(order.getStatus_of_orders().equals("Washed and Paid")) {
				washermandao.editearning(prev.getWasher_id(), w.getTotal_money_earned()+prev.getCost());
			}
		}
		else if(prev.getStatus_of_orders().equals("Washed and Unpaid")) {
			if(order.getStatus_of_orders().equals("Unwashed")) {
				studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() - prev.getCost());
			}
			else if(order.getStatus_of_orders().equals("Washed and Paid")) {
				studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() - prev.getCost());
				washermandao.editearning(prev.getWasher_id(), w.getTotal_money_earned()+prev.getCost());
			}
		}
		else if(prev.getStatus_of_orders().equals("Washed and Paid")) {
			if(order.getStatus_of_orders().equals("Unwashed")) {
				washermandao.editearning(prev.getWasher_id(), w.getTotal_money_earned()-prev.getCost());
			}
			else if(order.getStatus_of_orders().equals("Washed and Unpaid")) {
				washermandao.editearning(prev.getWasher_id(), w.getTotal_money_earned()-prev.getCost());
				studentdao.editduewash(s.getRoll_number(), s.getDue_wash_charges() + prev.getCost());
			}
		}
		laundarydao.editstatus(order);
	}

	@Override
	public List<List<Integer>> duechargesall(int washer_id) {
		return laundarydao.duechargesall(washer_id);
	}

	@Override
	public int totalduecharges(int washer_id) {
		return laundarydao.totalduecharges(washer_id);
	}

}
