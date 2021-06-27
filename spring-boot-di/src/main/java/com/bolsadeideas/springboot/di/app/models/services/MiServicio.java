package com.bolsadeideas.springboot.di.app.models.services;

import org.springframework.stereotype.Component;

@Component
public class MiServicio {
	
	public String operacion() {
		return "### Esto es una operacion a traves de un servicio injectado... ###";
	}

}
