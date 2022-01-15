package org.delard.poc.springboot.aop.sample.app.model.domain.dao;

import org.springframework.stereotype.Component;

@Component
public class ClienteVipDao {
	
	public void insertaClienteVip() {
		System.out.println("Cliente VIP insertado con exito");
	}

}
