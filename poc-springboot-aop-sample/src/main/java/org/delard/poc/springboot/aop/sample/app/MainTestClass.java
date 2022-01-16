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

		System.out.println("\n## PRUEBA EJECUCION POSTERIOR DE ASPECTO Y POST PROCESADO:\n");
		pruebaEjecucionPosterior(contexto);
		System.out.println("");

		System.out.println("\n## PRUEBA EJECUCION POSTERIOR A EXCEPCION:\n");
		pruebaEjecucionPosteriorException(contexto);
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
		elCliente.getCodigoClienteNormal(); 
		elCliente.getValoracionClienteNormal(); 
		
		elClienteVip.insertaClienteVip();
		
	}
	
	public static void pruebaOrdenacionAspectos(AnnotationConfigApplicationContext contexto) {

		//get DAO's beans from context
		var elClienteSimple = contexto.getBean("simpleClienteDao", SimpleClienteDao.class);
		var elClienteVip = contexto.getBean("clienteVipDao", ClienteVipDao.class);

		elClienteSimple.insertaCliente(new Cliente("yo", "claudio"), "ee");
		elClienteVip.insertaClienteVip();
	}	
	
	public static void pruebaEjecucionPosterior(AnnotationConfigApplicationContext contexto) {

		//get DAO's beans from context
		var elClienteSimple = contexto.getBean("simpleClienteDao", SimpleClienteDao.class);

		elClienteSimple.obtenerClientes(false).forEach(System.out::println);
	}
	
	
	public static void pruebaEjecucionPosteriorException(AnnotationConfigApplicationContext contexto) {

		//get DAO's beans from context
		var elClienteSimple = contexto.getBean("simpleClienteDao", SimpleClienteDao.class);

		try {
			elClienteSimple.obtenerClientes(true).forEach(System.out::println);
		} catch (Exception e) {
			System.out.println("Control de la Exception en Main de obtenerClientes por salud mental: " + e.toString());
		}
		
	}
	
	
}
