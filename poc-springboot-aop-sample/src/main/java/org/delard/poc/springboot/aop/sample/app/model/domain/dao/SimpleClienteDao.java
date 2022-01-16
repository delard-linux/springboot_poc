package org.delard.poc.springboot.aop.sample.app.model.domain.dao;

import java.util.List;

import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class SimpleClienteDao {

	public String insertaCliente(Cliente cl, String texto) {
		System.out.println("Cliente insertado con exito: " + cl + " texto: " + texto);
		return "cliente insertado";
	}
	
	public List<Cliente> obtenerClientes(boolean checkLanzaException) {
		
		if(checkLanzaException) throw new RuntimeException("La cagaste Burt Lancaster");
		
		System.out.println("Obtencion de clientes ejecutada con exito");
		return List.of(
			new Cliente("Marti", "futurible"),
			new Cliente("Spiderman", "returns"),
			new Cliente("Hulk", "elverde"),
			new Cliente("Thanos", "visionario")
			);
	}
	
}
