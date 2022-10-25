package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.models.Student;
import com.dbms.insti.models.Users;
import com.dbms.insti.models.Warden;
import com.dbms.insti.service.AppointmentService;
import com.dbms.insti.service.ComplaintService;
import com.dbms.insti.service.MedicineService;
import com.dbms.insti.service.PrescriptionService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;
import com.dbms.insti.service.WardenService;

@Controller
public class WardenController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private WardenService wardenService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private ComplaintService complaintservice;
    
    @GetMapping("/warden")
    public String profilepage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==4) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Warden warden = wardenService.findbyUserId(user.getUser_id());
                  
                   return "warden";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }

    @GetMapping("/warden/complaints")
    public String complainspage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==4) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Warden warden = wardenService.findbyUserId(user.getUser_id());
                   model.addAttribute("public_complaints", complaintservice.listPublicComplaints(warden.getHostel_id()));
                   return "warden_complaints";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }
}
