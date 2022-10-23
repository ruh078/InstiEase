package com.dbms.insti.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.dbms.insti.models.Appointment;
import com.dbms.insti.models.Medicine;
import com.dbms.insti.models.Prescription;
import com.dbms.insti.models.Student;
import com.dbms.insti.service.AppointmentService;
import com.dbms.insti.service.MedicineService;
import com.dbms.insti.service.PrescriptionService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;

@Controller
public class MedicalController {
	@Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService; 
    @Autowired 
    private AppointmentService appointmentService;
    @Autowired
    private StudentService studentservice;
    @Autowired
    private PrescriptionService prescriptionservice;
   @GetMapping("/medical")
   public String medicalpage(Model model){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
               return "medical_profile";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @GetMapping("/medical/medicine")
   public String medicinepage(Model model){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
        	   model.addAttribute("medicines", medicineService.listAllMedicine());
        	   model.addAttribute("less_stock_medicines", medicineService.listMedicine_lessStock());
        	   model.addAttribute("newmedicine", new Medicine());
        	   model.addAttribute("stock", 0);
               return "medicines";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @PostMapping({"/medical/medicine"})
   public String addmedicine(@ModelAttribute("newmedicine") Medicine medicine, Model model, RedirectAttributes attributes) {
          medicineService.save(medicine);
          return "redirect:/medical/medicine";
   }
   
   @PostMapping({"/medical/medicine/edit/{medicine_id}"})
   public String changestock(@PathVariable int medicine_id, @ModelAttribute("stock") int stock,  RedirectAttributes attributes) {
	   /* Medicine updatemed=null;
	   	 if(k==1) {
        	 for(Medicine m: medicines) {
        		 if(m.getMedicine_id()==medicine_id) {
        			 updatemed=m;
        			 break;
        		 }
        	 }
          }
          else {
        	  for(Medicine m: lmedicines) {
         		 if(m.getMedicine_id()==medicine_id) {
         			 updatemed=m;
         			 break;
         		 }
         	 }
          }*/
	   	  medicineService.updateStock(medicine_id, stock);
          return "redirect:/medical/medicine";
   }
   
   @GetMapping("/medical/appointments")
   public String appointmentpage(Model model){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
               Map<Integer, Object>studentuser = new HashMap<Integer, Object>();
               List<Student>students = studentservice.listAllStudents();
               for(Student student: students) {
                   studentuser.put(student.getRoll_number(), userService.findByUserId(student.getUser_id()));
               }
        	   model.addAttribute("currentappointments", appointmentService.listCurrentAppointments());
        	   model.addAttribute("previousappointments", appointmentService.listPreviousAppointments());
        	   model.addAttribute("studentuser", studentuser);
        	   
               return "appointments_incharge";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @GetMapping("/medical/appointments/list/{roll_number}")
   public String appointmentofstudent(@PathVariable int roll_number, Model model){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
               Map<Integer, Object>studentuser = new HashMap<Integer, Object>();
               List<Student>students = studentservice.listAllStudents();
               for(Student student: students) {
                   studentuser.put(student.getRoll_number(), userService.findByUserId(student.getUser_id()));
               }
        	   model.addAttribute("appointments", appointmentService.listAppointmentsStudent(roll_number));
        	   model.addAttribute("studentuser", studentuser);
        	   
               return "appointments_incharge_student";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @GetMapping("/medical/appointments/{appointment_id}")
   public String prescriptionpage(@PathVariable int appointment_id, Model model){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
        	   DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	   Date today = new Date();
       		   Date todayWithZeroTime=new Date();
       		   try {
       			   todayWithZeroTime = formatter.parse(formatter.format(today));
       		   } catch (ParseException e) {
       			e.printStackTrace();
       		   }
       		   int iseditable=1;
        	   Appointment a = appointmentService.getAppointmentbyId(appointment_id);
        	   if((a.getAppointment_date()).compareTo(todayWithZeroTime) < 0)
       			   iseditable=0;
        	   List<Medicine>medicines = medicineService.listMedicinenotprescribed(appointment_id);
        	   List<Prescription>prescription = prescriptionservice.getPrescriptionsOfAppointment(appointment_id);
        	   model.addAttribute("appointment", a);
        	   String desc="";
        	   model.addAttribute("desc", desc);
        	   model.addAttribute("student_details", userService.findByUserId(studentservice.getStudentbyId(a.getStudent_roll_no()).getUser_id()));
        	   model.addAttribute("prescriptions", prescription);
        	   model.addAttribute("medicineservice", medicineService);
        	   model.addAttribute("newprescription", new Prescription());
        	   model.addAttribute("medicines", medicines);
        	   model.addAttribute("iseditable", iseditable); 
               return "prescription_incharge";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @PostMapping("/medical/appointments/{appointment_id}")
   public String prescriptionnew(@PathVariable int appointment_id, @ModelAttribute("newprescription") Prescription prescription){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
        	   prescriptionservice.save(prescription);
        	   String url= "redirect:/medical/appointments/" + appointment_id;
               return url;
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }

   
   @PostMapping("/medical/appointments/editdesc/{appointment_id}")
   public String changedesc(@PathVariable int appointment_id, @ModelAttribute("desc") String desc){
       if(securityService.isLoggedIn()) {
           if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
        	   appointmentService.updateAppointmentDesc(appointment_id, desc);
        	   String url= "redirect:/medical/appointments/" + appointment_id;
               return url;
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @PostMapping("/medical/appointments/edit/{appointment_id}/{med_id}")
   public String edit_prescription(@PathVariable int appointment_id, @PathVariable int med_id, @ModelAttribute("newprescription") Prescription prescription) {
	   if(securityService.isLoggedIn()) {
       if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
        	   prescriptionservice.update(prescription);
        	   String url= "redirect:/medical/appointments/" + appointment_id;
               return url;
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @PostMapping("/medical/appointments/delete/{appointment_id}/{med_id}")
   public String delete_prescription(@PathVariable int appointment_id, @PathVariable int med_id) {
	   if(securityService.isLoggedIn()) {
       if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==2) {
        	   prescriptionservice.delete(appointment_id, med_id);
        	   String url= "redirect:/medical/appointments/" + appointment_id;
               return url;
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
}
