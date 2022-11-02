package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Complaints;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Student;
import com.dbms.insti.models.Users;
import com.dbms.insti.models.Warden;
import com.dbms.insti.service.AppointmentService;
import com.dbms.insti.service.ComplaintService;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.MedicineService;
import com.dbms.insti.service.PrescriptionService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.StudentService;
import com.dbms.insti.service.UserService;
import com.dbms.insti.service.WardenService;

@Controller
public class WardenController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private WardenService wardenService;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentservice;
    @Autowired
    private HostelService hostelservice;    
    @Autowired
    private ComplaintService complaintservice;
    
    @GetMapping("/warden")
    public String profilepage(Model model, RedirectAttributes attributes){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==4) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   Warden warden = wardenService.findbyUserId(user.getUser_id());
                   Hostel hostel = hostelservice.getHostelbyId(warden.getHostel_id());          
                   model.addAttribute("hostel",hostel);
                   model.addAttribute("user",user);
                   model.addAttribute("warden",warden);
                   model.addAttribute("newuser",new Users());
                   return "warden";
               }
               return "redirect:/";
           }
           attributes.addFlashAttribute("msg", "Not Logged In!");

           return "redirect:/login";
    }

    @GetMapping("/warden/complaints")
    public String complainspage(Model model, RedirectAttributes attributes){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==4) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   
                   Student student = studentservice.getStudentbyUserId(user.getUser_id());
                   
                   Warden warden = wardenService.findbyUserId(user.getUser_id());
                   model.addAttribute("all_complaints", complaintservice.listAllComplaints(warden.getHostel_id()));
                   model.addAttribute("service", studentservice);
                   model.addAttribute("userservice", userService);
                   model.addAttribute("public_complaints", complaintservice.listPublicComplaints(warden.getHostel_id()));
                   model.addAttribute("resolved_complaints", complaintservice.listResolvedComplaints(warden.getHostel_id()));
                   model.addAttribute("approvedandnotresolved_complaints", complaintservice.listApprovedandUnresolvedComplaints(warden.getHostel_id()));
                   model.addAttribute("yettoapprove_complaints", complaintservice.listUnapprovedComplaints(warden.getHostel_id()));
                   model.addAttribute("newcomplaint", new Complaints());
                   return "warden_complaints";
               }
               return "redirect:/";
           }
           attributes.addFlashAttribute("msg", "Not Logged In!");

           return "redirect:/login";
    }
    
    @PostMapping("/warden/complaint/edit/{com_id}")
    public String submitstatuspage(@ModelAttribute("newcomplaint") Complaints complaint, @PathVariable int com_id, RedirectAttributes attributes){
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==4) {
                 complaint.setComplaint_id(com_id);
                 complaintservice.changestatus(complaint);                                             
                return "redirect:/warden/complaints";
            }
            return "redirect:/";
        }
        attributes.addFlashAttribute("msg", "Not Logged In!");
        
        return "redirect:/login";
 }
    @GetMapping("/warden/all")
    public String allstudentspage(Model model, RedirectAttributes attributes){
           if(securityService.isLoggedIn()) {
               if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==4) {
                   Users user = userService.findByEmail(securityService.findLoggedInUsername());
                   
                   Student student = studentservice.getStudentbyUserId(user.getUser_id());
                   
                   Warden warden = wardenService.findbyUserId(user.getUser_id());
                   model.addAttribute("service", studentservice);
                   model.addAttribute("students", studentservice.listAllStudentsofHostel(warden.getHostel_id()));
                   model.addAttribute("userservice", userService);
                  
                   return "warden_allstudents";
               }
               return "redirect:/";
           }
           attributes.addFlashAttribute("msg", "Not Logged In!");
           
           return "redirect:/login";
    }
    
    @PostMapping("/warden/edit/{id}")
    public String editwarden(@PathVariable("id") int id, @ModelAttribute ("newuser") Users user, Model model,
            RedirectAttributes attributes) {
    	user.setUser_id(id);
    	user.setRole(4);
        user.setEmail_id(userService.findByUserId(id).getEmail_id());
        user.setPsw(userService.findByUserId(id).getPsw());
    	userService.edit(user);
        attributes.addFlashAttribute("msg", "Updated Details!");

    	return "redirect:/warden";
    }
    
    @PostMapping("/warden/changepsw/{id}")
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
	      
	       return "redirect:/warden";
	   }
}
