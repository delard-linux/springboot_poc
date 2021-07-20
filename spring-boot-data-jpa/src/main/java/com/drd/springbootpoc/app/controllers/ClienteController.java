package com.drd.springbootpoc.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drd.springbootpoc.app.exception.AppException;
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

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		//binder para el formato de fecha
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "bornAt" ,new CustomDateEditor(dateFormat,true));
				
	}
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		ClienteDTO cliente = clienteService.getCliente(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Detalle cliente");
		return "ver";
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
			Model model, @RequestParam(name = "foto_file") MultipartFile foto, RedirectAttributes  flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(STR_TITULO,"Formulario de Cliente");
			return "form";
		}
		
		if (!foto.isEmpty()) {

			String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
			var rootPath = Paths.get("uploads").resolve(uniqueFilename);

			var rootAbsolutePath = rootPath.toAbsolutePath();
			
			log.info("rootPath: {}", rootPath);
			log.info("rootAbsolutPath: {}",rootAbsolutePath);

			try {

				Files.copy(foto.getInputStream(), rootAbsolutePath);
				
				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

				cliente.setFoto(uniqueFilename);

			} catch (IOException e) {
				e.printStackTrace();
			}
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

	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		var pathFoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
		log.info("pathFoto: {}", pathFoto);
		Resource recurso = null;
		try {
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists() || !recurso.isReadable()) {
				throw new AppException("Error: no se puede cargar la imagen: " + pathFoto.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//TODO: Se podria meter un recurso por defecto apra evitar el operador ternario y la regla sonar
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" 
							+  (recurso != null ? recurso.getFilename():"") 
							+"\"")
				.body(recurso);
	}

}
