package com.dbms.insti.dao;
import java.util.List;

import com.dbms.insti.models.Student;

public interface StudentDao {
    public List<Student> listAllStudents();
    public void save(Student student);

}
