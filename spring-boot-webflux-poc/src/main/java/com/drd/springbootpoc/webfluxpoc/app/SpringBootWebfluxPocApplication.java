package com.drd.springbootpoc.webfluxpoc.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.drd.springbootpoc.webfluxpoc.app.models.dao.IProductoDao;
import com.drd.springbootpoc.webfluxpoc.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxPocApplication implements CommandLineRunner {

	@Autowired
	private IProductoDao productoDao;
	
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxPocApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxPocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.just(new Producto("Samsung Galaxy Note 20", 700.99),
				new Producto("Laptop HP Elitebook G4", 1200.50),
				new Producto("Apple Iphone 8", 437.55),
				new Producto("Monitor Samsung 27LCD", 488.85),
				new Producto("Bicicleta Trek ", 1450.99))
			.flatMap(producto -> productoDao.save(producto))
			.subscribe(producto -> log.info(String.format("Insert: %s",producto.getNombre())));
		
	}

}
