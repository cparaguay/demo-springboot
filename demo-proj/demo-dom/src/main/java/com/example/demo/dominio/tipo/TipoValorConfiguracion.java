package com.example.demo.dominio.tipo;

import lombok.Getter;

@Getter
public enum TipoValorConfiguracion {

	JWT_PARAMETER("JWT_PARAMETER"), 
	JWT_PARAMETER_AUTHORITIES("AUTHORITIES"),
	JWT_PARAMETER_HEADER("HEADER"),
	JWT_PARAMETER_SECRET("SECRET"),
	JWT_PARAMETER_PREFIXTOKEN("PREFIXTOKEN");
	
	private String key;
	
	private TipoValorConfiguracion(String name) {
		this.key = name;
	}

}
