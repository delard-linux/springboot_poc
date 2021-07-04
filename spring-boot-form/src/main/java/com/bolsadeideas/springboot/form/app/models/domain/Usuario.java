package com.bolsadeideas.springboot.form.app.models.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Usuario {

	private	String identificador;
	
	private	String nombre;
	
	@NotBlank
	private	String apellido;
	
	@Size(min = 3, max = 8)
	@NotBlank
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotBlank(message="el email no puede ser vacío")
	@Email(message="correo con formato incorrecto")
	private String email;
	
	public Usuario() {
	}

	public Usuario(String username, String password, String email) {
		
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
