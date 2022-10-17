package com.dbms.insti.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.insti.models.Complaints;
@Repository
public class ComplaintsDaoImpl implements ComplaintsDao {
    @Autowired
    JdbcTemplate template;
    private RowMapper<Complaints> complaintRowMapper = new RowMapper<Complaints>() {
        @Override
        public Complaints mapRow(ResultSet rs, int rowNum) throws SQLException {

            Complaints complaint = new Complaints();
            complaint.setComplaint_id(rs.getInt("complaint_id"));
            complaint.setStudent_roll_no(rs.getInt("student_roll_no"));
            complaint.setDescription(rs.getString("description"));
            complaint.setType(rs.getInt("type"));
            complaint.setIsPrivate(rs.getInt("is_private"));
            complaint.setStatus(rs.getString("status"));
            return complaint;
        }
    };

    @Override
    public List<Complaints> listAllComplaints() {
        String sql = "select * from complaints";
        List<Complaints> complaints = template.query(sql, complaintRowMapper);
        return complaints;
    }

   @Override
    public void save(Complaints complaints) {
        String sql = "insert into complaints(student_roll_no,description,type,isPrivate,status) values(?,?,?,?,?)";
        template.update(sql, complaints.getStudent_roll_no(), complaints.getDescription(), complaints.getType(), complaints.getIsPrivate(),"Not resolved");
        
    }
}