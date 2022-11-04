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
        String sql = "select * from student order by is_verified";
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
    public void edit(Student student) {
    	String sql = "UPDATE student SET room_number=?,bank_account_no=?, is_verified=0 WHERE user_id=?";
    	template.update(sql,student.getRoom_number(),student.getBank_account_no(),student.getUser_id());
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

	@Override
	public void editrefund(int roll_number, int refund) {
		String sql = "update student set mess_refund=? where roll_number=?";
		template.update(sql, refund, roll_number);

	}

	@Override
	public void editduewash(int roll_number, int duewash) {
		String sql = "update student set due_wash_charges=? where roll_number=?";
		template.update(sql, duewash, roll_number);
	}

    @Override
    public void update_verify(int roll_number, int is) {
        // TODO Auto-generated method stub
        String sql ="update student set is_verified=? where roll_number=?";
        template.update(sql,is,roll_number);
    }

	@Override
	public void editeligibility(int roll_number, int iseligible) {
		String sql ="update student set is_eligible_laundary=? where roll_number=?";
        template.update(sql,iseligible,roll_number);
	}

	@Override
	public void update_laundary() {
		String sql = "update student set is_eligible_laundary=0 where due_wash_charges!=0 ";
		template.update(sql);
		
	}

	@Override
	public void delete(int roll_number) {
		String sql = "delete from student where roll_number=?";
		template.update(sql, roll_number);
	}

	@Override
	public void update_mess_charges() {
		String sql = "delete from cancel_mess";
		String sql1 = "update student set mess_refund=0";
		template.update(sql);
		template.update(sql1);
		
	}
    

}
