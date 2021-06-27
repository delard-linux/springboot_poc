package com.bolsadeideas.springboot.di.app.models.domain;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class Factura {

	@Value("${factura.descripcion}")
	private String descripcion;

	@Autowired
	private Cliente cliente;

	@Autowired
	@Qualifier("itemFacturaOficina")
	private List<ItemFactura> items;

	@PostConstruct
	public void inicializar() {
		cliente.setNombre(cliente.getNombre().concat(" ").concat("José"));
		setDescripcion(getDescripcion().concat(" del cliente: ").concat(cliente.getNombre()));
	}

	@PreDestroy
	public void destrucion() {
		System.out.println("Factura destruida: ".concat(getDescripcion()));
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

}
