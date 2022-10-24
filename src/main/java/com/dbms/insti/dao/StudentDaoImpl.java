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
import com.dbms.insti.models.Student;

@Repository
public class StudentDaoImpl implements StudentDao{
    @Autowired
    JdbcTemplate template;
    private RowMapper<Student> studentRowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

            Student student = new Student();
            student.setRoll_number(rs.getInt("roll_number"));
            student.setRoom_number(rs.getInt("room_number"));
            student.setMess_refund(rs.getInt("mess_refund"));
            student.setBank_account_no(rs.getString("bank_account_no"));
            student.setIs_eligible_laundary(rs.getInt("is_eligible_laundary"));
            student.setDue_wash_charges(rs.getInt("due_wash_charges"));
            student.setIs_verified(rs.getInt("is_verified"));
            student.setUser_id(rs.getInt("user_id"));
            student.setHostel_id(rs.getInt("hostel_id"));
            
            return student;
        }
    };

    @Override
    public List<Student> listAllStudents() {
        String sql = "select * from student";
        List<Student> students = template.query(sql, studentRowMapper);
        return students;
    }

    @Override
    public void save(Student student) {
        // TODO Auto-generated method stub
        String sql = "insert into student(roll_number, room_number, bank_account_no, hostel_id, user_id) values(?, ?, ?, ?, ?)";
        template.update(sql, student.getRoll_number() , student.getRoom_number(), student.getBank_account_no(),student.getHostel_id(), student.getUser_id());
 
    }

	@Override
	public Student getStudentbyId(int student_roll_no) {
		String query = "select * from student where roll_number=?";
		try {
            Student student = template.queryForObject(query, studentRowMapper, student_roll_no);
            return student;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	@Override
	public List<Student> listAllStudentsofHostel(int hostel_id) {
		String sql = "select * from student where hostel_id=?";
        List<Student> students = template.query(sql, studentRowMapper, hostel_id);
        return students;
	}

	@Override
	public Student getStudentbyUserId(int user_id) {
		String query = "select * from student where user_id=?";
		try {
            Student student = template.queryForObject(query, studentRowMapper, user_id);
            return student;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
    

}
