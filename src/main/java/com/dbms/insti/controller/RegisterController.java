package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Student;
import com.dbms.insti.models.Users;
import com.dbms.insti.models.Warden;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;
@Controller
public class RegisterController {
	 @Autowired
	 private UserService userService;
	 @Autowired
	 private SecurityService securityService;
	 @Autowired
     private HostelService hostelservice;
	 @Autowired
     private StudentService studentservice;
	 
	 @GetMapping({"/register"})
	 public String register(Model model) {
		   if(securityService.isLoggedIn())
			   return "redirect:/";
	       model.addAttribute("user", new Users());
	       model.addAttribute("hostels", hostelservice.listAllHostels());
           model.addAttribute("newstu", new Student());
	       return "register";
	 }

	 @GetMapping("/registerError")
	 public String registerError(Model model) {
	        model.addAttribute("user", new Users());
	        model.addAttribute("emailError", true);
	        return "register";
	 }

	@PostMapping({"/register"})
	 public String addwarden(@ModelAttribute("newstu") Student student, @ModelAttribute("newuser") Users user, Model model, RedirectAttributes attributes) {
         System.out.println(student.getHostel_id());
          user.setRole(3);
          if(studentservice.getStudentbyId(student.getRoll_number())==null) {
        	  int x = userService.save(user);
        	  if(x==0) {
        	      attributes.addFlashAttribute("msg", "Invalid details! Could Not Register!");
        		  return "redirect:/register";
        	  }
        	  student.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
        	  studentservice.save(student);
          }
		 attributes.addFlashAttribute("msg", "Successfully Registerd!");
         return "redirect:/login";
  }
}
