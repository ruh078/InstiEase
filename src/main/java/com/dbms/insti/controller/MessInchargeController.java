package com.dbms.insti.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.dbms.insti.models.Day_menu;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.CancelMessService;
import com.dbms.insti.service.ComplaintService;
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
		@Autowired
	    private ComplaintService complaintservice;
		@GetMapping("/mess")
	   public String messpage(Model model, RedirectAttributes attributes){
			
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
	               Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Mess_incharge mess_in = messService.findbyuserid(user.getUser_id());
                   Hostel hostel = hostelService.getHostelbyId(mess_in.getHostel_id());
                   model.addAttribute("user", user);
                   model.addAttribute("mess_in", mess_in);
                   model.addAttribute("hostel", hostel);
	               return "mess_profile";
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");

	       return "redirect:/login";
	       
	   }
		
		@GetMapping("/mess/menu")
		public String menupage(Model model, RedirectAttributes attributes){
				
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
		        	   int no_students = studentservice.listAllStudentsofHostel(mess.getHostel_id()).size();
		        	   LocalDate startdate = LocalDate.now();
		        	   LocalDate enddate = startdate.plusDays(7);
		        	   Map<LocalDate, List<Integer>>m = new HashMap<LocalDate, List<Integer>>();
		        	   List<LocalDate> dates = new ArrayList<>();;
		        	   for (LocalDate date = startdate; date.isBefore(enddate); date = date.plusDays(1))
		        	   {
		        		   Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		        		   java.sql.Date sqlDate = new java.sql.Date(d.getTime());
		        		   List<Integer>l = new ArrayList<>();
		        		   l.add(no_students - cancelmessService.count_total(1, sqlDate));
		        		   l.add(no_students - cancelmessService.count_total(2, sqlDate));
		        		   l.add(no_students - cancelmessService.count_total(3, sqlDate));
		        		   dates.add(date);
		        		   m.put(date, l);
		        	   }
		        	   model.addAttribute("dates", dates);
		        	   model.addAttribute("count_date", m);
		        	   model.addAttribute("number_students", no_students);
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
		       attributes.addFlashAttribute("msg", "Not Logged In!");

		       return "redirect:/login";
		       
		   }
		
		@PostMapping("/mess/menu")
		public String addmenu(@ModelAttribute("newmenu") Day_menu menu, RedirectAttributes attributes){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   menu.setMess_id(mess.getMess_id());
		        	   daymenuService.save(menu);
					   	attributes.addFlashAttribute("msg", "Added menu item!");

		        	   return "redirect:/mess/menu";
		           }
		           return "redirect:/";
		       }
		       	attributes.addFlashAttribute("msg", "Not Logged In!");

		       return "redirect:/login";
		       
		   }
		
		@PostMapping("/mess/menu/edit/{day_id}")
		public String addmenu(@PathVariable int day_id, @ModelAttribute("newmenu") Day_menu menu,
				RedirectAttributes attributes){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   menu.setMess_id(mess.getMess_id());
		        	   menu.setDay_id(day_id);
		        	   daymenuService.edit(menu);
					   	attributes.addFlashAttribute("msg", "Updated menu item!");

		        	   return "redirect:/mess/menu";
		           }
		           return "redirect:/";
		       }
		       attributes.addFlashAttribute("msg", "Not Logged In!");

		       return "redirect:/login";
		       
		   }
		
		@GetMapping("/mess/cancel")
		public String cancelpage(Model model, RedirectAttributes attributes){
				
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
		       attributes.addFlashAttribute("msg", "Not Logged In!");

		       return "redirect:/login";
		       
		   }
		
		@GetMapping("/mess/refund")
		public String detailspage(Model model, RedirectAttributes attributes){
				
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
		       	attributes.addFlashAttribute("msg", "Not Logged In!");

		       return "redirect:/login";
		       
		}
		
		@GetMapping("/mess/complains")
		public String complaintspage(Model model, RedirectAttributes attributes){
				
		       if(securityService.isLoggedIn()) {
		           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==5) {
		        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
		        	   Mess_incharge mess = messService.findbyuserid(user.getUser_id());
		        	   model.addAttribute("complaints", complaintservice.listComplaintsofType(mess.getHostel_id(), 2));
		        	   model.addAttribute("userservice", userService);
		        	   model.addAttribute("service", studentservice);
		        	   model.addAttribute("hostel", hostelService.getHostelbyId(mess.getHostel_id()));
		               return "mess_complaints";
		           }
		           return "redirect:/";
		       }
		       	attributes.addFlashAttribute("msg", "Not Logged In!");
				
		       return "redirect:/login";
		       
		}
		
		@PostMapping("/mess/edit/{id}")
		public String editmessin(@PathVariable("id") int id, @ModelAttribute("newUser") Users user, Model model,
				RedirectAttributes attributes) {
		    user.setUser_id(id);
	        user.setRole(5);
	        user.setEmail_id(userService.findByUserId(id).getEmail_id());
	        user.setPsw(userService.findByUserId(id).getPsw());
	        userService.edit(user);
			attributes.addFlashAttribute("msg", "Updated details!");
				
		    return "redirect:/mess";
		}
}
