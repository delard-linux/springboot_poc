package com.drd.springbootpoc.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			flash.addFlashAttribute(ControllerConstants.FLASH_INFO, "Ya ha inciado sesión anteriormente");
			return ControllerConstants.REDIRECT;
			
		}
		
		if (error != null) {
			model.addAttribute(ControllerConstants.FLASH_ERROR, "Error en el login, nombre de usuario o contraseña incorrecta");
		}

		if (logout != null) {
			model.addAttribute(ControllerConstants.FLASH_SUCCESS, "Ha cerrado sesión con exito");
		}
			
		
		return "login";
	}
}