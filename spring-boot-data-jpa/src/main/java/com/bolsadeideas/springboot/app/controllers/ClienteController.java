package com.bolsadeideas.springboot.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.model.domain.ClienteDTO;
import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Controller
@SessionAttributes("clientedto")
public class ClienteController {

	private static final String STR_TITULO = "titulo";
	private static final String STR_CLIENTE = "clientedto";
	
	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		//binder para el formato de fecha
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "bornAt" ,new CustomDateEditor(dateFormat,true));
//		binder.registerCustomEditor(Date.class, "createAt" ,new CustomDateEditor(dateFormat,true));
				
	}
	
	
	@GetMapping(value={"/index", "/", "", "/listar"})
	public String listar(Model model) {

		model.addAttribute(STR_TITULO, "Listado de Clientes");
		
		List<ClienteDTO> clientes =  new ArrayList<>();
		List<Cliente> clientesEntity =  clienteDao.findAll();
		
		clientesEntity.forEach(cl -> clientes.add(new ClienteDTO(
				cl.getId(),cl.getNombre(), cl.getApellido(), cl.getEmail(), cl.getBornAt(), cl.getCreateAt()
				)));
		
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
		
		clienteDao.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}
	
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, Map<String, Object> model ) {
		
		if(id>0) {
			var clienteEntity = clienteDao.findById(id);
			
			var cliente = new ClienteDTO(
					clienteEntity.getId(),
					clienteEntity.getNombre(), 
					clienteEntity.getApellido(), 
					clienteEntity.getEmail(), 
					clienteEntity.getBornAt(), 
					clienteEntity.getCreateAt()
					);
			
			model.put(STR_CLIENTE, cliente);
			model.put(STR_TITULO,"Formulario de Actualizar Cliente");
		} else {
			return "redirect:listar";
		}
		
		return "form";
	} 
	
	


}
