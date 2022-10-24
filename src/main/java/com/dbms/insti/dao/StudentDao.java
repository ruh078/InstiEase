package com.dbms.insti.dao;
import java.util.List;

import com.dbms.insti.models.Student;

public interface StudentDao {
    public List<Student> listAllStudents();
    public void save(Student student);
    public Student getStudentbyId(int student_roll_no);
    public Student getStudentbyUserId(int user_id);
    public List<Student>listAllStudentsofHostel(int hostel_id);
}
