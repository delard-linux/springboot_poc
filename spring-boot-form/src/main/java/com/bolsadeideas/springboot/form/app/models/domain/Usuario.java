package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.bolsadeideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

public class Usuario {

	@IdentificadorRegex
	private	String identificador;
	
	private	String nombre;
	
	@Requerido
	private	String apellido;
	
	@Size(min = 3, max = 8)
	@NotBlank
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotBlank(message="el email no puede ser vacío")
	@Email(message="correo con formato incorrecto")
	private String email;
	
	@NotNull
	@Min(5)
	@Max(5000)
	private Integer cuenta;

	@NotNull
	@Past
	private Date fechaNacimiento;
	
	@NotNull
	private Pais pais;
	
	@NotEmpty
	private List<Rol> roles;
	
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

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
}
