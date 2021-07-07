package com.bolsadeideas.springboot.error.app.models.domain;

import java.util.Objects;

public class Usuario {

	private Integer id;

	private String nombre;

	private String apellido;

	public Usuario() {
	}

	public Usuario(Integer id, String nombre, String apellido) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		if (this == obj)
			return true;
		var usuario = (Usuario)obj;
		return this.hashCode() == usuario.hashCode();
	}

	@Override
	public int hashCode() {
	    return Objects.hash(
	    		((this.id == null) ? 0 : this.id), 
	    		((this.nombre == null) ? "sinnombre" : this.nombre), 
	    		((this.apellido == null) ? "sinapellido" : this.apellido));
	}
	
}
