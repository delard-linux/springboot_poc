package com.drd.springbootpoc.app.model.domain;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

public class ItemFacturaDTO implements Serializable {

	
	private static final long serialVersionUID = -1061931886335225675L;

	private Long id;

	private Integer cantidad;
	
	private ProductoDTO producto;
	
	
	public ItemFacturaDTO() {
		super();
	}
	
	public ItemFacturaDTO(Long id, @NotEmpty Integer cantidad, @NotEmpty ProductoDTO producto) {
		this.id = id;
		this.cantidad = cantidad;
		this.producto = producto;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}
	
	public Double calcularImporte() {
		return getCantidad()*getProducto().getPrecio();
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
