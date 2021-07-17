package com.drd.springbootpoc.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.service.IClienteService;
import com.drd.springbootpoc.app.util.paginator.Pagina;
import com.drd.springbootpoc.app.util.paginator.PaginaRender;

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
	public String listar(@RequestParam(name="page", defaultValue="0") int page,  Model model) {


		Pageable pageRequest = PageRequest.of(page,5);	
		
		Pagina<ClienteDTO> clientes = clienteService.getAllClientes(pageRequest);  

		PaginaRender<ClienteDTO> paginaRender = new PaginaRender<>("/listar", clientes);
		
		model.addAttribute(STR_TITULO, "Listado de Clientes");
		model.addAttribute("clientedtolist", clientes.getContenido());
		model.addAttribute("pagina", paginaRender);

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
	public String guardar(@Valid @ModelAttribute("clientedto") ClienteDTO cliente, BindingResult result, 
			Model model, RedirectAttributes  flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(STR_TITULO,"Formulario de Cliente");
			return "form";
		}
		
		clienteService.saveCliente(cliente);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Cliente salvado con exito!");
		return "redirect:/listar";
	}
	
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash ) {
		
		if(id>0) {
			
			var cliente = clienteService.getCliente(id);
			
			model.put(STR_CLIENTE, cliente);
			model.put(STR_TITULO,"Formulario de Actualizar Cliente");
			
			if (cliente == null) {
				flash.addFlashAttribute("error", "Cliente inexistente con el ID: " + id);
				return "redirect:/listar";
			}

		} else {
			flash.addFlashAttribute("error", "El ID de cliente no puede ser cero o negativo: " + id);
			return "redirect:/listar";
		}
		
		return "form";
	} 
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash,  SessionStatus status) {
		
		if(id>0) {
			clienteService.deleteCliente(id);
		} 
		status.setComplete();
		flash.addFlashAttribute("success", "Cliente eliminado con exito!");
		return "redirect:/listar";
	} 	


}
