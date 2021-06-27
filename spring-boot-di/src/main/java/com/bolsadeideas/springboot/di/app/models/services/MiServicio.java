package com.bolsadeideas.springboot.di.app.models.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("miServicioSimple")
@Primary
public class MiServicio implements IServicio{
	
	@Override
	public String operacion() {
		return "### Esto es una operacion SIMPLE a traves de un servicio injectado... ###";
	}

}
