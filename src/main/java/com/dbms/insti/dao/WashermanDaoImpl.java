package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Warden;
import com.dbms.insti.models.Washerman;
@Repository
public class WashermanDaoImpl implements WashermanDao {
    @Autowired
    JdbcTemplate template;
    private RowMapper<Washerman> washermanRowMapper = new RowMapper<Washerman>() {
        @Override
        public Washerman mapRow(ResultSet rs, int rowNum) throws SQLException {

            Washerman washerman = new Washerman();
            washerman.setWasher_id(rs.getInt("Washer_id"));
            washerman.setAccount_no(rs.getString("Account_no"));
            washerman.setUpi_id(rs.getString("Upi_id"));
            washerman.setCost_sheet_wash(rs.getInt("Cost_sheet_wash"));
            washerman.setCost_sheet_iron(rs.getInt("Cost_sheet_iron"));
            washerman.setCost_lower_wash(rs.getInt("Cost_lower_wash"));
            washerman.setCost_lower_iron(rs.getInt("Cost_lower_iron"));
            washerman.setCost_upper_wash(rs.getInt("Cost_upper_wash"));
            washerman.setCost_upper_iron(rs.getInt("Cost_upper_iron"));
            washerman.setTotal_money_earned(rs.getInt("Total_money_earned"));
            washerman.setHostel_id(rs.getInt("Hostel_id"));
            washerman.setUser_id(rs.getInt("User_id"));
            return washerman;
        }
    };

    @Override
    public List<Washerman> listAllWasherman() {
        String sql = "select * from washerman";
        List<Washerman> washerman = template.query(sql, washermanRowMapper);
        return washerman;
    }
    
    @Override
    public void save(Washerman washerman) {
        String sql = "insert into washerman(account_no,upi_id,cost_sheet_wash,cost_sheet_iron,cost_lower_wash,cost_lower_iron,cost_upper_wash,cost_upper_iron,total_money_earned,hostel_id, user_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,washerman.getAccount_no(),washerman.getUpi_id(),washerman.getCost_sheet_wash(),washerman.getCost_sheet_iron(),washerman.getCost_lower_wash(),washerman.getCost_lower_iron(),washerman.getCost_upper_wash(),washerman.getCost_upper_iron(),washerman.getTotal_money_earned(),washerman.getHostel_id(),washerman.getUser_id());
    }
    
    @Override
    public void edit(Washerman washerman) {
        String sql = "UPDATE washerman SET account_no=?,upi_id=?,cost_sheet_wash=?,cost_sheet_iron=?,cost_lower_wash=?,cost_lower_iron=?,cost_upper_wash=?,cost_upper_iron=? WHERE washer_id=?";
        template.update(sql,washerman.getAccount_no(),washerman.getUpi_id(),washerman.getCost_sheet_wash(),washerman.getCost_sheet_iron(),washerman.getCost_lower_wash(),washerman.getCost_lower_iron(),washerman.getCost_upper_wash(),washerman.getCost_upper_iron(),washerman.getWasher_id());
    }
    
    @Override
    public Washerman findByWasherId(int washer_id) {
        String query = "Select * from washerman where washer_id=?";
        try {
            Washerman washerman = template.queryForObject(query,washermanRowMapper, washer_id);
            return washerman;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
	public List<Washerman> listAllWashermanofHostel(int hostel_id) {
		String sql = "select * from washerman where hostel_id=?";
        List<Washerman> washerman = template.query(sql, washermanRowMapper, hostel_id);
        return washerman;
	}

    
}
