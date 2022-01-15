package org.delard.poc.springboot.aop.sample.app.model.domain.dao;

import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteDao {
	
	public String insertaCliente(Cliente cl, String cls) {
		System.out.println("Cliente insertado con exito: " + cl);
		return "cliente insertado";
	}

}
