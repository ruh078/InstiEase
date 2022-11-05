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

import com.dbms.insti.models.Student;
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
        public String adminwarden(Model model, RedirectAttributes attributes) {
           
            
            if(securityService.isLoggedIn()) {
                if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
                   
                    Map<Object, Object>washermanuser = new HashMap<Object, Object>();
                    Map<Object, Object>washermanhostel = new HashMap<Object, Object>();
                    List<Washerman>washer = washerService.listAllWasherman();
                    for(Washerman washerman: washer) {
                        washermanuser.put(washerman, userService.findByUserId(washerman.getUser_id()));
                    }
                    for(Washerman washerman: washer) {
                        washermanhostel.put(washerman, hostelService.getHostelbyId(washerman.getHostel_id()));
                    }
                    model.addAttribute("washers", washer);
                    model.addAttribute("hostels", hostelService.listAllHostels());
                    model.addAttribute("newwasher", new Washerman());
                    model.addAttribute("newuser", new Users());
                    model.addAttribute("washermanuser", washermanuser);
                    model.addAttribute("washermanhostel", washermanhostel);
                   
                    return "admin_washerman";
                }
                return "redirect:/";
            }
            attributes.addFlashAttribute("msg", "Not Logged In!");
            return "redirect/login";
        }
        
        @PostMapping({"/admin/wash"})
        public String addwasher(@ModelAttribute("newwasher") Washerman washer, @ModelAttribute("newuser") Users user, Model model, RedirectAttributes attributes) {
            user.setRole(6);
            int x = userService.save(user);
            if(x==0){
                attributes.addFlashAttribute("msg", "Invalid Details!");
                return "redirect:/admin/messin";
            }
            washer.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
            System.out.println(washer.getHostel_id());
            washerService.save(washer);
            attributes.addFlashAttribute("msg", "Successfully added new washerman!");
            return "redirect:/admin/wash";
        }
        @PostMapping("/admin/wash/delete/{id}")
        public String deletewasher(@PathVariable("id") int id, Model model, RedirectAttributes attributes) {
        	int x = washerService.delete(id);
        	if(x==1)
                attributes.addFlashAttribute("msg", "Succesfully deleted this washerman!");
            else
            	attributes.addFlashAttribute("msg", "Cannot delete this washerman!");            
        	return "redirect:/admin/wash";
        }
        
        @PostMapping({"/admin/wash/edit/{id}"})
        public String editwasher(@PathVariable("id") int id, @ModelAttribute("newuser") Users user, @ModelAttribute("newwasher") Washerman washer,  Model model,
                RedirectAttributes attributes) {
            user.setUser_id(washerService.findByWasherId(id).getUser_id());
            washer.setWasher_id(washerService.findByWasherId(id).getWasher_id());
            //user.setRole(6);
            //user.setEmail_id(userService.findByUserId((washerService.findByWasherId(id)).getUser_id()).getEmail_id());
            //user.setPsw(userService.findByUserId((washerService.findByWasherId(id)).getUser_id()).getPsw());
            userService.edit(user);
            washerService.edit(washer);
            attributes.addFlashAttribute("msg", "Successfully Updated details!");
            return "redirect:/admin/wash";
        }
        
        @PostMapping({"/admin/wash/edithostel/{id}"})
        public String edithostelwasher(@PathVariable("id") int id, @ModelAttribute("newwasher") Washerman washer, RedirectAttributes attributes) {
           
            //user.setRole(6);
            //user.setEmail_id(userService.findByUserId((washerService.findByWasherId(id)).getUser_id()).getEmail_id());
            //user.setPsw(userService.findByUserId((washerService.findByWasherId(id)).getUser_id()).getPsw());
            int x = washerService.edithostel(id,washer.getHostel_id());
            if(x==1)
            	attributes.addFlashAttribute("msg", "Successfully Updated Hostel!");
            else
            	attributes.addFlashAttribute("msg", "Cannot change the hostel!");
            return "redirect:/admin/wash";
        }
}
