package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Users;
import com.dbms.insti.models.Warden;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;
import com.dbms.insti.service.WardenService;

@Controller
public class AdminWardenController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private WardenService wardenService;
    @Autowired
    private HostelService hostelService;
    @GetMapping("/admin/warden")
    public String adminwarden(Model model) {
    	if(securityService.isLoggedIn()) {
    		if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
    			model.addAttribute("wardens", wardenService.listAllWardens());
    			model.addAttribute("hostels", hostelService.listAllHostels());
                model.addAttribute("newwarden", new Warden());
                model.addAttribute("newuser", new Users());
             
    			return "admin_warden";
    		}
    		return "redirect:/";
    	}
    	return "redirect/login";
    }
    
    @PostMapping({"/admin/warden"})
    public String addwarden(@ModelAttribute("newwarden") Warden warden, @ModelAttribute("newuser") Users user, Model model, RedirectAttributes attributes) {
           System.out.println(warden.getHostel_id());
            user.setRole(4);
           	userService.save(user);
         
           	warden.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
    		wardenService.save(warden);
           return "redirect:/admin/warden";
    }
    @GetMapping("/admin/warden/delete")
    public String deletewarden(Model model) {
        
        return "redirect:/admin/warden";
    }
}
