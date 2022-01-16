package org.delard.poc.springboot.aop.sample.app.model.domain.dao;

import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class SimpleClienteDao {

	public String insertaCliente(Cliente cl, String texto) {
		System.out.println("Cliente insertado con exito: " + cl + " texto: " + texto);
		return "cliente insertado";
	}
	
}
