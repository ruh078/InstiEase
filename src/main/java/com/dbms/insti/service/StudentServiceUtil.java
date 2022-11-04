package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.AppointmentDao;
import com.dbms.insti.dao.CancelMessDao;
import com.dbms.insti.dao.ComplaintsDao;
import com.dbms.insti.dao.LaundaryDao;
import com.dbms.insti.dao.PrescriptionDao;
import com.dbms.insti.dao.StudentDao;
import com.dbms.insti.dao.UserDao;
import com.dbms.insti.models.Student;

@Service
public class StudentServiceUtil implements StudentService{
    @Autowired
    StudentDao studentdao;
    @Autowired
    UserDao userdao;
    @Autowired
    ComplaintsDao complaintsdao;
    @Autowired
    CancelMessDao cancelmessdao;
    @Autowired
    LaundaryDao laundarydao;
    @Autowired
    AppointmentDao appointmentdao;
    
    @Override
    public List<Student> listAllStudents() {
        return studentdao.listAllStudents();
    }

    @Override
    public void save(Student student) {
        studentdao.save(student);
    }
    
    @Override
    public void edit(Student student) {
        studentdao.edit(student);
    }

	@Override
	public Student getStudentbyId(int student_roll_no) {
		return studentdao.getStudentbyId(student_roll_no);
	}

	@Override
	public List<Student> listAllStudentsofHostel(int hostel_id) {
		return studentdao.listAllStudentsofHostel(hostel_id);
	}

	@Override
	public Student getStudentbyUserId(int user_id) {
		return studentdao.getStudentbyUserId(user_id);
	}

    @Override
    public void update_verify(int roll_number, int is_verified) {
        // TODO Auto-generated method stub
        studentdao.update_verify(roll_number,is_verified);
        
    }

	@Override
	public void editeligibility(int roll_number, int iseligible) {
		studentdao.editeligibility(roll_number, iseligible);
	}

	@Override
	public void update_laundary() {
		studentdao.update_laundary();
	}

	@Override
	public int delete(int roll_number) {
		int user_id = studentdao.getStudentbyId(roll_number).getUser_id();
		Student student = studentdao.getStudentbyId(roll_number);
		if(student.getMess_refund()!=0)
			return 0;
		if(student.getDue_wash_charges()!=0)
			return 0;
		if(!laundarydao.listAllOrdersofStudent(roll_number, 1).isEmpty())
			return 0;
		if(!laundarydao.listAllOrdersofStudent(roll_number, 2).isEmpty())
			return 0;
		complaintsdao.delete_all_complaints_student(roll_number);
		cancelmessdao.delete_all_requests_student(roll_number);
		laundarydao.delete_all_orders_student(roll_number);
		appointmentdao.delete_all_orders_student(roll_number);
		try {
			
			studentdao.delete(roll_number);
			userdao.delete(user_id);
			return 1;
		}
		catch(Exception e) {
			return 0;
		}

		
	}

	@Override
	public void update_mess_charges() {
		studentdao.update_mess_charges();
	}
}
