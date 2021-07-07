package com.bolsadeideas.springboot.error.app.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bolsadeideas.springboot.error.app.errors.UsuarioNoEncontradoException;

@ControllerAdvice
public class ErrorHandlerController {
	
	private static final String STR_ERROR = "error";
	private static final String STR_MESSAGE = "message";
	private static final String STR_STATUS= "status";
	private static final String STR_TIMESTAMP = "timestamp";
	
	@ExceptionHandler(ArithmeticException.class)
	public String aritmeticaError(ArithmeticException ex, Model model) {
		model.addAttribute(STR_ERROR, "Error de aritmetica");
		model.addAttribute(STR_MESSAGE, ex.getMessage());
		model.addAttribute(STR_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute(STR_TIMESTAMP, new Date());
		return "/error/generica";
	}
	
	@ExceptionHandler(NumberFormatException.class)
	public String numberFormatExceptionError(NumberFormatException ex, Model model) {
		model.addAttribute(STR_ERROR, "Formato número inválido!");
		model.addAttribute(STR_MESSAGE, ex.getMessage());
		model.addAttribute(STR_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute(STR_TIMESTAMP, new Date());
		return "/error/numero-formato";
	}
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	public String usuarioNoEncontradoExceptionError(UsuarioNoEncontradoException ex, Model model) {
		model.addAttribute(STR_ERROR, "Error de usuario no encontrado");
		model.addAttribute(STR_MESSAGE, ex.getMessage());
		model.addAttribute(STR_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute(STR_TIMESTAMP, new Date());
		return "/error/generica";
	}

	
	
}
