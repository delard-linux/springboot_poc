package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);
	
	private static final String STR_TIEMPO_INICIO = "tiempoInicio";
	private static final String STR_TIEMPO_TRANSCURRIDO = "tiempoTranscurrido";
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("post"))
			return true;
		
		var requestUrl = new String(request.getRequestURL());
		
		if (logger.isInfoEnabled()) {
			logger.info(String.format("TiempoTranscurridoInterceptor: preHandle entrando ... %s)", requestUrl));
			long tiempoInicio = System.currentTimeMillis();
			request.setAttribute(STR_TIEMPO_INICIO, tiempoInicio);
			
		}
		Thread.sleep((new Random()).nextInt(100));
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		Long tiempoTranscurrido = 0L;
		var requestUrl = new String(request.getRequestURL());
		
		if (logger.isInfoEnabled()) {
			if (request.getAttribute(STR_TIEMPO_INICIO)!=null){
				try {
					tiempoTranscurrido = System.currentTimeMillis()-(Long)request.getAttribute(STR_TIEMPO_INICIO); 
					logger.info(String.format("Tiempo Transcurrido: %d ms %s ",tiempoTranscurrido, requestUrl));
					logger.info(String.format("TiempoTranscurridoInterceptor: post  Handle saliendo ... %s",requestUrl));
				} catch (NumberFormatException e) {
					logger.error("Tiempo Transcurrido con formato erroneo!", e);
				}
			} else {
				logger.info("Tiempo Transcurrido no trazado por request!");
			}
		}
		
		if(handler instanceof HandlerMethod 
				&& modelAndView!=null
				&& request.getAttribute(STR_TIEMPO_INICIO)!=null)
			modelAndView.addObject(STR_TIEMPO_TRANSCURRIDO, tiempoTranscurrido); 
		
	}

}
