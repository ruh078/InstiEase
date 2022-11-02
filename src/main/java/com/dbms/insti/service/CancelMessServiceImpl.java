package com.dbms.insti.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.CancelMessDao;
import com.dbms.insti.dao.MessChargesDao;
import com.dbms.insti.dao.StudentDao;
import com.dbms.insti.models.Cancel_mess;
@Service
public class CancelMessServiceImpl implements CancelMessService{
	@Autowired 
	CancelMessDao cancelmessdao;
	@Autowired
	MessChargesDao messchargesdao;
	@Autowired
	StudentDao studentdao;
	
	@Override
	public void save(Cancel_mess cancel_mess) {
		if(cancel_mess.getIs_breakfast()==0 && cancel_mess.getIs_lunch()==0 && cancel_mess.getIs_dinner()==0) {
			return;
		}
		if(cancelmessdao.getRequest(cancel_mess.getStudent_roll_no(), cancel_mess.getCancel_date())==null) {
			int refund = cancel_mess.getIs_breakfast() * messchargesdao.getcharge("breakfast").getCost();
			refund+=cancel_mess.getIs_lunch() * messchargesdao.getcharge("lunch").getCost();
			refund+=cancel_mess.getIs_dinner() * messchargesdao.getcharge("dinner").getCost();
			refund+=studentdao.getStudentbyId(cancel_mess.getStudent_roll_no()).getMess_refund();
			cancelmessdao.save(cancel_mess);
			studentdao.editrefund(cancel_mess.getStudent_roll_no(), refund);
			return;
		}
		Cancel_mess c = cancelmessdao.getRequest(cancel_mess.getStudent_roll_no(), cancel_mess.getCancel_date());
		cancel_mess.setIs_breakfast(c.getIs_breakfast()|cancel_mess.getIs_breakfast());
		cancel_mess.setIs_lunch(c.getIs_lunch()|cancel_mess.getIs_lunch());
		cancel_mess.setIs_dinner(c.getIs_dinner()|cancel_mess.getIs_dinner());
		
		int refund = cancel_mess.getIs_breakfast() * messchargesdao.getcharge("breakfast").getCost();
		refund+=cancel_mess.getIs_lunch() * messchargesdao.getcharge("lunch").getCost();
		refund+=cancel_mess.getIs_dinner() * messchargesdao.getcharge("dinner").getCost();
		
		int r = c.getIs_breakfast() * messchargesdao.getcharge("breakfast").getCost();
		r+=c.getIs_lunch() * messchargesdao.getcharge("lunch").getCost();
		r+=c.getIs_dinner() * messchargesdao.getcharge("dinner").getCost();
		
		refund = studentdao.getStudentbyId(cancel_mess.getStudent_roll_no()).getMess_refund() - r + refund;
		cancelmessdao.edit(cancel_mess);

		studentdao.editrefund(cancel_mess.getStudent_roll_no(), refund);

	}

	@Override
	public void edit(Cancel_mess cancel_mess) {
		Cancel_mess c = cancelmessdao.getById(cancel_mess.getRequest_id());
		int refund = cancel_mess.getIs_breakfast() * messchargesdao.getcharge("breakfast").getCost();
		refund+=cancel_mess.getIs_lunch() * messchargesdao.getcharge("lunch").getCost();
		refund+=cancel_mess.getIs_dinner() * messchargesdao.getcharge("dinner").getCost();
		
		int r = c.getIs_breakfast() * messchargesdao.getcharge("breakfast").getCost();
		r+=c.getIs_lunch() * messchargesdao.getcharge("lunch").getCost();
		r+=c.getIs_dinner() * messchargesdao.getcharge("dinner").getCost();
		
		refund = studentdao.getStudentbyId(cancel_mess.getStudent_roll_no()).getMess_refund() - r + refund;
		cancelmessdao.edit(cancel_mess);
		studentdao.editrefund(cancel_mess.getStudent_roll_no(), refund);
		if(cancel_mess.getIs_breakfast()==0 && cancel_mess.getIs_lunch()==0 && cancel_mess.getIs_dinner()==0)
			cancelmessdao.delete(cancel_mess.getRequest_id());
	}

	@Override
	public void delete(int request_id) {
		Cancel_mess c = cancelmessdao.getById(request_id);
		int r = c.getIs_breakfast() * messchargesdao.getcharge("breakfast").getCost();
		r+=c.getIs_lunch() * messchargesdao.getcharge("lunch").getCost();
		r+=c.getIs_dinner() * messchargesdao.getcharge("dinner").getCost();
		r = studentdao.getStudentbyId(c.getStudent_roll_no()).getMess_refund() - r;
		cancelmessdao.delete(request_id);
		studentdao.editrefund(c.getStudent_roll_no(), r);

	}

	@Override
	public List<Cancel_mess> CancellationofStudent(int roll_number) {
		return cancelmessdao.CancellationofStudent(roll_number);
	}

	@Override
	public List<Cancel_mess> CancellationofHostel(int hostel_id) {
		return cancelmessdao.CancellationofHostel(hostel_id);
	}

	@Override
	public Cancel_mess getRequest(int roll_number, Date cancel_date) {
		return cancelmessdao.getRequest(roll_number, cancel_date);
	}

	@Override
	public Cancel_mess getById(int request_id) {
		return cancelmessdao.getById(request_id);
	}

	@Override
	public int comparedate(Date d1) {
		LocalDate current = LocalDate.now();
 	   	LocalDate date = d1.toLocalDate();
 	   	if(date.isBefore(current))
 	   		return 0;
 	   	return 1;
		
	}

	@Override
	public int count(int meal_type, int roll_number) {
		return cancelmessdao.count(meal_type, roll_number);
	}

	@Override
	public List<Cancel_mess> CancellationofHostelTime(int time, int hostel_id) {
		return cancelmessdao.CancellationofHostelTime(time, hostel_id);
	}

	@Override
	public int count_total(int meal_type, int hostel_id, Date date) {
		return cancelmessdao.count_total(meal_type, hostel_id, date);
	}

	
}
