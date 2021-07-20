package com.drd.springbootpoc.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

	// Constantes de atributos
	private static final String STR_TITULO = "titulo";
	private static final String STR_CLIENTE = "clientedto";

	// Constantes de folders
	private static final String FOLDER_UPLOADS = "uploads";

	// Constantes de vistas
	private static final String STR_REDIRECT = "redirect:/";
	private static final String VIEW_VER = "ver";
	private static final String VIEW_LISTAR = "listar";
	private static final String VIEW_FORM = "form";
	
	// Constantes de tipos de mensajes flash
	private static final String FLASH_SUCCESS = "success";
	private static final String FLASH_INFO = "info";
	private static final String FLASH_ERROR = "error";
	
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

		ClienteDTO cliente = clienteService.obtenerCliente(id);
		if (cliente == null) {
			flash.addFlashAttribute(FLASH_ERROR, "El cliente no existe en la base de datos");
			return STR_REDIRECT + VIEW_LISTAR;
		}

		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Detalle cliente");
		return VIEW_VER;
	}
	
	@GetMapping(value={"/index", "/", "", "/listar"})
	public String listar(@RequestParam(name="page", defaultValue="0") int page,  Model model) {


		Pageable pageRequest = PageRequest.of(page,5);	
		
		Pagina<ClienteDTO> clientes = clienteService.obtenerTodosClientes(pageRequest);  

		PaginaRender<ClienteDTO> paginaRender = new PaginaRender<>("/listar", clientes);
		
		model.addAttribute(STR_TITULO, "Listado de Clientes");
		model.addAttribute("clientedtolist", clientes.getContenido());
		model.addAttribute("pagina", paginaRender);

		return VIEW_LISTAR;
	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		var cliente = new ClienteDTO();
		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Formulario de Cliente");
		return VIEW_FORM;
	}

	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("clientedto") ClienteDTO cliente, BindingResult result, 
			Model model, @RequestParam(name = "foto_file") MultipartFile foto, RedirectAttributes  flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(STR_TITULO,"Formulario de Cliente");
			return VIEW_FORM;
		}
				
		Long idCliente = cliente.getId();
		
		String nombreFichero = null;
		InputStream streamFichero = null;

		try {
			if (foto!=null 
					&& foto.getOriginalFilename()!=null) {				
				nombreFichero = foto.getOriginalFilename();
				streamFichero = foto.getInputStream();	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flash.addFlashAttribute(FLASH_ERROR, "No es posible cargar la foto '" 
					+ nombreFichero + "'!");
			return STR_REDIRECT + VIEW_LISTAR;
		}		

		if (idCliente == null) {
			if (nombreFichero!=null && !nombreFichero.isBlank()) 
				idCliente = clienteService.crearClienteConFoto(cliente, nombreFichero, streamFichero);
			else
				idCliente = clienteService.crearCliente(cliente);
		} else {
			if (nombreFichero!=null  && !nombreFichero.isBlank()) 
				clienteService.actualizarClienteConFoto(cliente, nombreFichero, streamFichero);
			else 
				clienteService.actualizarCliente(cliente);
		}

			
		status.setComplete();
		flash.addFlashAttribute(FLASH_SUCCESS, "Cliente salvado con exito '" 
				+ idCliente + "'!");
		return STR_REDIRECT + VIEW_LISTAR;
	}
	
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash ) {
		
		if(id>0) {
			
			var cliente = clienteService.obtenerCliente(id);
			
			model.put(STR_CLIENTE, cliente);
			model.put(STR_TITULO,"Formulario de Actualizar Cliente");
			
			if (cliente == null) {
				flash.addFlashAttribute(FLASH_ERROR, "Cliente inexistente con el ID: " + id);
				return STR_REDIRECT + VIEW_LISTAR;
			}

		} else {
			flash.addFlashAttribute(FLASH_ERROR, "El ID de cliente no puede ser cero o negativo: " + id);
			return STR_REDIRECT + VIEW_LISTAR;
		}
		
		return VIEW_FORM;
	} 
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash,  SessionStatus status) {
		
		if(id>0) {
			
			var cliente = clienteService.obtenerCliente(id);
			
			if (cliente!= null) {
				if(clienteService.borrarFotoCliente(id)) 
					flash.addFlashAttribute(FLASH_INFO, "Foto " 
								+ cliente.getFoto()
								+ " eliminada con exito!");
				clienteService.borrarCliente(id);
				flash.addFlashAttribute(FLASH_SUCCESS, "Cliente eliminado con exito!");
			} else {
				flash.addFlashAttribute(FLASH_SUCCESS, "Cliente inexistente: " + id);
			}

		} 
		status.setComplete();
		return STR_REDIRECT + VIEW_LISTAR;
	} 	

	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		var pathFoto = Paths.get(FOLDER_UPLOADS).resolve(filename).toAbsolutePath();
		log.info("pathFoto: {}", pathFoto);
		Resource recurso = null;
		try {
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists() || !recurso.isReadable()) {
				//TODO meter algun tipo de AOP/Interceptor para el control de excepciones de aplicacion
				throw new AppException("Error: no se puede cargar la imagen: " + pathFoto.toString());
			}
		} catch (MalformedURLException e) {
			//TODO pintar la excepción y la traza a fichero de errores
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
