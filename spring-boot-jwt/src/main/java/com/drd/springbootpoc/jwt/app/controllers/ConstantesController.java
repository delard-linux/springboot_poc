package com.drd.springbootpoc.jwt.app.controllers;

public class ConstantesController {
	
	private ConstantesController() {}
	
	// Constantes de tipos de mensajes flash
	public static final String FLASH_SUCCESS = "success";
	public static final String FLASH_INFO    = "info";
	public static final String FLASH_ERROR   = "error";
	
	// Constantes de nombres de atributos
	public static final String ATT_TITULO              = "titulo";
	public static final String ATT_CLIENTE             = "clientedto";
	public static final String ATT_FACTURA             = "facturadto";
	public static final String ATT_CLIENTE_LIST        = "clientedtolist";
	public static final String ATT_PAGINA              = "pagina";		
	public static final String ATT_CLIENTE_SEARCH_CRIT = "cl_search_crit";
	
	// Constantes comunes de vistas
	public static final String REDIRECT      = "redirect:/";
	
	
	// Constantes de mensajes i18n
	public static final String TXT_LOGIN_SUCCESS = "text.login.success";
	public static final String TXT_LOGIN_ALREADY = "text.login.already";
	public static final String TXT_LOGIN_LOGOUT = "text.login.logout";
	public static final String TXT_LOGIN_ERROR = "text.login.error";

	
	// Constantes de títulos de vista
	public static final String TXT_CLIENTE_TITULO_LISTAR      = "text.cliente.titulo.listar";
	public static final String TXT_CLIENTE_TITULO_VER         = "text.cliente.titulo.detalle";
	public static final String TXT_CLIENTE_TITULO_CREAR       = "text.cliente.titulo.crear";
	public static final String TXT_CLIENTE_TITULO_ACTUALIZAR  = "text.cliente.titulo.editar";

	// Constantes de logs i18n
	public static final String LOG_LOGIN_SUCCESS = "log.login.success";
	
	
	
	
	
	
}