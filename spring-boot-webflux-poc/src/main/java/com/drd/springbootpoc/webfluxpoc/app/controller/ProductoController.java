package com.drd.springbootpoc.webfluxpoc.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drd.springbootpoc.webfluxpoc.app.models.dao.IProductoDao;
import com.drd.springbootpoc.webfluxpoc.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	
	@Autowired
	private IProductoDao productoDao;
	
	@GetMapping("")
	public String listar(Model model) {
		
		Flux<Producto> productos = productoDao.findAll();
		
		model.addAttribute("titulo", "Listado de productos");
		model.addAttribute("productos", productos);
		
		return "listar";
	}

}
