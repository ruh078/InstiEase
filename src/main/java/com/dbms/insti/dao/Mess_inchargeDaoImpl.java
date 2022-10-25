package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Mess_incharge;
@Repository
public class Mess_inchargeDaoImpl implements  Mess_inchargeDao {
	@Autowired
    JdbcTemplate template;
    private RowMapper<Mess_incharge> messRowMapper = new RowMapper<Mess_incharge>() {
        @Override
        public Mess_incharge mapRow(ResultSet rs, int rowNum) throws SQLException {

            Mess_incharge mess = new Mess_incharge();
            mess.setMess_id(rs.getInt("mess_id"));
            mess.setHostel_id(rs.getInt("hostel_id"));
            mess.setUser_id(rs.getInt("user_id"));
            return mess;
        }
    };
    
	@Override
	public List<Mess_incharge> listAllMesses() {
		 	String sql = "select * from mess_incharge";
	        List<Mess_incharge> messes = template.query(sql, messRowMapper);
	        return messes;
	}

	@Override
	public void save(Mess_incharge mess) {
		String sql = "insert into mess_incharge(hostel_id, user_id) values(?, ?)";
		System.out.println(mess.getHostel_id());
        template.update(sql, mess.getHostel_id(), mess.getUser_id());
	}

	@Override
	public Mess_incharge findbyhostelid(int hostel_id) {
		String sql = "select * from mess_incharge where hostel_id=?";
		try {
			Mess_incharge mess = template.queryForObject(sql, messRowMapper, hostel_id);
			
			return mess;
		}
		catch (EmptyResultDataAccessException e) {
			//System.out.println("error");
        	return null;
        }
   
	}

	@Override
	public Mess_incharge findbyuserid(int user_id) {
		String sql = "select * from mess_incharge where user_id=?";
		try {
			Mess_incharge mess = template.queryForObject(sql, messRowMapper, user_id);
			
			return mess;
		}
		catch (EmptyResultDataAccessException e) {
			//System.out.println("error");
        	return null;
        }
	}
	
}
