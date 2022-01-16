package org.delard.poc.springboot.aop.sample.app.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.delard.poc.springboot.aop.sample.app.model.domain.entity.Cliente;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class AntesInsertarClienteAspect {

	@Before("org.delard.poc.springboot.aop.sample.app.aop.ListaPointCuts.nombreDelPointCutParaClientes()")
	public void antesInsertarCliente(JoinPoint miJoin) {
		
		
		var listaArgumentosCliente = Arrays.asList(miJoin.getArgs()).stream()
				.filter(Cliente.class::isInstance)
				.toList();
		
		var cliente = !listaArgumentosCliente.isEmpty()? listaArgumentosCliente.get(0) : "#noHayClienteEnParametro";
		
		System.out.println("ASPECT-AntesInsertarClienteAspect: El usuario está logeado insertado el Cliente: " + cliente);
		
		System.out.println("ASPECT-AntesInsertarClienteAspect: El perfil para insertar clientes es correcto");
	}
	
}
 