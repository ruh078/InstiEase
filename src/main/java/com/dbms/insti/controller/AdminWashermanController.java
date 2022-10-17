package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Users;
import com.dbms.insti.models.Warden;
import com.dbms.insti.models.Washerman;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;


@Controller
public class AdminWashermanController {
	 @Autowired
	    private SecurityService securityService;
	    @Autowired
	    private UserService userService;
	    @Autowired
	    private HostelService hostelService;
	    //@Autowired
	   // private WasherService washerService;
	    @GetMapping("/admin/wash")
	    public String adminwarden(Model model) {
	    	if(securityService.isLoggedIn()) {
	    		if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
	    		//	model.addAttribute("washers", washerService.listAllWardens());
	    			model.addAttribute("hostels", hostelService.listAllHostels());
	                model.addAttribute("newwasher", new Washerman());
	                model.addAttribute("newuser", new Users());
	             
	    			return "admin_washerman";
	    		}
	    		return "redirect:/";
	    	}
	    	return "redirect/login";
	    }
	    
	    @PostMapping({"/admin/wash"})
	    public String addwarden(@ModelAttribute("newwarden") Warden warden, @ModelAttribute("newuser") Users user, Model model, RedirectAttributes attributes) {
	           
	           return "redirect:/admin/wash";
	    }
	    @GetMapping("/admin/wash/delete")
	    public String deletewarden(Model model) {
	        
	        return "redirect:/admin/wash";
	    }
}
