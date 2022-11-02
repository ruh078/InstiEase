package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Warden;
@Repository
public class WardenDaoImpl implements WardenDao {
    @Autowired
    JdbcTemplate template;
    private RowMapper<Warden> wardenRowMapper = new RowMapper<Warden>() {
        @Override
        public Warden mapRow(ResultSet rs, int rowNum) throws SQLException {

            Warden warden = new Warden();
            warden.setWarden_id(rs.getInt("warden_id"));
            warden.setHostel_id(rs.getInt("hostel_id"));
            warden.setUser_id(rs.getInt("user_id"));
            return warden;
        }
    };

    @Override
    public List<Warden> listAllWardens() {
        String sql = "select * from warden";
        List<Warden> wardens = template.query(sql, wardenRowMapper);
        return wardens;
    }

    @Override
    public void save(Warden warden) {
        String sql = "insert into warden(hostel_id, user_id) values(?, ?)";
        template.update(sql, warden.getHostel_id(), warden.getUser_id());
    }
    
    @Override
    public Warden findbyHostelId(int hostel_id) {
        String query = "select * from warden where hostel_id=?";
        try {
            Warden warden = template.queryForObject(query,wardenRowMapper, hostel_id);
            return warden;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public Warden findbyUserId(int user_id) {
        String query = "select * from warden where user_id=?";
        try {
            Warden warden = template.queryForObject(query,wardenRowMapper, user_id);
            return warden;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

	@Override
	public void delete(int warden_id) {
		// TODO Auto-generated method stub
        String query = "delete from warden where warden_id=?";
        template.update(query, warden_id);
		
	}

	@Override
	public Warden findbyWardenId(int warden_id) {
		String query = "select * from warden where warden_id=?";
        try {
            Warden warden = template.queryForObject(query,wardenRowMapper, warden_id);
            return warden;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

}
