package com.bolsadeideas.springboot.error.app.errors;

public class UsuarioNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 466621873898373096L;

	public UsuarioNoEncontradoException(String id) {
		super(String.format("Usuario con ID: %s no existe en el sistema", id));
	}

	
}
 