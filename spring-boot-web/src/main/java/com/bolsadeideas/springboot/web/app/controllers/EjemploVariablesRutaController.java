package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables")
public class EjemploVariablesRutaController {

	private static final String ATTRIBUTE_TITULO = "titulo";
	private static final String ATTRIBUTE_RESULTADO = "resultado";

	private static final String VIEW_INDEX = "variables/index";
	private static final String VIEW_VER = "variables/ver";
	
	@GetMapping(value = {"/index", "/", ""})
	public String params(Model model) {
		model.addAttribute(ATTRIBUTE_TITULO, "Enviar Parametros de la ruta (@PathVariable)");
		return VIEW_INDEX;
		
	}
	
	
	@GetMapping("/string/{texto}")
	public String variables(@PathVariable(name="texto") String texto, 
						Model model) {

		model.addAttribute(ATTRIBUTE_TITULO, "Recibir Parametros de la ruta (@PathVariable)");
		model.addAttribute(ATTRIBUTE_RESULTADO, "El texto enviado en la ruta es: "+ texto);

		return VIEW_VER;
		
	}

	@GetMapping("/string/{texto}/{numero}")
	public String variables(@PathVariable(name="texto") String texto, 
						@PathVariable(name="numero") Integer numero,
						Model model) {

		model.addAttribute(ATTRIBUTE_TITULO, "Recibir Parametros de la ruta (@PathVariable)");
		model.addAttribute(ATTRIBUTE_RESULTADO, "El texto enviado en la ruta es '"+ texto + "' y el numero enviado en el path es '" + numero + "'");

		return VIEW_VER;
		
	}
	
}
