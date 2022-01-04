package com.drd.springbootpoc.reactorpoc.app.models;

public class Usuario {

	private String nombre;
	private String apellidos;
	
	public Usuario(String nombre, String apellidos) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [nombre=").append(nombre).append(", apellidos=").append(apellidos).append("]");
		return builder.toString();
	}
	
	
	
	
}
