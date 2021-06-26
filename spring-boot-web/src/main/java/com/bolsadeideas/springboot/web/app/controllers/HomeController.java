package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		return "forward:/app/index";
	}

	@GetMapping("/redir")
	public String homeRedirect() {
		return "redirect:/app/index";
	}
	
	@GetMapping("/go")
	public String homeGoogle() {
		return "redirect:https://www.google.com";
	}
	
}
