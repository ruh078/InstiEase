package com.dbms.insti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String adminstudent(Model model, RedirectAttributes attributes) {
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
                model.addAttribute("verify", 0);
                return "admin_student";
            }
            return "redirect:/";
        }
        attributes.addFlashAttribute("msg", "Not Logged In!");
        return "redirect:/login";
    }
    @PostMapping({"/admin/student/edit/{roll_number}"})
    public String change_verify(@PathVariable int roll_number, @ModelAttribute("verify") int is_verified,  RedirectAttributes attributes) {
            System.out.println(is_verified);
           studentservice.update_verify(roll_number, is_verified);
           attributes.addFlashAttribute("msg", "verification status updated!");
           return "redirect:/admin/student";
    }
    
}
