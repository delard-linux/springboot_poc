package org.delard.poc.springboot.aop.sample.app.model.domain.entity;

public class Cliente {

	private String nombre;
	private String tipo;
	
	public Cliente(String nombre, String tipo) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [nombre=").append(nombre).append(", tipo=").append(tipo).append("]");
		return builder.toString();
	}
	
}
