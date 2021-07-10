package com.bolsadeideas.springboot.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.bolsadeideas.springboot.app.model.domain.ClienteDTO;
import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;


@Controller
public class ClienteController {

	private static final String STR_TITULO = "titulo";
	private static final String STR_CLIENTE = "cliente";
	
	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		//binder para el formato de fecha
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "bornAt" ,new CustomDateEditor(dateFormat,true));
		binder.registerCustomEditor(Date.class, "createAt" ,new CustomDateEditor(dateFormat,true));
				
	}
	
	
	@GetMapping(value={"/index", "/", "", "/listar"})
	public String listar(Model model) {

		model.addAttribute(STR_TITULO, "Listado de Clientes");
		model.addAttribute("clientes", clienteDao.findAll());

		return "listar";
	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		var cliente = new Cliente();
		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Formulario de Cliente");
		return "form";
	}

	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("cliente") ClienteDTO cliente, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute(STR_TITULO,"Formulario de Cliente");
			return "form";
		}
		
		clienteDao.save(cliente);
		return "redirect:listar";
	}
	
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, Map<String, Object> model ) {
		
		if(id>0) {
			var cliente = clienteDao.findById(id);
			model.put(STR_CLIENTE, cliente);
			model.put(STR_TITULO,"Formulario de Actualizar Cliente");
		} else {
			return "redirect:listar";
		}
		
		return "form";
	} 
	
	


}
