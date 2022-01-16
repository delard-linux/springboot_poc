package org.delard.poc.springboot.aop.sample.app.aop;

import org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteVipDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClasePrincipal {

	public static void main(String[] args) {
		
		//Read SpringBoot application context 
		var contexto = new AnnotationConfigApplicationContext(ConfiguracionAOP.class);
		//get DAO's beans from context
		var elCliente = contexto.getBean("clienteDao", ClienteDao.class);
		var elClienteVip = contexto.getBean("clienteVipDao", ClienteVipDao.class);
		
		elCliente.insertaCliente(new Cliente("yo", "claudio"), "ee");
		elCliente.setCodigoClienteNormal("MyCode");
		elCliente.setValoracionClienteNormal("5 STARS");
		var codCliente = elCliente.getCodigoClienteNormal(); 
		var valCliente = elCliente.getValoracionClienteNormal(); 
		
		elClienteVip.insertaClienteVip();
		
		//close context
		contexto.close();
		
	}
	
}
