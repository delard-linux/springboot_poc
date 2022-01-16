package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
public class RequisitosClienteAspect {

	@Before("org.delard.poc.springboot.aop.sample.app.aop.ListaPointCuts.nombreDelPointCutParaClientes()")
	public void requisitosCliente() {
		System.out.println("ASPECT-RequisitosClienteAspect: El cliente cumple los requisitos");
	}
	
}
