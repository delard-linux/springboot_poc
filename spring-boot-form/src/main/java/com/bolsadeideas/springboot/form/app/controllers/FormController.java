package com.bolsadeideas.springboot.form.app.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

@Controller
public class FormController {
	
	@GetMapping("/form")
	public String form(Model model) {

		model.addAttribute("titulo","Formulario de usuarios");
		model.addAttribute("usuario",new Usuario());

		return "form";
	}

	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "form";
		}
		
		model.addAttribute("titulo","Resultado del envío del formulario");
		model.addAttribute("usuario",usuario);
		
		return "resultado";
	}

}
