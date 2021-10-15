package com.drd.springbootpoc.app.common.enums;

public enum Authority {

	ADMIN("ADMIN"), USER("USER");
    private String code; 

    private Authority(String code){this.code = code;}

    public String getCode(){return code;}
}

