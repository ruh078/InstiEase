package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.MessService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

@Controller
public class AdminMessController {
	@Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostelService hostelService;
    @Autowired
    private MessService messService;
    @GetMapping("/admin/messin")
    public String adminmess(Model model) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
                model.addAttribute("hostels", hostelService.listAllHostels());
                model.addAttribute("newUser", new Users());
                model.addAttribute("newMess", new Mess_incharge());
                return "admin_mess";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }
    
    
    
    @PostMapping({"/admin/messin"})
    public String addmess(@ModelAttribute("newUser") Users user, @ModelAttribute("newMess") Mess_incharge mess, Model model, RedirectAttributes attributes) {
    	user.setRole(5);
       	userService.save(user);
     
       	mess.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
		messService.save(mess);
        return "redirect:/admin/messin";
    }
}
