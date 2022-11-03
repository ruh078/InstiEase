package com.dbms.insti.controller;

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
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.HostelServiceUtil;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;
@Controller
public class AdminHostelController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostelService hostelService;
    @GetMapping("/admin/hostel")
    public String adminhostel(Model model, RedirectAttributes attributes) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
                model.addAttribute("hostels", hostelService.listAllHostels());
                model.addAttribute("newhostel", new Hostel());
                return "admin_hostel";
            }
            return "redirect:/";
        }
        attributes.addFlashAttribute("msg", "Not Logged In!");
        return "redirect:/login";
    }
    @PostMapping({"/admin/hostel"})
    public String addhostel(@ModelAttribute("newhostel") Hostel hostel, Model model, RedirectAttributes attributes) {
           hostelService.save(hostel);
           attributes.addFlashAttribute("msg", "Succesfully added new hostel!");
           return "redirect:/admin/hostel";
    }
    @PostMapping("/admin/hostel/delete/{id}")
    public String deletehostel(@PathVariable int id, Model model, RedirectAttributes attributes) {
    	int x = hostelService.delete(id);
    	if(x==1)
    		attributes.addFlashAttribute("msg", "Succesfully Deleted!");
    	else
    		attributes.addFlashAttribute("msg", "This hostel can't be deleted since users are associated with this hostel");
        return "redirect:/admin/hostel";
    }
}
