package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Day_menu;

public interface DayMenuService {
	public List<Day_menu> Menu(int mess_id);
    public void save(Day_menu menu);
    public void edit(Day_menu menu);
    public Day_menu Menu_day(int mess_id, int day_id);
}
