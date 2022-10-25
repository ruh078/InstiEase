package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Mess_charges;
@Repository
public class MessChargesDaoImpl implements MessChargesDao{
	@Autowired
    JdbcTemplate template;
    private RowMapper<Mess_charges> messchargeRowMapper = new RowMapper<Mess_charges>() {
        @Override
        public Mess_charges mapRow(ResultSet rs, int rowNum) throws SQLException {

            Mess_charges messcharges = new Mess_charges();
            messcharges.setMeal_type(rs.getString("meal_type"));
            messcharges.setCost(rs.getInt("cost"));
           
            return messcharges;
        }
    };
	@Override
	public Mess_charges getcharge(String s) {
		String sql = "select * from mess_charges where meal_type=?";
		return template.queryForObject(sql, messchargeRowMapper, s);
	}

}
