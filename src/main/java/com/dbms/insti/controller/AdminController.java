package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
   public String adminpage(Model model, RedirectAttributes attributes){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
               Users user = userService.findByEmail(securityService.findLoggedInUsername());
               model.addAttribute("user",user);
          
               return "admin_profile";
           }
           return "redirect:/";
       }
       attributes.addFlashAttribute("msg", "Not Logged In!");
       return "redirect:/login";       
   }
   
   @PostMapping("/admin/edit/{id}")
   public String editadmin(@PathVariable("id") int id, @ModelAttribute Users user, Model model, RedirectAttributes attributes)
   {
       user.setUser_id(id);
       user.setRole(1);
       user.setEmail_id(userService.findByUserId(id).getEmail_id());
       user.setPsw(userService.findByUserId(id).getPsw());
       userService.edit(user);
       attributes.addFlashAttribute("msg", "Details Updated!");
       return "redirect:/admin";
   }
   
}

