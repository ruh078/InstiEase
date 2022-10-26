package com.dbms.insti.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbms.insti.models.Day_menu;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.CancelMessService;
import com.dbms.insti.service.DayMenuService;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.MessService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;

@Controller
public class MessInchargeController {
		@Autowired
		private SecurityService securityService;
		@Autowired
		private UserService userService;
		@Autowired
		private MessService messService;
		@Autowired
		private HostelService hostelService;
		@Autowired
		private DayMenuService daymenuService;
		@Autowired
		private CancelMessService cancelmessService;
		@Autowired
	    private StudentService studentservice;
		@GetMapping("/mess")
	   public String messpage(Model model){
			
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
	               return "mess_profile";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	   }
		
		@GetMapping("/mess/menu")
		public String menupage(Model model){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   Hostel hostel = hostelService.getHostelbyId(mess.getHostel_id());
		        	   List<String>Days =  Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
		        	   List<Integer>Daywithnomenu = new ArrayList<Integer>();
		        	   for(int i=0;i<7;i++) {
		        		   if(daymenuService.Menu_day(mess.getMess_id(), i)==null) {
		        			   Daywithnomenu.add(i);
		        		   }
		        		   
		        	   }
		        	   model.addAttribute("days", Days);
		        	   model.addAttribute("hostel", hostel);
		        	   model.addAttribute("user", user);
		        	   model.addAttribute("mess", mess);
		        	   model.addAttribute("newmenu", new Day_menu());
		        	   model.addAttribute("menu", daymenuService.Menu(mess.getMess_id()));
		        	   model.addAttribute("add_day", Daywithnomenu);
		        	   model.addAttribute("add", !Daywithnomenu.isEmpty());
		               return "mess_menu_incharge";
		           }
		           return "redirect:/";
		       }
		       
		       return "redirect:/login";
		       
		   }
		
		@PostMapping("/mess/menu")
		public String addmenu(@ModelAttribute("newmenu") Day_menu menu){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   menu.setMess_id(mess.getMess_id());
		        	   daymenuService.save(menu);
		        	   return "redirect:/mess/menu";
		           }
		           return "redirect:/";
		       }
		       
		       return "redirect:/login";
		       
		   }
		
		@PostMapping("/mess/menu/edit/{day_id}")
		public String addmenu(@PathVariable int day_id, @ModelAttribute("newmenu") Day_menu menu){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   menu.setMess_id(mess.getMess_id());
		        	   menu.setDay_id(day_id);
		        	   daymenuService.edit(menu);
		        	   return "redirect:/mess/menu";
		           }
		           return "redirect:/";
		       }
		       
		       return "redirect:/login";
		       
		   }
		
		@GetMapping("/mess/cancel")
		public String cancelpage(Model model){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   Hostel hostel = hostelService.getHostelbyId(mess.getHostel_id());
		        	   model.addAttribute("allcancels", cancelmessService.CancellationofHostel(hostel.getHostel_id()));
		        	   model.addAttribute("currentcancels", cancelmessService.CancellationofHostelTime(1, hostel.getHostel_id()));
		        	   model.addAttribute("futurecancels", cancelmessService.CancellationofHostelTime(2, hostel.getHostel_id()));
		        	   model.addAttribute("pastcancels", cancelmessService.CancellationofHostelTime(3, hostel.getHostel_id()));
		        	   model.addAttribute("service", studentservice);
	                   model.addAttribute("userservice", userService);
		               return "cancelmess_incharge";
		           }
		           return "redirect:/";
		       }
		       
		       return "redirect:/login";
		       
		   }
		
		@GetMapping("/mess/refund")
		public String detailspage(Model model){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   model.addAttribute("students", studentservice.listAllStudentsofHostel(mess.getHostel_id()));
		        	   model.addAttribute("service", userService);
		        	   model.addAttribute("cancelservice", cancelmessService);
		               return "refund_incharge";
		           }
		           return "redirect:/";
		       }
		       
		       return "redirect:/login";
		       
		}
}
