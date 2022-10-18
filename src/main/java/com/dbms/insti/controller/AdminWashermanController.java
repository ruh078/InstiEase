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
import com.dbms.insti.service.WashermanService;


@Controller
public class AdminWashermanController {
	 @Autowired
	    private SecurityService securityService;
	    @Autowired
	    private UserService userService;
	    @Autowired
	    private HostelService hostelService;
	    @Autowired
	    private WashermanService washerService;
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
	    public String addwasher(@ModelAttribute("newwasher") Washerman washer, @ModelAttribute("newuser") Users user, Model model, RedirectAttributes attributes) {
	    	user.setRole(6);
	       	int x = userService.save(user);
	       	if(x==0)
	       		return "redirect:/admin/messin";
	       	washer.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
	       	System.out.println(washer.getHostel_id());
			washerService.save(washer);
	           return "redirect:/admin/wash";
	    }
	    @GetMapping("/admin/wash/delete")
	    public String deletewasher(Model model) {
	        
	        return "redirect:/admin/wash";
	    }
}
