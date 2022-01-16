package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class RequisitosTablaClienteAspect {

	@Before("org.delard.poc.springboot.aop.sample.app.aop.ListaPointCuts.nombreDelPointCutParaClientes()")
	public void requisitosTablaCliente() {
		System.out.println("ASPECT-RequisitosTablaClienteAspect: La tabla es de menos de 100 registros, cumple requisitos tabla");
	}
	


	
}
