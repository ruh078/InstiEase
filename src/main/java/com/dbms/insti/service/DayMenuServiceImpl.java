package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.DayMenuDao;
import com.dbms.insti.models.Day_menu;

@Service
public class DayMenuServiceImpl implements DayMenuService {
	@Autowired
	DayMenuDao daymenudao;
	@Override
	public List<Day_menu> Menu(int mess_id) {
		return daymenudao.Menu(mess_id);
	}

	@Override
	public void save(Day_menu menu) {
		daymenudao.save(menu);
		
	}

	@Override
	public void edit(Day_menu menu) {
		daymenudao.edit(menu);
	}

	@Override
	public Day_menu Menu_day(int mess_id, int day_id) {
		return daymenudao.Menu_day(mess_id, day_id);
	}

}
