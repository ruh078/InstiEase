package com.dbms.insti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.StudentDao;

import com.dbms.insti.models.Student;

@Service
public class StudentServiceUtil implements StudentService{
    @Autowired
    StudentDao studentdao;
    @Override
    public List<Student> listAllStudents() {
        return studentdao.listAllStudents();
    }

    @Override
    public void save(Student student) {
        studentdao.save(student);
    }

	@Override
	public Student getStudentbyId(int student_roll_no) {
		return studentdao.getStudentbyId(student_roll_no);
	}

	@Override
	public List<Student> listAllStudentsofHostel(int hostel_id) {
		return studentdao.listAllStudentsofHostel(hostel_id);
	}
}
