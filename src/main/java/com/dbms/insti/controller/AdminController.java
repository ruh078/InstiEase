package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.models.Users;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;
@Controller
public class AdminController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
   @GetMapping("/admin")
   public String adminpage(Model model){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
               return "admin_profile";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
}

