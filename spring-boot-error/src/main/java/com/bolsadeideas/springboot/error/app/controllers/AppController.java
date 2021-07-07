package com.bolsadeideas.springboot.error.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	
	@SuppressWarnings("unused")
	@GetMapping("/index")
	public String index() {
		
		Integer valor = 100/0;
		
		return "index";
	}
	

}
