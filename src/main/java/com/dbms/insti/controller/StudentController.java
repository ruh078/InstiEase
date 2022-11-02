package com.dbms.insti.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbms.insti.dao.MessChargesDao;
import com.dbms.insti.models.Appointment;
import com.dbms.insti.models.Cancel_mess;
import com.dbms.insti.models.Dates;
import com.dbms.insti.models.Day_menu;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Laundary_orders;
import com.dbms.insti.models.Medicine;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Prescription;
import com.dbms.insti.models.Student;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.AppointmentService;
import com.dbms.insti.service.CancelMessService;
import com.dbms.insti.service.DayMenuService;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.LaundaryService;
import com.dbms.insti.service.MedicineService;
import com.dbms.insti.service.MessService;
import com.dbms.insti.service.PrescriptionService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;
import com.dbms.insti.service.WashermanService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class StudentController {
	@Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired 
    private AppointmentService appointmentService;
    @Autowired
    private StudentService studentservice;
    @Autowired
    private MedicineService medicineService; 
    @Autowired
    private PrescriptionService prescriptionservice;
    @Autowired
    private DayMenuService daymenuService;
    @Autowired
    private MessService messService;
    @Autowired
    private HostelService hostelService;
    @Autowired
    private CancelMessService cancelmessService;
    @Autowired
    private MessChargesDao messchargesdao;
    @Autowired 
    private WashermanService washermanservice;
    @Autowired
    private LaundaryService laundaryservice;
    
	@GetMapping("/student")
	   public String studentpage(Model model, RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user=userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student=studentservice.getStudentbyUserId(user.getUser_id());
	        	   Hostel hostel = hostelService.getHostelbyId(student.getHostel_id());
	        	   model.addAttribute("user",user);
	        	   model.addAttribute("student",student);
	        	   model.addAttribute("newuser", new Users());
	        	   model.addAttribute("newstudent",new Student());
	        	   model.addAttribute("hostel", hostel);
	               return "student_profile";
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");

	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/medical")
	public String appointmentspage(Model model, RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   
	       		   LocalDate date = LocalDate.now();
	       		  
	       		   LocalDate newdate = date.plusDays(15);
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   model.addAttribute("currentappointments", appointmentService.listCurrentAppointmentsStudent(student.getRoll_number()));
	        	   model.addAttribute("previousappointments", appointmentService.listPreviousAppointmentsStudent(student.getRoll_number()));
	        	   model.addAttribute("newappointment", new Appointment());
	        	   model.addAttribute("date", date);
	        	   model.addAttribute("enddate", newdate);
	        	   model.addAttribute("iseligible", student.getIs_verified());
	               return "student_appointments";
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");

	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/medical")
	public String takeappointment(@ModelAttribute("newappointment") Appointment appointment,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   appointment.setStudent_roll_no(student.getRoll_number());
	        	   appointmentService.save(appointment);
				   attributes.addFlashAttribute("msg", "New Appointment registered!");
	               return "redirect:/student/medical";
	           }
	           return "redirect:/";
	       }
	       	
		   	attributes.addFlashAttribute("msg", "Not Logged In!");

	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/medical/edit/{appointment_id}")
	public String editappointment(@PathVariable int appointment_id, @ModelAttribute("newappointment") Appointment appointment,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   appointment.setStudent_roll_no(student.getRoll_number());
	        	   int x = appointmentService.edit(appointment);
	        	   if(x==1)
				   	attributes.addFlashAttribute("msg", "Details Updated!");
	        	   else
	        		   attributes.addFlashAttribute("msg", "Can't be edited");

	               return "redirect:/student/medical";
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/medical/delete/{appointment_id}")
	public String deleteappointment(@PathVariable int appointment_id, RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	
	        	   int x = appointmentService.delete(appointment_id);
	        	   if(x==1)
				   	attributes.addFlashAttribute("msg", "Appointment Deleted!");
	        	   else
	        		   attributes.addFlashAttribute("msg", "Can't be Deleted!");

	               return "redirect:/student/medical";
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
       
	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/medical/prescription/{appointment_id}")
	public String appointmentspage(@PathVariable int appointment_id, Model model,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Appointment a = appointmentService.getAppointmentbyId(appointment_id);
	        	   
	        	   List<Prescription>prescription = prescriptionservice.getPrescriptionsOfAppointment(appointment_id);
	        	   model.addAttribute("appointment", a);	        	   
	        	   model.addAttribute("student_details", userService.findByEmail(securityService.findLoggedInUsername()));
	        	   model.addAttribute("prescriptions", prescription);
	        	   model.addAttribute("medicineservice", medicineService);
	        	   return "student_prescription";
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");

	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/mess")
	public String messpage(Model model,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   model.addAttribute("student_details", user);
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   List<String>Days =  Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
	        	   LocalDate date = LocalDate.now();
		       	   date = date.plusDays(1);
	        	   LocalDate sd = LocalDate.of(2020,7,31); 
	        	   sd = sd.withYear(date.getYear());
	        	   LocalDate ed = LocalDate.of(2020,11,30); 
	        	   ed = ed.withYear(date.getYear());
		       	   LocalDate enddate = date;
		       	   int f=0;
	       		   if(date.isAfter(sd) && date.isBefore(ed)) {
	       			   f=1;
	       			   enddate = ed;
	       		   }
	       		   sd = LocalDate.of(2020,01,01); 
	        	   sd = sd.withYear(date.getYear());
	        	   ed = LocalDate.of(2020,4,30); 
	        	   ed = ed.withYear(date.getYear());
	        	   if(date.isAfter(sd) && date.isBefore(ed)) {
	        		   f=1;
	       			   enddate = ed;
	       		   }
	        	   if(messchargesdao.getcharge("breakfast")==null || messchargesdao.getcharge("lunch")==null || messchargesdao.getcharge("dinner")==null) {
	        		   attributes.addFlashAttribute("msg", "Mess charges are not added by admin");
	        		   return "redirect:/student";
	        	   }
	        	   model.addAttribute("cancel_requests", cancelmessService.CancellationofStudent(student.getRoll_number()));
	        	   model.addAttribute("newrequest", new Cancel_mess());
	        	   model.addAttribute("days", Days);
	        	   model.addAttribute("menu", daymenuService.Menu(messService.findbyhostelid(student.getHostel_id()).getMess_id()));
	        	   model.addAttribute("date", date);
	        	   model.addAttribute("enddate", enddate);
	        	   model.addAttribute("cancel_end_date", new Dates());
	        	   model.addAttribute("can_cancel", f);
	        	   model.addAttribute("service", cancelmessService);
	        	   model.addAttribute("bcost", messchargesdao.getcharge("breakfast").getCost());
	        	   model.addAttribute("lcost", messchargesdao.getcharge("lunch").getCost());
	        	   model.addAttribute("dcost", messchargesdao.getcharge("dinner").getCost());
	        	   model.addAttribute("bcount", cancelmessService.count(1, student.getRoll_number()));
	        	   model.addAttribute("lcount", cancelmessService.count(2, student.getRoll_number()));
	        	   model.addAttribute("dcount", cancelmessService.count(3, student.getRoll_number()));
	        	   model.addAttribute("refund", student.getMess_refund());
	        	   model.addAttribute("iseligible", student.getIs_verified());
	        	   return "student_mess";
	           }
			   attributes.addFlashAttribute("msg", "Not Logged In!");
       
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/mess")
	public String addcancel( @ModelAttribute("cancel_end_date")Dates endingdate, @ModelAttribute("newrequest") Cancel_mess cancel_mess,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   cancel_mess.setStudent_roll_no(student.getRoll_number());
	        	   LocalDate edate = endingdate.getDate().toLocalDate();

	        	   edate = edate.plusDays(1);
	        	   LocalDate startdate = cancel_mess.getCancel_date().toLocalDate();
	        	  
	        	   for (LocalDate date = startdate; date.isBefore(edate); date = date.plusDays(1))
	        	   {
	        		   Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        		   java.sql.Date sqlDate = new java.sql.Date(d.getTime());
	        	       cancel_mess.setCancel_date(sqlDate);
	        	       System.out.println(date);
	        	       cancelmessService.save(cancel_mess);
	        	   }
				   attributes.addFlashAttribute("msg", "cancel request added!");
	     
	        	   return "redirect:/student/mess";
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/mess/edit/{id}")
	public String editcancel( @PathVariable int id, @ModelAttribute("newrequest") Cancel_mess cancel_mess,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   cancel_mess.setCancel_date(cancelmessService.getById(id).getCancel_date());
	        	   cancel_mess.setStudent_roll_no(cancelmessService.getById(id).getStudent_roll_no());
	        	   cancel_mess.setRequest_id(id);
	        	   cancelmessService.edit(cancel_mess);
				   	attributes.addFlashAttribute("msg", "cancel request edited!");

	        	   return "redirect:/student/mess";
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/mess/delete/{id}")
	public String delete( @PathVariable int id, RedirectAttributes attributes){
	       if(securityService.isLoggedIn()){
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   cancelmessService.delete(id);
				   	attributes.addFlashAttribute("msg", "cancel request deleted!");

	        	   return "redirect:/student/mess";
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
       
	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/washerman")
	public String laundarypage(Model model,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	  
	        	   if(student.getDue_wash_charges()==0)
	        		   studentservice.editeligibility(student.getRoll_number(), 1);
	        	   student = studentservice.getStudentbyUserId(user.getUser_id());
	       		   model.addAttribute("allwasherman", washermanservice.listAllWashermanofHostel(student.getHostel_id()));
	       		   model.addAttribute("userservice", userService);
	       		   model.addAttribute("allorders", laundaryservice.listAllOrdersofStudent(student.getRoll_number(), 0));
	       		   model.addAttribute("orders_unwashed", laundaryservice.listAllOrdersofStudent(student.getRoll_number(), 1));
	       		   model.addAttribute("orders_unpaid", laundaryservice.listAllOrdersofStudent(student.getRoll_number(), 2));
	       		   model.addAttribute("orders_paid", laundaryservice.listAllOrdersofStudent(student.getRoll_number(), 3));
	       		   model.addAttribute("washerservice", washermanservice);
	       		   model.addAttribute("due_charges", student.getDue_wash_charges());
	       		   model.addAttribute("iseligible", student.getIs_verified() & student.getIs_eligible_laundary());
	               return "student_washerman";
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");

	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/washerman/{id}")
	public String laundarypage(@PathVariable int id, Model model, RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	       		   model.addAttribute("userservice", userService);
	       		   model.addAttribute("washer", washermanservice.findByWasherId(id));
	       		   model.addAttribute("allorders", laundaryservice.listOrdersofStudent(student.getRoll_number(), id, 0));
	       		   model.addAttribute("orders_unwashed", laundaryservice.listOrdersofStudent(student.getRoll_number(), id, 1));
	       		   model.addAttribute("orders_unpaid", laundaryservice.listOrdersofStudent(student.getRoll_number(), id, 2));
	       		   model.addAttribute("orders_paid", laundaryservice.listOrdersofStudent(student.getRoll_number(), id, 3));
	       		   model.addAttribute("neworder", new Laundary_orders());
	       		   model.addAttribute("due_charges", laundaryservice.duecharges(student.getRoll_number(), id));
	       		   model.addAttribute("iseligible", student.getIs_verified() & student.getIs_eligible_laundary());
	       		   return "student_washerman_order";
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/washerman/{id}")
	public String giveorder(@PathVariable int id, @ModelAttribute("neworder") Laundary_orders neworder,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	       		   neworder.setStudent_roll_no(student.getRoll_number());
	       		   Date utilDate = new Date();
	       		   java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	       		   neworder.setOrder_date(sqlDate);
	       		   neworder.setWasher_id(id);
	       		   laundaryservice.save(neworder);
	       		   String url = "redirect:/student/washerman/" + id;
				   	attributes.addFlashAttribute("msg", "Order placed!");

	               return url;
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
        
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/washerman/{wid}/delete/{id}")
	public String deleteorder(@PathVariable int wid, @PathVariable int id,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   laundaryservice.delete(id);
	       		   String url = "redirect:/student/washerman/" + wid;
				   	attributes.addFlashAttribute("msg", "order deleted!");
					
	               return url;
	           }
	           return "redirect:/";
	       }
	       	attributes.addFlashAttribute("msg", "Not Logged In!");
			
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/washerman/{wid}/edit/{id}")
	public String editorder(@PathVariable int wid, @PathVariable int id, @ModelAttribute("neworder") Laundary_orders order,RedirectAttributes attributes){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   order.setWasher_id(wid);
	        	   order.setOrder_id(id);
	        	   order.setStudent_roll_no(student.getRoll_number());
	        	   laundaryservice.edit(order);
	       		   String url = "redirect:/student/washerman/" + wid;
				   	attributes.addFlashAttribute("msg", "Order Updated!");

	               return url;
	           }
	           return "redirect:/";
	       }
	       attributes.addFlashAttribute("msg", "Not Logged In!");
       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("student/edit/{id}")
	public String editstudent(@PathVariable("id") int id, @ModelAttribute("newuser") Users user, @ModelAttribute("newstudent") Student student, Model model,
			RedirectAttributes attributes) {
		user.setUser_id(id);
		student.setUser_id(id);
		student.setIs_verified(0);
		userService.edit(user);
		studentservice.edit(student);
		attributes.addFlashAttribute("msg", "Sucessfully Updated!");

		return "redirect:/student";
	}
	
	@PostMapping("/student/changepsw/{id}")
	   public String changepsw(@PathVariable("id") int id, @ModelAttribute("oldpsw") String oldpsw, @ModelAttribute("newpsw") String newpsw, Model model, RedirectAttributes attributes) {
	       Users user = userService.findByUserId(id);
	       BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	       if(bcrypt.matches(oldpsw, user.getPsw())) {
	    	   userService.changepassword(id, newpsw);
	    	   attributes.addFlashAttribute("msg", "Changed password!");
	       }
	       else {
	    	   attributes.addFlashAttribute("msg", "Enter correct old password!");
	       }
	      
	       return "redirect:/student";
	   }
}
