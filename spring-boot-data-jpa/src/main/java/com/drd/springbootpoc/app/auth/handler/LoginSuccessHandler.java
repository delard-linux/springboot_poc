package com.drd.springbootpoc.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.drd.springbootpoc.app.controllers.ControllerConstants;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) 
					throws IOException, ServletException {

		var flashMapManager = new SessionFlashMapManager();
		
		var flashMap = new FlashMap();
		
		var locale = localeResolver.resolveLocale(request);
		var mensaje = String.format(messageSource.getMessage(ControllerConstants.TXT_LOGIN_SUCCESS, null, locale), authentication.getName());
		var mensajelog = String.format(messageSource.getMessage(ControllerConstants.LOG_LOGIN_SUCCESS, null, locale), authentication.getName());
		
		
		flashMap.put(ControllerConstants.FLASH_SUCCESS, mensaje);
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		logger.info(mensajelog);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
