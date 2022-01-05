package com.drd.springbootpoc.webfluxpoc.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drd.springbootpoc.webfluxpoc.app.models.dao.IProductoDao;
import com.drd.springbootpoc.webfluxpoc.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class); 
	
	@Autowired
	private IProductoDao productoDao;
	
	@GetMapping
	public Flux<Producto> index() {
		
		return productoDao.findAll()
				.map(pr -> {
						pr.setNombre(pr.getNombre().toUpperCase());
						return pr;
					})
				.doOnNext(pr -> log.info(String.format("2º Subscriptor: %s", pr)));
		
	}
	
	@GetMapping("/{id}")
	public Mono<Producto> detail(@PathVariable String id) {
		
		return productoDao.findById(id)
				.map(pr -> {
						pr.setNombre(pr.getNombre().toUpperCase());
						return pr;
					})
				.doOnNext(pr -> log.info(String.format("2º Subscriptor: %s", pr)));
		
	}	
	
}