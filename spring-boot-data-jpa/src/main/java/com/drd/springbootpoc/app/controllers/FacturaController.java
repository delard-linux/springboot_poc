package com.drd.springbootpoc.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drd.springbootpoc.app.model.domain.ClienteDTO;
import com.drd.springbootpoc.app.model.domain.FacturaDTO;
import com.drd.springbootpoc.app.model.domain.ItemFacturaDTO;
import com.drd.springbootpoc.app.model.domain.ProductoDTO;
import com.drd.springbootpoc.app.model.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes(names = {"facturadto"})
public class FacturaController {
	
	// Constantes de atributos
	private static final String STR_TITULO = "titulo";
	private static final String STR_FACTURA = "facturadto";
	private static final String STR_TITULO_CREAR = "Crear factura";
	
	// Constantes de vistas
	private static final String STR_REDIRECT = "redirect:/";
	private static final String STR_PREFIX_FACTURA = "/factura";
	private static final String VIEW_VER = "ver";
	private static final String VIEW_LISTAR = "listar";
	private static final String VIEW_FORM = "form";
	
	// Constantes de tipos de mensajes flash
	private static final String FLASH_SUCCESS = "success";
	private static final String FLASH_ERROR = "error";
	
	@Autowired
	private IClienteService clienteService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value="clienteId") Long clienteId,
			Map<String, Object> model,
			RedirectAttributes flash) {
		
		ClienteDTO cliente = clienteService.obtenerCliente(clienteId);
		
		if (cliente==null) {
			flash.addFlashAttribute(FLASH_ERROR, "El cliente no existe en la base de datos");
			return STR_REDIRECT + VIEW_LISTAR;
		}
		
		var factura = new FacturaDTO();
		factura.setCliente(cliente);
		model.put(STR_FACTURA, factura);
		model.put(STR_TITULO, STR_TITULO_CREAR);
		
		return STR_PREFIX_FACTURA + "/" + VIEW_FORM;
	}

	@GetMapping(value = "/cargar-productos/{productoNombreTerm}", produces = { "application/json" })
	public @ResponseBody List<ProductoDTO> cargarProductos(@PathVariable String productoNombreTerm) {
		return clienteService.obtenerProductosPorNombre(productoNombreTerm);
	}
	
	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute("facturadto") FacturaDTO facturadto, 
			BindingResult result,
			Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, 
			RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute(STR_TITULO, STR_TITULO_CREAR);
			//TODO: si se ha metido alguna linea de productos antes se pierde por lo que habría que dejarlo en sesion
			return STR_PREFIX_FACTURA + "/" + VIEW_FORM;
		}
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute(STR_TITULO, STR_TITULO_CREAR);
			model.addAttribute(FLASH_ERROR, "La factura debe tener al menos una linea");
			return STR_PREFIX_FACTURA + "/" + VIEW_FORM;
		}
		
		if (itemId.length > 0) {
			for (var i = 0; i < itemId.length; i++) {
				ProductoDTO producto = clienteService.obtenerProducto(itemId[i]);

				var linea = new ItemFacturaDTO();
				linea.setCantidad(cantidad[i]);
				linea.setProducto(producto);
				facturadto.addItemFactura(linea);

				log.info("ID: {} , cantidad: {} ", itemId[i], cantidad[i]);
			}
		}

		clienteService.guardarFactura(facturadto);
		
		status.setComplete();

		flash.addFlashAttribute(FLASH_SUCCESS, "Factura creada con éxito!");

		return STR_REDIRECT + VIEW_VER + "/" + facturadto.getCliente().getId();
	}
	
	
}
