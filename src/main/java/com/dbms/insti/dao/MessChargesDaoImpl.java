package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Mess_charges;
import com.dbms.insti.models.Mess_incharge;
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
		try {
			return template.queryForObject(sql, messchargeRowMapper, s);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Mess_charges> listAllMesscharges(){
	    String sql = "select * from mess_charges";
        List<Mess_charges> messes = template.query(sql, messchargeRowMapper);
        return messes;
	}
	
	@Override
	public void edit(Mess_charges mess_charges) {
	    String sql = "UPDATE mess_charges SET cost=? where meal_type=?";
	    template.update(sql, mess_charges.getCost(), mess_charges.getMeal_type());
	    return;
	}

	@Override
	public void add(Mess_charges mess_charges) {
		String sql = "insert into mess_charges(meal_type, cost) values(?,?)";
		template.update(sql, mess_charges.getMeal_type(), mess_charges.getCost());
		
	}

}
