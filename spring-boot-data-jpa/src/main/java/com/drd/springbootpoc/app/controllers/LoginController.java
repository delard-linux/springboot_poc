package com.drd.springbootpoc.app.controllers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@Autowired
    private MessageSource messageSource;
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			Model model, Principal principal, RedirectAttributes flash, Locale locale) {
		
		if(principal != null) {
			flash.addFlashAttribute(ControllerConstants.FLASH_INFO, 
					messageSource.getMessage(ControllerConstants.TXT_LOGIN_ALREADY, null, locale));
			return ControllerConstants.REDIRECT;
		}
		
		if (error != null) {
			model.addAttribute(ControllerConstants.FLASH_ERROR,
					messageSource.getMessage(ControllerConstants.TXT_LOGIN_ERROR, null, locale));
		}

		if (logout != null) {
			model.addAttribute(ControllerConstants.FLASH_SUCCESS, 
					messageSource.getMessage(ControllerConstants.TXT_LOGIN_LOGOUT, null, locale));
		}
		
		return "login";
	}
}