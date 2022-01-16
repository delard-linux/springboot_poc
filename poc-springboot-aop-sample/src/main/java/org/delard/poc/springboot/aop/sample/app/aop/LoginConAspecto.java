package org.delard.poc.springboot.aop.sample.app.aop;

import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginConAspecto {

	
	@Pointcut("execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.Cliente*Dao.inser*(..))")
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
	
	@AfterReturning(pointcut = "execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.SimpleClienteDao.obtenerClientes(..))",returning = "listaClientes")
	public void tareaTrasEjecutarMetodo(List<Cliente> listaClientes) {
		
		if (!(listaClientes.stream()
				.filter(cl -> cl.getTipo().contains("verde"))
				.toList().isEmpty()))
			System.out.println("ASPECT-tareaTrasEjecutarMetodo: ... hay uno verde al menos");	
		
		procesadoDeDatosAfterReturning(listaClientes);
		
	}

	private void procesadoDeDatosAfterReturning(List<Cliente> listaClientes) {
		
		listaClientes.forEach(cl -> cl.setNombre("MASAJEADO "+ cl.getNombre()));
		
	}
	
}
