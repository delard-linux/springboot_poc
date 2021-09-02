package com.drd.springbootpoc.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

	// Constantes de atributos
	private static final String STR_TITULO = "titulo";
	private static final String STR_CLIENTE = "clientedto";

	// Constantes de vistas
	private static final String STR_REDIRECT = "redirect:/";
	private static final String VIEW_VER = "ver";
	private static final String VIEW_LISTAR = "listar";
	private static final String VIEW_FORM = "form";
	
	// Constantes de tipos de mensajes flash
	private static final String FLASH_SUCCESS = "success";
	private static final String FLASH_INFO = "info";
	private static final String FLASH_ERROR = "error";
	
	private static final int NUM_PAGE_ELEMENTS  = 20;
	
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
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		ClienteDTO cliente = clienteService.obtenerClienteConFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute(FLASH_ERROR, "El cliente no existe en la base de datos");
			return STR_REDIRECT + VIEW_LISTAR;
		}

		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Detalle cliente");
		return VIEW_VER;
	}
	
	@GetMapping(value={"/", "/listar"})
	public String listar(@RequestParam(name="page", defaultValue="0") int page,  Model model) {


		Pageable pageRequest = PageRequest.of(page,NUM_PAGE_ELEMENTS);	
		
		Pagina<ClienteDTO> clientes = clienteService.obtenerTodosClientes(pageRequest);  

		PaginaRender<ClienteDTO> paginaRender = new PaginaRender<>("/listar", clientes);
		
		model.addAttribute(STR_TITULO, "Listado de Clientes");
		model.addAttribute("clientedtolist", clientes.getContenido());
		model.addAttribute("pagina", paginaRender);		
		model.addAttribute("cl_search_crit", new ClienteSearchCriteria("", "", ""));

		return VIEW_LISTAR;
	}

	@GetMapping("/buscar")
	public String buscar(@ModelAttribute("cl_search_crit") ClienteSearchCriteria criteria, @RequestParam(name="page", defaultValue="0") int page,  Model model) {

		Pageable pageRequest = PageRequest.of(page,NUM_PAGE_ELEMENTS);	
		
		Pagina<ClienteDTO> clientes = clienteService.obtenerTodosClientesCriteria(pageRequest, criteria);  

		PaginaRender<ClienteDTO> paginaRender = new PaginaRender<>("/buscar", clientes);
		
		model.addAttribute(STR_TITULO, "Listado de Clientes");
		model.addAttribute("clientedtolist", clientes.getContenido());
		model.addAttribute("pagina", paginaRender);
		model.addAttribute("cl_search_crit", criteria);

		return VIEW_LISTAR;
	}
	
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		var cliente = new ClienteDTO();
		model.put(STR_CLIENTE, cliente);
		model.put(STR_TITULO, "Crear Cliente");
		return VIEW_FORM;
	}

	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("clientedto") ClienteDTO cliente, BindingResult result, 
			Model model, @RequestParam(name = "foto_file") MultipartFile foto, RedirectAttributes  flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(STR_TITULO,"Cliente");
			return VIEW_FORM;
		}
				
		Long idCliente = null;
		try {
			idCliente = clienteService.guardarCliente(cliente, foto);
		} catch (IOException e) {
			log.error("No se puede guardar el cliente: {} {}", cliente.getNombre(),cliente.getApellido());
			log.error(e.getMessage(),e);
			flash.addFlashAttribute(FLASH_ERROR, "No se puede guardar el cliente: " 
							+ cliente.getNombre() + " " + cliente.getApellido());
			return STR_REDIRECT + VIEW_LISTAR;
		}
		
		status.setComplete();
		flash.addFlashAttribute(FLASH_SUCCESS, "Cliente guardado con exito '" 
				+ idCliente + "'!");
		return STR_REDIRECT + VIEW_LISTAR;
	}
	
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name="id") Long id, 
			Map<String, Object> model, RedirectAttributes  flash ) {
		
		if(id>0) {
			
			var cliente = clienteService.obtenerCliente(id);
			
			model.put(STR_CLIENTE, cliente);
			model.put(STR_TITULO,"Actualizar Cliente");
			
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
				try {
					clienteService.borrarCliente(id);
				} catch (IOException e) {
					log.error("No se puede eliminar el cliente: {}", id);
					log.error(e.getMessage(),e);
					flash.addFlashAttribute(FLASH_ERROR, "No se puede eliminar el cliente: " + id);
					return STR_REDIRECT + VIEW_LISTAR;
				}
				flash.addFlashAttribute(FLASH_INFO, "Cliente eliminado con exito!");
			} else {
				flash.addFlashAttribute(FLASH_ERROR, "Cliente inexistente: " + id);
			}

		} 
		status.setComplete();
		return STR_REDIRECT + VIEW_LISTAR;
	} 	

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

}
