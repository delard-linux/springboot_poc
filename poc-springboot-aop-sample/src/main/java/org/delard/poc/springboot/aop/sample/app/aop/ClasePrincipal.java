package org.delard.poc.springboot.aop.sample.app.aop;

import org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteVipDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClasePrincipal {

	public static void main(String[] args) {
		
		//Leer configuracion de spring 
		var contexto = new AnnotationConfigApplicationContext(ConfiguracionAOP.class);
		
		//Obtener los bean contenedor de Spring
		var elCliente = contexto.getBean("clienteDao", ClienteDao.class);
		var elClienteVip = contexto.getBean("clienteVipDao", ClienteVipDao.class);
		
		//Llamar al metodo
		elCliente.insertaCliente(new Cliente("yo", "claudio"), "ee");
		elClienteVip.insertaClienteVip();
		
		
		//Cerrar contexto
		contexto.close();
		
		
		
	}
	
}
