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
            complaint.setIsPrivate(rs.getInt("isPrivate"));
            complaint.setStatus(rs.getString("status"));
            complaint.setComplain_date(rs.getDate("complain_date"));
            return complaint;
        }
    };

    @Override
    public List<Complaints> listAllComplaints(int hostel_id) {
        String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?)";
        List<Complaints> complaints = template.query(sql, complaintRowMapper, hostel_id);
        return complaints;
    }
    
    @Override
    public List<Complaints> listComplaintsofStudent(int roll_number) {
        String sql = "select * from complaints where student_roll_no=?";
        List<Complaints> complaints = template.query(sql, complaintRowMapper, roll_number);
        return complaints;
    }
    
    @Override
    public List<Complaints> listResolvedComplaints(int hostel_id) {
        String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?) and status=? ";
        List<Complaints> complaints = template.query(sql, complaintRowMapper,hostel_id,"Resolved");
        return complaints;
    }

   @Override
    public void save(Complaints complaints) {
        String sql = "insert into complaints(student_roll_no,description,type,isPrivate,status,complain_date) values(?,?,?,?,?,?)";
        template.update(sql, complaints.getStudent_roll_no(), complaints.getDescription(), complaints.getType(), complaints.getIsPrivate(),"Yet to Approve",complaints.getComplain_date());
        
    }



    @Override
    public List<Complaints> listApprovedandUnresolvedComplaints(int hostel_id) {
        String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?) and status=? ";
        List<Complaints> complaints = template.query(sql, complaintRowMapper,hostel_id,"Approved and Unresolved");
        return complaints;
    }
    
    @Override
    public List<Complaints> listUnapprovedComplaints(int hostel_id) {
        String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?) and status=? ";
        List<Complaints> complaints = template.query(sql, complaintRowMapper,hostel_id,"Yet to approve");
        return complaints;
    }

    @Override
    public List<Complaints> listPublicComplaints(int hostel_id) {
        String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?) and isPrivate=0 ";
        List<Complaints> complaints = template.query(sql, complaintRowMapper,hostel_id);
        return complaints;
    }

    public List<Complaints> listPrivateComplaints(int hostel_id) {
        String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?) and isPrivate=1 ";
        List<Complaints> complaints = template.query(sql, complaintRowMapper,hostel_id);
        return complaints;
    }

	@Override
	public List<Complaints> listComplaintsofType(int hostel_id, int type) {
		String sql = "select * from complaints where student_roll_no in (select roll_number from student where hostel_id=?) and type=? order by complain_date desc";
        List<Complaints> complaints = template.query(sql, complaintRowMapper,hostel_id, type);
        return complaints;
	}
}