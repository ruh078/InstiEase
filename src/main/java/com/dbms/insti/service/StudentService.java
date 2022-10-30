package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Student;

public interface StudentService {
    public List<Student> listAllStudents();
    public void save(Student student);
    public Student getStudentbyId(int student_roll_no);
    public Student getStudentbyUserId(int user_id);
    public List<Student>listAllStudentsofHostel(int hostel_id);
    public void update_verify(int roll_number, int is_verified);
}
