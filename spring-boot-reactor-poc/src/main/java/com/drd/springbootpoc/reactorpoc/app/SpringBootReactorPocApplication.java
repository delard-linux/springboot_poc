package com.drd.springbootpoc.reactorpoc.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.drd.springbootpoc.reactorpoc.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorPocApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorPocApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorPocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Usuario> nombres = Flux.just("Andres Pelaez", "Pedro Martinez", "Maria Dorsal", 
											"David the Best", "Rodrigo la Liaste", 
											"Kevin Choni", "Pedro Fulano")
				.map( nombre -> new Usuario(
						nombre.substring(0,nombre.indexOf(' ')).toUpperCase(),
						nombre.substring(nombre.indexOf(' ')+1).toUpperCase()
						))
				.filter( usuario -> usuario.getNombre().equalsIgnoreCase("Pedro"))
				.doOnNext(usuario-> {
					if (usuario == null) {
						throw new RuntimeException("Los usuarios no pueden ser nulos");
					} else {
						System.out.println("doOnNext -> " + usuario.toString());
					}})
				.map( usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
					});
		
		nombres.subscribe(
				elem -> log.info(elem.toString()),
				err -> log.error(String.format("Se ha producido un error: %s", err.toString())),
				new Runnable() {
					@Override
					public void run() {
						log.info("FINALIZACION del flujo!");
					}
					
					}
				);
		
	}

}
