package com.bolsadeideas.springboot.web.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/params")
public class EjemploParamsController {

	private static final String ATTRIBUTE_RESULTADO = "resultado";
	
	private static final String VIEW_INDEX = "params/index";
	private static final String VIEW_VER = "params/ver";
	
	@GetMapping(value = {"/index", "/", ""})
	public String params(Model model) {

		return VIEW_INDEX;
		
	}

	
	@GetMapping("/string")
	public String param(@RequestParam(name = "texto", required = false, defaultValue = "VALOR POR DEFECTO") String texto, 
						Model model) {

		model.addAttribute(ATTRIBUTE_RESULTADO, "El texto enviado es: "+ texto);

		return VIEW_VER;
		
	}
	
	@GetMapping("/mix-params")
	public String param(@RequestParam String saludo,
						@RequestParam Integer numero,
						Model model) {

		model.addAttribute(ATTRIBUTE_RESULTADO, "El texto enviado es: '"+ saludo + "' y el número es '" + numero + "'");

		return VIEW_VER;
		
	}

	@GetMapping("/mix-params-request")
	public String param(HttpServletRequest request,
						Model model) {
		
		String saludo = request.getParameter("saludo");
		Integer numero = null;
		try {
			numero = Integer.parseInt(request.getParameter("numero"));
		} catch (NumberFormatException e) {
			numero = 0;
		} 
					 
		model.addAttribute(ATTRIBUTE_RESULTADO, "El texto enviado es: '"+ saludo + "' y el número es '" + numero + "'");

		return VIEW_VER;
		
	}
	
	
	
}
