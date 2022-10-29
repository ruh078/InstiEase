package com.dbms.insti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

@Controller
public class AdminMedicalController {
	@Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @GetMapping("/admin/medin")
    public String adminmedical(Model model) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
            	   List<Users>med = userService.FindByRole(2);
            	   if(med.isEmpty())
            		   model.addAttribute("med_incharge", null);
            	   else
            		   model.addAttribute("med_incharge", med.get(0));
            	   model.addAttribute("newUser", new Users());
            	   System.out.println(userService.FindByRole(2));
                   return "admin_medical";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }
    @PostMapping({"/admin/medin"})
    public String addmedical( @ModelAttribute("newhostel") Users user, Model model, RedirectAttributes attributes) {
           user.setRole(2);
           userService.save(user);
           return "redirect:/admin/medin";
    }
    @GetMapping("/admin/medin/delete")
    public String deletemedical(Model model) {
        
        return "redirect:/admin/medin";
    }
    
    @PostMapping({"/admin/medin/edit/{id}"})
    public String editmedical(@PathVariable("id") int id, @ModelAttribute("newUser") Users user, Model model) {
        user.setUser_id(id);
        user.setRole(2);
        user.setEmail_id(userService.findByUserId(id).getEmail_id());
        user.setPsw(userService.findByUserId(id).getPsw());
        userService.edit(user);
        return "redirect:/admin/medin";
    }
    
}
