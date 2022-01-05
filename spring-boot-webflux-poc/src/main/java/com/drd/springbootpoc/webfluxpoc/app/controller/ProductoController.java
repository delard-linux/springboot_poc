package com.drd.springbootpoc.webfluxpoc.app.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.drd.springbootpoc.webfluxpoc.app.models.dao.IProductoDao;
import com.drd.springbootpoc.webfluxpoc.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class); 
	
	@Autowired
	private IProductoDao productoDao;
	
	@GetMapping({"","/"})
	public String listar(Model model) {
		
		Flux<Producto> productos = productoDao.findAll()
				.map(pr -> {
						pr.setNombre(pr.getNombre().toUpperCase());
						return pr;
					});
		
		productos.subscribe( pr -> log.info(String.format("2º Subscriptor: %s", pr)));
		
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		
		return "listar";
	}
	
	@GetMapping("/listar-datadriven")
	public String listarDataDriven(Model model) {
		
		Flux<Producto> productos = productoDao.findAll()
				.map(pr -> {
						pr.setNombre(pr.getNombre().toUpperCase());
						return pr;
					})
				.delayElements(Duration.ofSeconds(1));
		
		productos.subscribe( pr -> log.info(String.format("2º Subscriptor: %s", pr)));
		
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos,2));
		return "listar";
	}
	
	@GetMapping("/full")
	public String listarFull(Model model) {
		
		Flux<Producto> productos = productoDao.findAll()
				.map(pr -> {
						pr.setNombre(pr.getNombre().toUpperCase());
						return pr;
					})
				.repeat(5000);
		
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		
		return "listar";
	}

}
