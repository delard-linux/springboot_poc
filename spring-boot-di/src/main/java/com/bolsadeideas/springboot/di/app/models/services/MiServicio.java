package com.bolsadeideas.springboot.di.app.models.services;

public class MiServicio implements IServicio{
	
	@Override
	public String operacion() {
		return "### Esto es una operacion SIMPLE a traves de un servicio injectado... ###";
	}

}
