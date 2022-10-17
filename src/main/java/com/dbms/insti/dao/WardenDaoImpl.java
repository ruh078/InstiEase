package com.dbms.insti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
        String sql = "insert into warden(warden_id) values(?)";
        template.update(sql, warden.getWarden_id());
    }

}
