package org.delard.poc.springboot.aop.sample.app.model.domain.dao;

import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteDao {

	private String valoracionClienteNormal;
	private String codigoClienteNormal;
	
	public String getValoracionClienteNormal() {
		System.out.println("ClienteDao -> getValoracionClienteNormal()");
		return valoracionClienteNormal;
	}
	public void setValoracionClienteNormal(String valoracionClienteNormal) {
		System.out.println("ClienteDao -> setValoracionClienteNormal() " + valoracionClienteNormal);
		this.valoracionClienteNormal = valoracionClienteNormal;
	}
	public String getCodigoClienteNormal() {
		System.out.println("ClienteDao -> getCodigoClienteNormal()");
		return codigoClienteNormal;
	}
	public void setCodigoClienteNormal(String codigoClienteNormal) {
		System.out.println("ClienteDao -> setCodigoClienteNormal() " + codigoClienteNormal);
		this.codigoClienteNormal = codigoClienteNormal;
	}
	
	public String insertaCliente(Cliente cl, String cls) {
		System.out.println("Cliente insertado con exito: " + cl);
		return "cliente insertado";
	}
	
	
}
