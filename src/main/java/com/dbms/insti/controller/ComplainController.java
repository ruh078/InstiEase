package com.dbms.insti.controller;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbms.insti.models.Appointment;
import com.dbms.insti.models.Complaints;
import com.dbms.insti.models.Student;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.ComplaintService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;

@Controller
public class ComplainController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentservice;
    @Autowired
    private ComplaintService complaintservice;
    
    @GetMapping("/student/complain")
    public String complainspage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
                   
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Student student = studentservice.getStudentbyUserId(user.getUser_id());
                   model.addAttribute("public_complaints", complaintservice.listPublicComplaints(student.getHostel_id()));
                   model.addAttribute("all_complaints", complaintservice.listComplaintsofStudent(student.getRoll_number()));
                   model.addAttribute("service", studentservice);
                   model.addAttribute("userservice", userService);
                   model.addAttribute("newcomplaint", new Complaints());
                   return "students_complaints";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
           
     }
    @PostMapping("/student/complain")
    public String filecomplaint(@ModelAttribute("newcomplaint") Complaints complaint){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Student student = studentservice.getStudentbyUserId(user.getUser_id());
                   complaint.setStudent_roll_no(student.getRoll_number());
                   Date utilDate = new Date();
                   java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                   complaint.setComplain_date(sqlDate);
                   complaintservice.save(complaint);
                   return "redirect:/student/complain";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
           
     }

}
