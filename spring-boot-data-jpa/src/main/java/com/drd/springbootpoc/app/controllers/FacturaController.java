package com.drd.springbootpoc.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.domain.FacturaDTO;
import com.drd.springbootpoc.app.model.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value="clienteId") Long clienteId,
			Map<String, Object> model,
			RedirectAttributes flash) {
		
		ClienteDTO cliente = clienteService.obtenerCliente(clienteId);
		
		if (cliente==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:listar";
		}
		
		var factura = new FacturaDTO();
		factura.setCliente(cliente);
		model.put("factura", factura);
		model.put("titulo", "Crear factura");
		
		return "factura/form";
	}

	
	
	
}
