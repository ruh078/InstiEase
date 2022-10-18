package com.dbms.insti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            	/*Map<Object, Object>messuser = new HashMap<Object, Object>();
                Map<Object, Object>messhostel = new HashMap<Object, Object>();
                List<Mess_incharge>messes = messService.listAllMesses();
                for(Mess_incharge mess: messes) {
                    messuser.put(mess, userService.findByUserId(mess.getUser_id()));
                }
                for(Mess_incharge mess: messes) {
                    messhostel.put(mess, hostelService.getHostelbyId(mess.getHostel_id()));
                }
                model.addAttribute("hostels", hostelService.listAllHostels());
                model.addAttribute("newUser", new Users());
                model.addAttribute("newMess", new Mess_incharge());
                model.addAttribute("messuser", messuser);
                model.addAttribute("messhostel", messhostel);
                model.addAttribute("messes", messes);*/
            	List<Mess_incharge>messes = messService.listAllMesses();
            	List<Hostel>hostels = hostelService.listAllHostels();
                Map<Object, Integer>hostelmess = new HashMap<Object, Integer>();
            	Map<Integer, Object>messuser = new HashMap<Integer, Object>();

                for(Hostel hostel: hostels) {
                	if(messService.findbyhostelid(hostel.getHostel_id())!=null) {
                		hostelmess.put(hostel, messService.findbyhostelid(hostel.getHostel_id()).getMess_id());
                	}

                }
                for(Mess_incharge mess: messes) {
                    messuser.put(mess.getMess_id(), userService.findByUserId(mess.getUser_id()));
                }
                
                
                model.addAttribute("hostels", hostels);
                model.addAttribute("hostelmess", hostelmess);
                model.addAttribute("messuser", messuser);
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
       	System.out.println(mess.getHostel_id());
		messService.save(mess);
        return "redirect:/admin/messin";
    }
}
