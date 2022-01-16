package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class AntesInsertarClienteAspect {

	@Before("org.delard.poc.springboot.aop.sample.app.aop.ListaPointCuts.nombreDelPointCutParaClientes()")
	public void antesInsertarCliente() {
		System.out.println("ASPECT-AntesInsertarClienteAspect: El usuario está logeado");
		
		System.out.println("ASPECT-AntesInsertarClienteAspect: El perfil para insertar clientes es correcto");
	}
	
}
