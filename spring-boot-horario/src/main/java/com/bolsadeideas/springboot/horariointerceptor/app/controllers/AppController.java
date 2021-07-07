package com.bolsadeideas.springboot.horariointerceptor.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsadeideas.springboot.horariointerceptor.app.services.IHorarioAperturaService;

@Controller
public class AppController {
	
	@Value("${config.horario.apertura}")
	private String apertura;

	@Value("${config.horario.cierre}")
	private String cierre;
	
	@Autowired
	private IHorarioAperturaService horarioApertura;
	
	@GetMapping({"/","index"})
	public String index(Model model) {
		
		model.addAttribute("titulo", "Bienvenido al horario de atencion a clientes");
		
		return "index";
	}

	@GetMapping({"/cerrado"})
	public String cerrado(Model model) {
		
		boolean esHorarioAtencion = horarioApertura.esHorarioDeAtencion();

		if (!esHorarioAtencion) {
		
		model.addAttribute("titulo", "Bienvenido al horario de atencion a clientes");
		
		var mensaje = new StringBuilder("Cerrado, el horario de atencion al cliente ");
		mensaje.append("es desde las ");
		mensaje.append(apertura);
		mensaje.append(" hrs. hasta las ");
		mensaje.append(cierre);
		mensaje.append(" hrs. Gracias por su visita");
		
		model.addAttribute("horarioMensaje", mensaje.toString());

		return "cerrado";
		} 
		return "error";
		
	}
	
}
