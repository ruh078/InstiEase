package com.dbms.insti.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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

import com.dbms.insti.models.Appointment;
import com.dbms.insti.models.Day_menu;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Medicine;
import com.dbms.insti.models.Mess_incharge;
import com.dbms.insti.models.Prescription;
import com.dbms.insti.models.Student;
import com.dbms.insti.models.Users;
import com.dbms.insti.service.AppointmentService;
import com.dbms.insti.service.DayMenuService;
import com.dbms.insti.service.MedicineService;
import com.dbms.insti.service.MessService;
import com.dbms.insti.service.PrescriptionService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;
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
    
	@GetMapping("/student")
	   public String studentpage(Model model){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	               return "student_profile";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/medical")
	public String appointmentspage(Model model){
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
	        	  
	               return "student_appointments";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/medical")
	public String takeappointment(@ModelAttribute("newappointment") Appointment appointment){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   appointment.setStudent_roll_no(student.getRoll_number());
	        	   appointmentService.save(appointment);
	               return "redirect:/student/medical";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/medical/edit/{appointment_id}")
	public String editappointment(@PathVariable int appointment_id, @ModelAttribute("newappointment") Appointment appointment){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   appointment.setStudent_roll_no(student.getRoll_number());
	        	   appointmentService.edit(appointment);
	               return "redirect:/student/medical";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
	
	@PostMapping("/student/medical/delete/{appointment_id}")
	public String deleteappointment(@PathVariable int appointment_id){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	
	        	   appointmentService.delete(appointment_id);
	               return "redirect:/student/medical";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/medical/prescription/{appointment_id}")
	public String appointmentspage(@PathVariable int appointment_id, Model model){
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
	       
	       return "redirect:/login";
	       
	 }
	
	@GetMapping("/student/mess")
	public String messpage(Model model){
	       if(securityService.isLoggedIn()) {
	           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==3) {
	        	   Users user = userService.findByEmail(securityService.findLoggedInUsername());
	        	   model.addAttribute("student_details", user);
	        	   Student student = studentservice.getStudentbyUserId(user.getUser_id());
	        	   List<String>Days =  Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
	        	   
	        	   model.addAttribute("days", Days);
	        	   model.addAttribute("menu", daymenuService.Menu(messService.findbyhostelid(student.getHostel_id()).getMess_id()));
	        	   return "student_mess";
	           }
	           return "redirect:/";
	       }
	       
	       return "redirect:/login";
	       
	 }
}
