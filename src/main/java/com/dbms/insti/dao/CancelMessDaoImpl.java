package com.dbms.insti.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Cancel_mess;
@Repository
public class CancelMessDaoImpl implements CancelMessDao {
	
	@Autowired
    JdbcTemplate template;
    private RowMapper<Cancel_mess> cancelmessRowMapper = new RowMapper<Cancel_mess>() {
        @Override
        public Cancel_mess mapRow(ResultSet rs, int rowNum) throws SQLException {

            Cancel_mess cancel_mess = new Cancel_mess();
            cancel_mess.setRequest_id(rs.getInt("Request_id"));
            cancel_mess.setStudent_roll_no(rs.getInt("student_roll_no"));
            cancel_mess.setCancel_date(rs.getDate("Cancel_date"));
            cancel_mess.setIs_breakfast(rs.getInt("is_breakfast"));
            cancel_mess.setIs_lunch(rs.getInt("is_lunch"));
            cancel_mess.setIs_dinner(rs.getInt("is_dinner"));
            return cancel_mess;
        }
    };
	
	@Override
	public void save(Cancel_mess cancel_mess) {
		String sql = "insert into cancel_mess(cancel_date, is_breakfast, is_lunch, is_dinner, student_roll_no) values(?, ?, ?, ?, ?)";
		template.update(sql, cancel_mess.getCancel_date(), cancel_mess.getIs_breakfast(), cancel_mess.getIs_lunch(), cancel_mess.getIs_dinner(), cancel_mess.getStudent_roll_no());
	}

	@Override
	public void edit(Cancel_mess cancel_mess) {
		String sql = "update cancel_mess set is_breakfast=?, is_lunch=?, is_dinner=? where student_roll_no=? and cancel_date=?";
		template.update(sql, cancel_mess.getIs_breakfast(), cancel_mess.getIs_lunch(), cancel_mess.getIs_dinner(), cancel_mess.getStudent_roll_no(), cancel_mess.getCancel_date() );
	}

	@Override
	public void delete(int request_id) {
		String sql = "delete from cancel_mess where request_id=?";
		template.update(sql, request_id);
	}

	@Override
	public List<Cancel_mess> CancellationofStudent(int roll_number) {
		String sql = "select * from cancel_mess where student_roll_no=? order by cancel_date desc";
		List<Cancel_mess>l=template.query(sql, cancelmessRowMapper, roll_number);
		return l;
	}

	@Override
	public List<Cancel_mess> CancellationofHostel(int hostel_id) {
		String sql = "select * from cancel_mess where student_roll_no in (select roll_number from student where hostel_id=?)";
		List<Cancel_mess>l=template.query(sql, cancelmessRowMapper, hostel_id);
		return l;
	}

	@Override
	public Cancel_mess getRequest(int roll_number, Date cancel_date) {
		String sql = "select * from cancel_mess where student_roll_no=? and cancel_date=?";
		
		try {
			Cancel_mess c = template.queryForObject(sql, cancelmessRowMapper, roll_number, cancel_date);
			return c;
		}
		catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	@Override
	public Cancel_mess getById(int request_id) {
		String sql = "select * from cancel_mess where request_id=?";
		
		try {
			Cancel_mess c = template.queryForObject(sql, cancelmessRowMapper, request_id);
			return c;
		}
		catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	@Override
	public int count(int meal_type, int roll_number) {
		String sql="";
		try {
		if(meal_type==1)
			 sql = "select sum(is_breakfast) as sum from cancel_mess where student_roll_no=?";
		else if(meal_type==2)
			 sql = "select sum(is_lunch) as sum from cancel_mess where student_roll_no=?";
		else if(meal_type==3)
			 sql = "select sum(is_dinner) as sum from cancel_mess where student_roll_no=?";
		return template.queryForObject(sql, Integer.class, roll_number);
		}
		catch(Exception e) {
			return 0;
		}
	}

	@Override
	public List<Cancel_mess> CancellationofHostelTime(int time, int hostel_id) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date today = new java.util.Date();
		java.util.Date todayWithZeroTime=new java.util.Date();
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		String sql="";
		if(time==1) {
			sql = "select * from cancel_mess where cancel_date=? and student_roll_no in (select roll_number from student where hostel_id=?)";
		}
		else if(time==2) {
			sql = "select * from cancel_mess where cancel_date>? and student_roll_no in (select roll_number from student where hostel_id=?)";
		}
		else {
			sql = "select * from cancel_mess where cancel_date<? and student_roll_no in (select roll_number from student where hostel_id=?)";
		}
		List<Cancel_mess>l=template.query(sql, cancelmessRowMapper, todayWithZeroTime, hostel_id);
		return l;
	}

	@Override
	public int count_total(int meal_type, Date date) {
		String sql="";
		try {
		if(meal_type==1)
			 sql = "select sum(is_breakfast) as sum from cancel_mess where cancel_date=?";
		else if(meal_type==2)
			 sql = "select sum(is_lunch) as sum from cancel_mess where cancel_date=?";
		else if(meal_type==3)
			 sql = "select sum(is_dinner) as sum from cancel_mess where cancel_date=?";
		return template.queryForObject(sql, Integer.class, date);
		}
		catch(Exception e) {
			return 0;
		}
	}

	

}
