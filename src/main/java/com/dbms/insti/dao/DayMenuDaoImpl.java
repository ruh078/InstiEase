package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Day_menu;

@Repository
public class DayMenuDaoImpl implements DayMenuDao {

	@Autowired
    JdbcTemplate template;
    private RowMapper<Day_menu> DayMenuRowMapper = new RowMapper<Day_menu>() {
        @Override
        public Day_menu mapRow(ResultSet rs, int rowNum) throws SQLException {

            Day_menu menu = new Day_menu();
            menu.setMess_id(rs.getInt("mess_id"));
            menu.setDay_id(rs.getInt("day_id"));
            menu.setBreakfast(rs.getString("breakfast"));
            menu.setLunch(rs.getString("lunch"));
            menu.setDinner(rs.getString("dinner"));
            return menu;
        }
    };
	
	@Override
	public List<Day_menu> Menu(int mess_id) {
		String sql = "select * from day_menu where mess_id=?";
		List<Day_menu>menu = template.query(sql, DayMenuRowMapper, mess_id);
		return menu;
	}

	@Override
	public void save(Day_menu menu) {
        String sql = "insert into day_menu(mess_id, day_id, breakfast, lunch, dinner) values(?,?,?,?,?)";
        template.update(sql, menu.getMess_id(), menu.getDay_id(), menu.getBreakfast(), menu.getLunch(), menu.getDinner());
	}

	@Override
	public void edit(Day_menu menu) {
		String sql = "update day_menu set breakfast=?, lunch=?, dinner=? where mess_id=? and day_id=?";
        template.update(sql, menu.getBreakfast(), menu.getLunch(), menu.getDinner(),  menu.getMess_id(), menu.getDay_id());

	}

	@Override
	public Day_menu Menu_day(int mess_id, int day_id) {
		String sql = "select * from day_menu where mess_id=? and day_id=?";
		try {
			Day_menu menu = template.queryForObject(sql, DayMenuRowMapper, mess_id, day_id);
			return menu;
		}
		catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	@Override
	public void delete(int mess_id) {
		String sql = "delete from day_menu where mess_id=?";
		template.update(sql, mess_id);
	}

}
