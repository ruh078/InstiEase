package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.dao.UserDao;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

@Controller
public class LoginController {
	@Autowired
	 private SecurityService securityService;
	@GetMapping("/login")
    public String login(Model model){
		if (securityService.isLoggedIn())
			return "redirect:/";
        model.addAttribute("user", new Users());
        return "login";
    }
		
}
