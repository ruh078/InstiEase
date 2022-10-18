package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dbms.insti.models.Medicine;
import com.dbms.insti.service.MedicineService;
import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

@Controller
public class MedicalContoller {
	@Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService; 
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
        	   model.addAttribute("newmedicine", new Medicine());
               return "medicines";
           }
           return "redirect:/";
       }
       
       return "redirect:/login";
       
   }
   
   @PostMapping({"/medical/medicine"})
   public String addhostel(@ModelAttribute("newmedicine") Medicine medicine, Model model, RedirectAttributes attributes) {
          medicineService.save(medicine);
          return "redirect:/medical/medicine";
   }
}
