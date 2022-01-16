package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginConAspecto {

	
	@Pointcut("execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.Cliente*Dao.*(..))")
	private void nombreDelPointCutParaClientes(){}

	@Pointcut("execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.Cliente*Dao.get*(..))")
	private void getterDelPointCutParaClientes(){}

	@Pointcut("execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.Cliente*Dao.set*(..))")
	private void setterDelPointCutParaClientes(){}

	@Pointcut("nombreDelPointCutParaClientes() && !(getterDelPointCutParaClientes() || setterDelPointCutParaClientes())")
	private void paqueteExceptoGetterSetter(){}
	
	@Before("paqueteExceptoGetterSetter()")
	public void antesInsertarCliente() {
		System.out.println("ASPECT-LoginConAspecto-1: El usuario está logeado");
		
		System.out.println("ASPECT-LoginConAspecto-1: El perfil para insertar clientes es correcto");
	}
	
	@Before("setterDelPointCutParaClientes()")
	public void aspectoSoloSetters() {
		System.out.println("ASPECT-LoginConAspecto-2: Solo Setters");
		
	}
	
	@Before("getterDelPointCutParaClientes()")
	public void aspectoSoloGetters() {
		System.out.println("ASPECT-LoginConAspecto-3: Solo Getters");
	}
	


	
}
