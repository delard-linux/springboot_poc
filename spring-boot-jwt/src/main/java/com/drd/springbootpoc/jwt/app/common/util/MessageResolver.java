package com.drd.springbootpoc.jwt.app.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {

	@Autowired
	private MessageSource messageSource;
	
	
	public String getMessage(String key, Locale locale, String ...params) {
		return messageSource.getMessage(key, params, locale);
	}
	
	public String getMessage(String key, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}
	
}
