package com.bolsadeideas.springboot.web.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/variables")
public class EjemploVariablesRutaController {

	
	@GetMapping("/string/{texto}")
	public String variable(@PathVariable(name="texto") String texto, 
						Model model) {

		model.addAttribute("titulo", "Recibir Parametros de la ruta (@PathVariable)");
		model.addAttribute("resultado", "El texto enviado en la ruta es: "+ texto);

		return "variables/ver";
		
	}
	
}
