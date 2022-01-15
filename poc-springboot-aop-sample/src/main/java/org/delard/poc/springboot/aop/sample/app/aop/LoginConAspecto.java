package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginConAspecto {

	
	@Pointcut("execution(* insertaCliente(..))")
	private void nombreDelPointCutParaClientes(){}
	
	@Before("nombreDelPointCutParaClientes()")
	public void antesInsertarCliente() {
		System.out.println("ASPECT-1: El usuario está logeado");
		
		System.out.println("ASPECT-1: El perfil para insertar clientes es correcto");
	}
	
	@Before("nombreDelPointCutParaClientes()")
	public void requisitosCliente() {
		System.out.println("ASPECT-2: El cliente cumple los requisitos para ser insertado en BD");
	}
	
	@Before("nombreDelPointCutParaClientes()")
	public void requisitosTabla() {
		System.out.println("ASPECT-3: Comprobar que la tabla tenga menos de 3000 reguistos");
		
	}

	
}
