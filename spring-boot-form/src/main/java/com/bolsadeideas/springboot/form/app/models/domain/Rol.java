package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Objects;

public class Rol {

	private Integer id;
	private String codigo;
	private String nombre;

	public Rol() {
	}

	public Rol(Integer id, String codigo, String nombre) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Rol))
			return false;
		if (this == obj)
			return true;
		var role = (Rol)obj;
		return this.hashCode() == role.hashCode();
	}

	@Override
	public int hashCode() {
	    return Objects.hash(
	    		((this.id == null) ? 0 : this.id), 
	    		((this.codigo == null) ? "sincodigo" : this.codigo), 
	    		((this.nombre == null) ? "sinnombre" : this.nombre));
	}

}
