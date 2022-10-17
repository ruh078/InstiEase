package com.dbms.insti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbms.insti.service.SecurityService;
import com.dbms.insti.service.UserService;

@Controller
public class AdminWardenController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @GetMapping("/admin/warden")
    public String adminwarden(Model model) {
    	return "admin_warden";
    }
}
