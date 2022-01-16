package org.delard.poc.springboot.aop.sample.app;

import org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteVipDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.dao.SimpleClienteDao;
import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTestClass {

	public static void main(String[] args) {
		
		//Read SpringBoot application context 
		var contexto = new AnnotationConfigApplicationContext(ConfiguracionAOP.class);
		
		
		System.out.println("\n## PRUEBA ASPECTOS CON POINTCUTS:\n");
		pruebaPointCuts(contexto);
		System.out.println("");
		
		System.out.println("\n## PRUEBA ORDENACION ASPECTOS:\n");
		pruebaOrdenacionAspectos(contexto);
		System.out.println("");
		
		//close context
		contexto.close();
		
	}
	
	public static void pruebaPointCuts(AnnotationConfigApplicationContext contexto) {

		//get DAO's beans from context
		var elCliente = contexto.getBean("clienteDao", ClienteDao.class);
		var elClienteVip = contexto.getBean("clienteVipDao", ClienteVipDao.class);

		elCliente.insertaCliente(new Cliente("yo", "claudio"), "ee");
		elCliente.setCodigoClienteNormal("MyCode");
		elCliente.setValoracionClienteNormal("5 STARS");
		var codCliente = elCliente.getCodigoClienteNormal(); 
		var valCliente = elCliente.getValoracionClienteNormal(); 
		
		elClienteVip.insertaClienteVip();
		
	}
	
	public static void pruebaOrdenacionAspectos(AnnotationConfigApplicationContext contexto) {

		//get DAO's beans from context
		var elClienteSimple = contexto.getBean("simpleClienteDao", SimpleClienteDao.class);
		var elClienteVip = contexto.getBean("clienteVipDao", ClienteVipDao.class);

		elClienteSimple.insertaCliente(new Cliente("yo", "claudio"), "ee");
		elClienteVip.insertaClienteVip();
		
	}
	
}
