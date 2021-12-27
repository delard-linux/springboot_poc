package com.drd.springbootpoc.jwt.app.common.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;

import com.drd.springbootpoc.jwt.app.common.util.MessageResolver;

@Controller
public class AppController {

	@Autowired
    private LocaleResolver localeResolver;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	MessageResolver msgResolver;
	
	
	public Locale getApplicationLocale() {
		return localeResolver.resolveLocale(request);
	}
	
	public String getMessage(String key, String ...params) {
		return msgResolver.getMessage(key, getApplicationLocale(), params );
	}
	
	public String getMessage(String key) {
		return msgResolver.getMessage(key, getApplicationLocale());
	}
	
}
