package com.bolsadeideas.springboot.di.app.models.services;

public class MiServicioComplejo implements IServicio{
	
	@Override
	public String operacion() {
		return "### Esto es una operacion COMPLEJA a traves de un servicio injectado... ###";
	}

}
