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
import com.dbms.insti.models.Users;
@Repository
public class HostelDaoImpl implements HostelDao {
    @Autowired
    JdbcTemplate template;
    private RowMapper<Hostel> hostelRowMapper = new RowMapper<Hostel>() {
        @Override
        public Hostel mapRow(ResultSet rs, int rowNum) throws SQLException {

            Hostel hostel = new Hostel();
            hostel.setHostel_id(rs.getInt("hostel_id"));
            hostel.setHostel_name(rs.getString("hostel_name"));
            return hostel;
        }
    };

    @Override
    public List<Hostel> listAllHostels() {
        String sql = "select * from hostel";
        List<Hostel> hostels = template.query(sql, hostelRowMapper);
        return hostels;
    }

    @Override
    public void save(Hostel hostel) {
        String sql = "insert into hostel(hostel_name) values(?)";
        template.update(sql, hostel.getHostel_name());
    }

    @Override
    public Hostel getHostelbyId(int hostel_id) {
        String query = "select * from hostel where hostel_id=?";
        try {
            Hostel hostel = template.queryForObject(query, hostelRowMapper, hostel_id);
            return hostel;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
       
    }

	@Override
	public void delete(int hostel_id) {
		String sql = "delete from hostel where hostel_id=?";
		template.update(sql, hostel_id);
	}

}
