package com.drd.springbootpoc.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.drd.springbootpoc.app.controllers.ConstantesController;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) 
					throws IOException, ServletException {

		var flashMapManager = new SessionFlashMapManager();
		
		var flashMap = new FlashMap();
		
		var locale = localeResolver.resolveLocale(request);
		var mensaje = String.format(messageSource.getMessage(ConstantesController.TXT_LOGIN_SUCCESS, null, locale), authentication.getName());
		
		flashMap.put(ConstantesController.FLASH_SUCCESS, mensaje);
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		log.info(messageSource.getMessage(ConstantesController.LOG_LOGIN_SUCCESS, null, locale), authentication.getName());		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
