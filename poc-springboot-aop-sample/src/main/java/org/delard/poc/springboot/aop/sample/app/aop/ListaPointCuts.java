package org.delard.poc.springboot.aop.sample.app.aop;

import org.aspectj.lang.annotation.Pointcut;

public class ListaPointCuts {

	@Pointcut("execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.SimpleClienteDao.inser*(..)) || execution(* org.delard.poc.springboot.aop.sample.app.model.domain.dao.ClienteVipDao.inser*(..))")
	public void nombreDelPointCutParaClientes(){}
	
}
