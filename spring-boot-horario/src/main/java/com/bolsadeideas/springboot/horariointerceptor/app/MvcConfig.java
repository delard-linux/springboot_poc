package com.bolsadeideas.springboot.horariointerceptor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bolsadeideas.springboot.horariointerceptor.app.services.HorarioAperturaService;
import com.bolsadeideas.springboot.horariointerceptor.app.services.IHorarioAperturaService;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Autowired
	@Qualifier("controlHorarioInterceptor")
	private HandlerInterceptor controlHorario;

	@Bean("horarioAperturaServiceNormal")
	@Primary
	public IHorarioAperturaService registrarMiServicio() {
		return new HorarioAperturaService();		
	}

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(controlHorario)
		.excludePathPatterns("/cerrado")
		.excludePathPatterns("/**/*.css");

	}

}
