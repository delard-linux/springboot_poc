package com.drd.springbootpoc.app.model.domain;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ProductoDTO implements Serializable {
	
	private static final long serialVersionUID = -7013390188452716338L;

	private Long id;

	private String nombre;

	private Double precio;

	@NotNull
	private Date createAt;
	
	
	public ProductoDTO() {
		super();
	}
	
	public ProductoDTO(Long id, @NotEmpty String nombre, @NotEmpty Double precio, Date createAt) {
		this.id = id;
		this.nombre = nombre;
		this.precio= precio;
		this.createAt = createAt;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
