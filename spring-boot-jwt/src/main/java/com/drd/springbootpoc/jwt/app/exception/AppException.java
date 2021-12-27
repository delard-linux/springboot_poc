package com.drd.springbootpoc.jwt.app.exception;

public class AppException  extends RuntimeException{
    
	private static final long serialVersionUID = 1L;
	
	private final String msgError;
     
    public AppException(String msg){
        super();
        this.msgError=msg;
    }
     
    @Override
    public String getMessage(){
        return this.msgError;
    }
     
}
