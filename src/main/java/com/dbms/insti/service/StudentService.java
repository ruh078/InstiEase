package com.dbms.insti.service;

import java.util.List;

import com.dbms.insti.models.Student;

public interface StudentService {
    public List<Student> listAllStudents();
    public void save(Student student);
}
