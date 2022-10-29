package com.dbms.insti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbms.insti.models.Laundary_orders;
import com.dbms.insti.models.Users;
import com.dbms.insti.models.Washerman;
import com.dbms.insti.service.ComplaintService;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.LaundaryService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;
import com.dbms.insti.service.WashermanService;

@Controller
public class WashermanController {
	@Autowired
	private SecurityService securityService;
	@Autowired
    private UserService userService;
	@Autowired
	private WashermanService washermanService;
	@Autowired
	private LaundaryService laundaryservice;
	@Autowired
    private StudentService studentservice;
	@Autowired
	private HostelService hostelservice;
	@Autowired
    private ComplaintService complaintservice;
	
	@GetMapping("/washerman")
    public String profilepage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==6) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                 //  Warden washerman = washerman.findbyUserId(user.getUser_id());
                  
                   return "washerman_profile";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }
	@GetMapping("/washerman/order")
    public String orderpage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==6) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Washerman washerman = washermanService.findByUserId(user.getUser_id());
                   model.addAttribute("allorders", laundaryservice.listOrdersofWasherman(washerman.getWasher_id(), 0));
	       		   model.addAttribute("orders_unwashed", laundaryservice.listOrdersofWasherman(washerman.getWasher_id(), 1));
	       		   model.addAttribute("orders_unpaid", laundaryservice.listOrdersofWasherman(washerman.getWasher_id(), 2));
	       		   model.addAttribute("orders_paid", laundaryservice.listOrdersofWasherman(washerman.getWasher_id(), 3));
	       		   model.addAttribute("userservice", userService);
	       		   model.addAttribute("service", studentservice);
	       		   model.addAttribute("neworder", new Laundary_orders());
                   return "washerman_orders";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }
	
	@PostMapping("/washerman/order/edit/{id}")
    public String editstatus(@PathVariable int id, @ModelAttribute("neworder") Laundary_orders order){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==6) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Washerman washerman = washermanService.findByUserId(user.getUser_id());
                   order.setOrder_id(id);
                   laundaryservice.editstatus(order);
                   return "redirect:/washerman/order";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }
	
	@GetMapping("/washerman/due")
    public String duepage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==6) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Washerman washerman = washermanService.findByUserId(user.getUser_id());
                   List<List<Integer>>l = laundaryservice.duechargesall(washerman.getWasher_id());
                   model.addAttribute("alldues", l);
                   model.addAttribute("userservice", userService);
	       		   model.addAttribute("service", studentservice);
	       		   model.addAttribute("hostelservice", hostelservice);
	       		   model.addAttribute("totaldue", laundaryservice.totalduecharges(washerman.getWasher_id()));
                   return "washerman_duecharges";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }
	
	@GetMapping("/washerman/complaint")
    public String complaintpage(Model model){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==6) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Washerman washerman = washermanService.findByUserId(user.getUser_id());
                   model.addAttribute("complaints", complaintservice.listComplaintsofType(washerman.getHostel_id(), 1));
	        	   model.addAttribute("userservice", userService);
	        	   model.addAttribute("service", studentservice);
	        	   model.addAttribute("hostel", hostelservice.getHostelbyId(washerman.getHostel_id()));
                   return "washerman_complaints";
               }
               return "redirect:/";
           }
           
           return "redirect:/login";
    }

}
