package com.drd.springbootpoc.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.drd.springbootpoc.app.controllers.ControllerConstants;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) 
					throws IOException, ServletException {

		var flashMapManager = new SessionFlashMapManager();
		
		var flashMap = new FlashMap();
		
		flashMap.put(ControllerConstants.FLASH_SUCCESS, "Hola " + authentication.getName() + ", has iniciado sesión con éxito!");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		logger.info("El usuario '" + authentication.getName() + "' ha iniciado sesión con éxito");
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
