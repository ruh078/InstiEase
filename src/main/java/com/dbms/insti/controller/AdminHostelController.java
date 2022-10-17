package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Users;
import com.dbms.insti.service.HostelService;
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
    public String adminhostel(Model model) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
                model.addAttribute("hostels", hostelService.listAllHostels());
                return "admin_hostel";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }
    @PostMapping({"/admin/hostel"})
    public String addhostel(@ModelAttribute("hostel") Hostel hostel, Model model, RedirectAttributes attributes) {
           
           userService.save(hostel);
           return "redirect:/admin/hostel";
    }
    
}
