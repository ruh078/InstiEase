package com.dbms.insti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Student;
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
    public String adminwarden(Model model, RedirectAttributes attributes) {
    	if(securityService.isLoggedIn()) {
    		if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
    		    List<Warden>wardens = wardenService.listAllWardens();
                List<Hostel>hostels = hostelService.listAllHostels();
    			Map<Object, Integer>hostelwarden = new HashMap<Object, Integer>();
    			Map<Integer,Object>wardenuser = new HashMap<Integer, Object>();
    			
    			for(Hostel hostel: hostels) {
    			    
    			    if(wardenService.findbyHostelId(hostel.getHostel_id())!=null) {
                        hostelwarden.put(hostel, wardenService.findbyHostelId(hostel.getHostel_id()).getWarden_id());
                    }

                }
                for(Warden warden: wardens) {
                    wardenuser.put(warden.getWarden_id(), userService.findByUserId(warden.getUser_id()));
                }
                
                
                model.addAttribute("hostels", hostels);
                model.addAttribute("hostelwarden", hostelwarden);
                model.addAttribute("wardenuser", wardenuser);    			       			    
                model.addAttribute("newwarden", new Warden());
                model.addAttribute("newuser", new Users());
                
    			return "admin_warden";
    			
    		}
    		return "redirect:/";
    	}
        attributes.addFlashAttribute("msg", "Not Logged In!");
    	return "redirect:/login";
    }
    
    @PostMapping({"/admin/warden"})
    public String addwarden(@ModelAttribute("newwarden") Warden warden, @ModelAttribute("newuser") Users user, Model model, RedirectAttributes attributes) {
            user.setRole(4);
           	int x=userService.save(user);
           	if(x==0)
                {attributes.addFlashAttribute("msg", "Invalid Details!");
           	    return "redirect:/admin/warden";}
           	warden.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
    		wardenService.save(warden);
            attributes.addFlashAttribute("msg", "Succesfully added new warden!");
    		return "redirect:/admin/warden";
    }
    @GetMapping("/admin/warden/delete")
    public String deletewarden(Model model, RedirectAttributes attributes) {
        attributes.addFlashAttribute("msg", "Succesfully deleted warden!");
        return "redirect:/admin/warden";
    }
    
    @PostMapping("/admin/warden/edit/{id}")
    public String editwarden(@PathVariable("id") int id, @ModelAttribute("newuser") Users user, Model model,
            RedirectAttributes attributes) {
        user.setUser_id(wardenService.findbyHostelId(id).getUser_id());
        user.setRole(4);
        user.setEmail_id(userService.findByUserId((wardenService.findbyHostelId(id)).getUser_id()).getEmail_id());
        user.setPsw(userService.findByUserId((wardenService.findbyHostelId(id)).getUser_id()).getPsw());
        userService.edit(user);
        attributes.addFlashAttribute("msg", "Succesfully updated warden details!");
        return "redirect:/admin/warden";
    }
}
