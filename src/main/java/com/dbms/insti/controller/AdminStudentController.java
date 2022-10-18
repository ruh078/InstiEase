package com.dbms.insti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.models.Student;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;

@Controller
public class AdminStudentController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentservice;
    @Autowired
    private HostelService hostelservice;
    @GetMapping("/admin/student")
    public String adminstudent(Model model) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
                Map<Object, Object>studentuser = new HashMap<Object, Object>();
                Map<Object, Object>studenthostel = new HashMap<Object, Object>();
                List<Student>students = studentservice.listAllStudents();
                for(Student student: students) {
                    studentuser.put(student, userService.findByUserId(student.getUser_id()));
                }
                for(Student student: students) {
                    studenthostel.put(student, hostelservice.getHostelbyId(student.getHostel_id()));
                }
                model.addAttribute("students", students);
                model.addAttribute("studentuser", studentuser);
                model.addAttribute("studenthostel",studenthostel);
                return "admin_student";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }
    
    
}
