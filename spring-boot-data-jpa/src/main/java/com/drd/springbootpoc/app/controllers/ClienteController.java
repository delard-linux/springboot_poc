package com.drd.springbootpoc.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.service.IClienteService;

@Controller
@SessionAttributes("clientedto")
public class ClienteController {

	private static final String STR_TITULO = "titulo";
	private static final String STR_CLIENTE = "clientedto";
	
	@Autowired
	//@Qualifier("clienteDaoJPA")
	private IClienteService clienteService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		//binder para el formato de fecha
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "bornAt" ,new CustomDateEditor(dateFormat,true));
				
	}
	
	
	@GetMapping(value={"/index", "/", "", "/listar"})
	public String listar(Model model) {

		model.addAttribute(STR_TITULO, "Listado de Clientes");
		
		List<ClienteDTO> clientes = clienteService.getAllClientes();  
		
		model.addAttribute("clientedtolist", clientes);

		return "listar";
	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		var cliente = new ClienteDTO();
		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Formulario de Cliente");
		return "form";
	}

	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("clientedto") ClienteDTO cliente, BindingResult result, Model model, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(STR_TITULO,"Formulario de Cliente");
			return "form";
		}
		
		clienteService.saveCliente(cliente);
		
		status.setComplete();
		return "redirect:/listar";
	}
	
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, Map<String, Object> model ) {
		
		if(id>0) {
			
			var cliente = clienteService.getCliente(id);
			
			model.put(STR_CLIENTE, cliente);
			model.put(STR_TITULO,"Formulario de Actualizar Cliente");

		} else {
			return "redirect:/listar";
		}
		
		return "form";
	} 
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name="id") Long id, Map<String, Object> model, SessionStatus status) {
		
		if(id>0) {
			clienteService.deleteCliente(id);
		} 
		status.setComplete();
		return "redirect:/listar";
	} 	


}