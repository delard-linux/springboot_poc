package com.drd.springbootpoc.jwt.app.model.domain;

import java.io.Serializable;

public class ClienteSearchCriteria implements Serializable {


	private static final long serialVersionUID = 5531301789315894178L;

	private String nombreFilter;

	private String apellidoFilter;

	private String emailFilter;
	
	public ClienteSearchCriteria(String nombre, String apellido, String email) {
		super();
		this.nombreFilter = nombre;
		this.apellidoFilter = apellido;
		this.emailFilter = email;
	}
	
	public String getNombreFilter() {
		return nombreFilter;
	}

	public void setNombreFilter(String nombre) {
		this.nombreFilter = nombre;
	}

	public String getApellidoFilter() {
		return apellidoFilter;
	}

	public void setApellidoFilter(String apellido) {
		this.apellidoFilter = apellido;
	}

	public String getEmailFilter() {
		return emailFilter;
	}

	public void setEmailFilter(String email) {
		this.emailFilter = email;
	}

}
