package com.bolsadeideas.springboot.app.model.domain;

import java.io.Serializable;
import java.util.Date;


public class ClienteDTO implements Serializable {


	private static final long serialVersionUID = 8058062057701411951L;

	private String nombre;

	private String apellido;

	private String email;

	private Date bornAt;


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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBornAt() {
		return bornAt;
	}

	public void setBornAt(Date bornAt) {
		this.bornAt = bornAt;
	}	
	
}
