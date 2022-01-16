package org.delard.poc.springboot.aop.sample.app.model.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class MockServicioImpl implements IMockServicio {

	@Override
	public String tareaMock() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep((new Random()).nextInt(2000));
		System.out.println("HEY.... soy un servicio Mock");
		return "\"resultado del MOCK\"";
	}
	
}
