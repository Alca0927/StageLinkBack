package com.pro.stagelink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/s/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/s/loginfailed")
	public String loginerror(Model model) {
	model.addAttribute("error","true");
	return "login";
	}
}
