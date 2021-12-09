package com.drd.springbootpoc.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
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

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.domain.ClienteSearchCriteria;
import com.drd.springbootpoc.app.model.service.IClienteService;
import com.drd.springbootpoc.app.model.service.IUploadFileService;
import com.drd.springbootpoc.app.util.paginator.Pagina;
import com.drd.springbootpoc.app.util.paginator.PaginaRender;

@Controller
@SessionAttributes(names = {"clientedto","cl_search_crit"})
public class ClienteController {

	
	// Constantes de nombres de vistas
	private static final String VIEW_VER 	= "ver";
	private static final String VIEW_LISTAR = "listar";
	private static final String VIEW_FORM 	= "form";
	
	private static final int NUM_PAGE_ELEMENTS  = 20;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	//@Qualifier("clienteDaoJPA")
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		//binder para el formato de fecha
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "bornAt" ,new CustomDateEditor(dateFormat,true));
				
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, 
			Map<String, Object> model, 
			RedirectAttributes flash,
			Locale locale) {

		ClienteDTO cliente = clienteService.obtenerClienteConFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute(ConstantesController.FLASH_ERROR, "El cliente no existe en la base de datos");
			return ConstantesController.REDIRECT + VIEW_LISTAR;
		}

		model.put(ConstantesController.ATT_CLIENTE, cliente);
		model.put(ConstantesController.ATT_TITULO, messageSource.getMessage(ConstantesController.TXT_CLIENTE_TITULO_VER, null, locale));
		return VIEW_VER;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping(value={"/", "/listar"})
	public String listar(@RequestParam(name="page", defaultValue="0") int page,  
			Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {

		// Ejemplo de como realizar operaciones con el usuario autenticado, por parametro o estaticamente
		if(authentication != null) {
			log.info("Hola, tu username es: {}", authentication.getName());
		}

		var auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			log.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: '{}'", auth.getName());
			
			// Control de los roles del usuario autenticado programaticamente
			if(hasRole("ROLE_USER")) {
				log.info("Hola '{}', tienes acceso", auth.getName());
			} else {
				log.info("Hola '{}', NO tienes acceso", auth.getName());
			}
			
			
			// Control de los roles del usuario autenticado programaticamente con el wrapper
			var securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
			
			if(securityContext.isUserInRole("ROLE_ADMIN")) {
				log.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola '{}' tienes acceso!", securityContext.getUserPrincipal().getName());
			} else {
				log.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola '{}' NO tienes acceso!", securityContext.getUserPrincipal().getName());
			}

			if(request.isUserInRole("ROLE_ADMIN")) {
				log.info("Forma usando HttpServletRequest: Hola '{}' tienes acceso!", auth.getName());
			} else {
				log.info("Forma usando HttpServletRequest: Hola '{}' NO tienes acceso!", auth.getName());
			}
			
			
		}
		
		Pageable pageRequest = PageRequest.of(page,NUM_PAGE_ELEMENTS);	
		
		Pagina<ClienteDTO> clientes = clienteService.obtenerTodosClientes(pageRequest);  

		PaginaRender<ClienteDTO> paginaRender = new PaginaRender<>("/listar", clientes);
		
		model.addAttribute(ConstantesController.ATT_TITULO, messageSource.getMessage(ConstantesController.TXT_CLIENTE_TITULO_VER, null, locale));
		model.addAttribute(ConstantesController.ATT_CLIENTE_LIST, clientes.getContenido());
		model.addAttribute(ConstantesController.ATT_PAGINA, paginaRender);		
		model.addAttribute(ConstantesController.ATT_CLIENTE_SEARCH_CRIT, new ClienteSearchCriteria("", "", ""));

		return VIEW_LISTAR;
	}

	@Secured({"ROLE_USER","ROLE_ADMIN",})
	@GetMapping("/buscar")
	public String buscar(@ModelAttribute("cl_search_crit") ClienteSearchCriteria criteria, 
			@RequestParam(name="page", defaultValue="0") int page,  
			Model model,
			Locale locale) {

		Pageable pageRequest = PageRequest.of(page,NUM_PAGE_ELEMENTS);	
		
		Pagina<ClienteDTO> clientes = clienteService.obtenerTodosClientesCriteria(pageRequest, criteria);  

		PaginaRender<ClienteDTO> paginaRender = new PaginaRender<>("/buscar", clientes);
		
		model.addAttribute(ConstantesController.ATT_TITULO, messageSource.getMessage(ConstantesController.TXT_CLIENTE_TITULO_LISTAR, null, locale));
		model.addAttribute(ConstantesController.ATT_CLIENTE_LIST, clientes.getContenido());
		model.addAttribute(ConstantesController.ATT_PAGINA, paginaRender);
		model.addAttribute(ConstantesController.ATT_CLIENTE_SEARCH_CRIT, criteria);
		
		return VIEW_LISTAR;
	}
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Map<String, Object> model, Locale locale) {
		var cliente = new ClienteDTO();
		model.put(ConstantesController.ATT_CLIENTE, cliente);
		model.put(ConstantesController.ATT_TITULO, messageSource.getMessage(ConstantesController.TXT_CLIENTE_TITULO_CREAR, null, locale));
		return VIEW_FORM;
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("clientedto") ClienteDTO cliente, BindingResult result, 
			Model model, @RequestParam(name = "foto_file") MultipartFile foto, 
			RedirectAttributes  flash, SessionStatus status, Locale locale) {
		
		if(result.hasErrors()) {
			model.addAttribute(ConstantesController.ATT_TITULO, messageSource.getMessage(ConstantesController.TXT_CLIENTE_TITULO_CREAR, null, locale));
			return VIEW_FORM;
		}
				
		Long idCliente = null;
		try {
			idCliente = clienteService.guardarCliente(cliente, foto);
		} catch (IOException e) {
			log.error("No se puede guardar el cliente: {} {}", cliente.getNombre(),cliente.getApellido());
			log.error(e.getMessage(),e);
			flash.addFlashAttribute(ConstantesController.FLASH_ERROR, "No se puede guardar el cliente: " 
							+ cliente.getNombre() + " " + cliente.getApellido());
			return ConstantesController.REDIRECT + VIEW_LISTAR;
		}
		
		status.setComplete();
		flash.addFlashAttribute(ConstantesController.FLASH_SUCCESS, "Cliente guardado con exito '" 
				+ idCliente + "'!");
		return ConstantesController.REDIRECT + VIEW_LISTAR;
	}
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash, Locale locale ) {
		
		if(id>0) {
			
			var cliente = clienteService.obtenerCliente(id);
			
			model.put(ConstantesController.ATT_CLIENTE, cliente);
			model.put(ConstantesController.ATT_TITULO,messageSource.getMessage(ConstantesController.TXT_CLIENTE_TITULO_ACTUALIZAR, null, locale));
			
			if (cliente == null) {
				flash.addFlashAttribute(ConstantesController.FLASH_ERROR, "Cliente inexistente con el ID: " + id);
				return ConstantesController.REDIRECT + VIEW_LISTAR;
			}

		} else {
			flash.addFlashAttribute(ConstantesController.FLASH_ERROR, "El ID de cliente no puede ser cero o negativo: " + id);
			return ConstantesController.REDIRECT + VIEW_LISTAR;
		}
		
		return VIEW_FORM;
	} 
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash,  SessionStatus status) {
		
		if(id>0) {
			
			var cliente = clienteService.obtenerCliente(id);
			
			if (cliente!= null) {
				try {
					clienteService.borrarCliente(id);
				} catch (IOException e) {
					log.error("No se puede eliminar el cliente: {}", id);
					log.error(e.getMessage(),e);
					flash.addFlashAttribute(ConstantesController.FLASH_ERROR, "No se puede eliminar el cliente: " + id);
					return ConstantesController.REDIRECT + VIEW_LISTAR;
				}
				flash.addFlashAttribute(ConstantesController.FLASH_INFO, "Cliente eliminado con exito!");
			} else {
				flash.addFlashAttribute(ConstantesController.FLASH_ERROR, "Cliente inexistente: " + id);
			}

		} 
		status.setComplete();
		return ConstantesController.REDIRECT + VIEW_LISTAR;
	} 	

	@Secured("ROLE_ADMIN")
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFichero(@PathVariable String filename) {
		
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			log.error("No se puede obtener el fichero: {}", filename);
			log.error(e.getMessage(),e);
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" 
							+  (recurso != null ? recurso.getFilename():"") 
							+"\"")
				.body(recurso);
	}
	
	// este método se puede eliminar, es PoC para autorización programática
	private boolean hasRole(String role) {
		
		var context = SecurityContextHolder.getContext();
		if(context == null) 
			return false;
		
		var auth = context.getAuthentication();
		if(auth == null) 
			return false;
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		authorities.forEach(item -> log.info("Hola '{}', tu rol es '{}'", auth.getName(), item.getAuthority()));
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
	}	

}
