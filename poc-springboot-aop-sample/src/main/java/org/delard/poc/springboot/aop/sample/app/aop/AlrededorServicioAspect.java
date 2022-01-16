package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AlrededorServicioAspect {
	
	@Around("execution(* *.tareaMock(..))")
	public Object tareaTrasEjecutarMetodoAlrededor(ProceedingJoinPoint soyUnPunto) throws Throwable {
		
		var inicio = System.currentTimeMillis();
		
		Object resultado = soyUnPunto.proceed();
		
		System.out.println("ASPECT-tareaTrasEjecutarMetodoAlrededor: ... Tarea MOCK al canto en " 
						+ (System.currentTimeMillis()-inicio) + "ms!" );	
		
		return resultado;
	}
	
}
