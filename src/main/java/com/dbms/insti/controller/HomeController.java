package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;
@Controller
public class HomeController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
   @GetMapping("/")
   public String home(Model model){
       System.out.println("check");
       if (securityService.isLoggedIn()) {
           int x = userService.findByEmail(securityService.findLoggedInUsername()).getRole();
           System.out.println(x);
           if(x==1) {
               return "redirect:/admin";
           }
           else if(x==2) {
               return "redirect:/medical";
           }
           else if(x==3) {
               return "redirect:/student";
           }
           else if(x==4) {
               return "redirect:/warden";
           }
           else if(x==5) {
               return "redirect:/mess";
           }
           else if(x==6) {
               return "redirect:/washerman";
           }
           
       }
       
       return "home";
       
   }
}
