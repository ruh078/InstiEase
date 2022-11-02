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

import com.dbms.insti.dao.MessChargesDao;
import com.dbms.insti.models.Hostel;
import com.dbms.insti.models.Mess_charges;
import com.dbms.insti.models.Mess_incharge;

import com.dbms.insti.models.Users;
import com.dbms.insti.service.HostelService;
import com.dbms.insti.service.MessChargesService;
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
    @Autowired
    private MessChargesService messchargesService;
    @Autowired
    private MessChargesDao messchargesdao;
    
    @GetMapping("/admin/messin")
    public String adminmess(Model model, RedirectAttributes attributes) {
        if(securityService.isLoggedIn()) {
            if(userService.findByEmail(securityService.findLoggedInUsername()).getRole()==1) {
            	List<Mess_incharge>messes = messService.listAllMesses();
            	List<Hostel>hostels = hostelService.listAllHostels();
            	List<Mess_charges>mess_charges = messchargesService.listAllMesscharges();
                Map<Object, Integer>hostelmess = new HashMap<Object, Integer>();
            	Map<Integer, Object>messuser = new HashMap<Integer, Object>();
            	Map<String,Object>messchargefetch = new HashMap<String, Object>();

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
                model.addAttribute("mess_charges", mess_charges);
                model.addAttribute("newmesscharge", new Mess_charges());
                if(mess_charges.size()!=3) {
                	model.addAttribute("add_charge", 1);
                }
                
                model.addAttribute("bcost", 0);
            	model.addAttribute("lcost", 0);
            	model.addAttribute("dcost", 0);
                return "admin_mess";
            }
            return "redirect:/";
        }
        attributes.addFlashAttribute("msg", "Not Logged In!");
        return "redirect:/login";
    }
    
    
    
    @PostMapping({"/admin/messin"})
    public String addmess(@ModelAttribute("newUser") Users user, @ModelAttribute("newMess") Mess_incharge mess, Model model, RedirectAttributes attributes) {
    	user.setRole(5);
       	int x = userService.save(user);
       	if(x==0){
            attributes.addFlashAttribute("msg", "Invalid Details!");
       		return "redirect:/admin/messin";
        }
       	System.out.println("abcdefg");
       	System.out.println(mess.getHostel_id());
       	mess.setUser_id(userService.findByEmail(user.getEmail_id()).getUser_id());
       	System.out.println(mess.getHostel_id());
		messService.save(mess);
        attributes.addFlashAttribute("msg", "Successfully add new mess incharge!");
        return "redirect:/admin/messin";
    }
    
    @PostMapping({"/admin/messin/edit/{id}"})
    public String editmess(@PathVariable("id") int id, @ModelAttribute("newUser") Users user, Model model,
            RedirectAttributes attributes) {
        user.setUser_id(messService.findbyhostelid(id).getUser_id());
        user.setRole(5);
        user.setEmail_id(userService.findByUserId((messService.findbyhostelid(id)).getUser_id()).getEmail_id());
        user.setPsw(userService.findByUserId((messService.findbyhostelid(id)).getUser_id()).getPsw());
        userService.edit(user);
        attributes.addFlashAttribute("msg", "Successfully updated details!");
        return "redirect:/admin/messin";
    }  
    
    @PostMapping({"/admin/messin/edit2/{meal_type}"})
    public String editmesscharge(@PathVariable("meal_type") String meal_type, @ModelAttribute("newmesscharge") Mess_charges mess_charges, Model model,
            RedirectAttributes attributes) {
        mess_charges.setMeal_type(meal_type);
        messchargesService.edit(mess_charges);
        attributes.addFlashAttribute("msg", "Successfully updated details!");
        return "redirect:/admin/messin";
    }  
    
    @PostMapping("/admin/messin/addcharge")
    public String addmesscharge(@ModelAttribute("bcost") int breakfast,  @ModelAttribute("lcost") String l, @ModelAttribute("dcost") String d, Model model,
            RedirectAttributes attributes) {
    		Mess_charges mess_charges = new Mess_charges();
        	//mess_charges.setMeal_type("breakfast");
    		//mess_charges.setCost(b);
        	/*if(messchargesdao.getcharge("breakfast")!=null) {
        		messchargesService.edit(mess_charges);
        	}
        	else {
        		messchargesService.add(mess_charges);
        	}
        	mess_charges.setMeal_type("lunch");
    		mess_charges.setCost(l);
        	if(messchargesdao.getcharge("lunch")!=null) {
        		messchargesService.edit(mess_charges);
        	}
        	else {
        		messchargesService.add(mess_charges);
        	}
        	mess_charges.setMeal_type("dinner");
    		mess_charges.setCost(b=d);
        	if(messchargesdao.getcharge("dinner")!=null) {
        		messchargesService.edit(mess_charges);
        	}
        	else {
        		messchargesService.add(mess_charges);
        	}*/
        	attributes.addFlashAttribute("msg", "Successfully updated details!");
        	return "redirect:/admin/messin";
    }  
    
    @GetMapping("/admin/messin/delete/{id}")
    public String deletemess(@PathVariable("id") int id, Model model, RedirectAttributes attributes) {
        int x = messService.delete(id);
        if(x==1)
        attributes.addFlashAttribute("msg", "Successfully deleted details!");
        else
        	attributes.addFlashAttribute("msg", "Cannot delete this mess incharge!");
        return "redirect:/admin/messin";
    }  
}
