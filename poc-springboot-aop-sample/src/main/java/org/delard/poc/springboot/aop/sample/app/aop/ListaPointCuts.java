package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Pointcut;

public class ListaPointCuts {

	@Pointcut("execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.SimpleClienteDao.*(..)) || execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteVipDao.*(..))")
	public void nombreDelPointCutParaClientes(){}
	
}
