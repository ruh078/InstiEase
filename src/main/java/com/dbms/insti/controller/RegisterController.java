package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.dbms.insti.models.Users;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;
@Controller
public class RegisterController {
	 @Autowired
	 private UserService userService;
	 @Autowired
	 private SecurityService securityService;
	 @GetMapping({"/"})
	 public String home(Model m) {
		   if(securityService.isLoggedIn())
			   m.addAttribute("name", userService.findByEmail(securityService.findLoggedInUsername()).getName());
		   else
			   m.addAttribute("name", "Guest");
	       return "home";
	 }
	 @GetMapping({"/register"})
	 public String register(Model model) {
		   if(securityService.isLoggedIn())
			   return "redirect:/";
	       model.addAttribute("user", new Users());
	       return "register";
	 }

	 @GetMapping("/registerError")
	 public String registerError(Model model) {
	        model.addAttribute("user", new Users());
	        model.addAttribute("emailError", true);
	        return "register";
	 }

	 @PostMapping({"/register"})
	 public String register(@ModelAttribute("user") Users user, Model model, RedirectAttributes attributes) {
	        if (userService.userExists(user.getEmail_id())) {
	            return "redirect:/registerError";
	        }
	        userService.save(user);
	        return "redirect:/login";
	 }
}
