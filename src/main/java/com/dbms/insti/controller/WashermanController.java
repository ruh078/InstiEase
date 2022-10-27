package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.models.Users;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

@Controller
public class WashermanController {
	@Autowired
	private SecurityService securityService;
	@Autowired
    private UserService userService;
	//@Autowired
//	private WashermanService washermanService;
	@GetMapping("/washerman")
    public String profilepage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==6) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                 //  Warden washerman = washerman.findbyUserId(user.getUser_id());
                  
                   return "washerman_profile";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }

}
