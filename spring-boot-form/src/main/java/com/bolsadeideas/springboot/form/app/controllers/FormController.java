package com.bolsadeideas.springboot.form.app.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class FormController {
	
	@GetMapping("/form")
	public String form(Model model) {
		return "form";
	}

	@PostMapping("/form")
	public String procesar(Model model) {
		return "resultado";
	}

}
