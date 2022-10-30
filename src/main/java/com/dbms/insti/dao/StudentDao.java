package com.dbms.insti.dao;
import java.util.List;

import com.dbms.insti.models.Student;

public interface StudentDao {
    public List<Student> listAllStudents();
    public void save(Student student);
    public void editrefund(int roll_number, int refund);
    public void editduewash(int roll_number, int duewash);
    public void editeligibility(int roll_number, int iseligible);
    public Student getStudentbyId(int student_roll_no);
    public Student getStudentbyUserId(int user_id);
    public List<Student>listAllStudentsofHostel(int hostel_id);
    public void update_verify(int roll_number, int is_verified);
}
