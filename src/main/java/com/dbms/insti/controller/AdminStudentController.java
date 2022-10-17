package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.models.Hostel;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

public class AdminStudentController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/admin/hostel")
    public String adminhostel(Model model) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
               
                return "admin_student";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }
    
}
