package com.example.demo.controller.path;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SecurityControllerPath {

	public static final String RESOURCE_PATH = "/security";

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public abstract class LOGIN {
		public static final String PATH = "/login";
		public static final String FULL_PATH = RESOURCE_PATH + PATH;
		
		public static final String DESCRIPTION = "Login, si el proceso es exitoso este devolvera un token";
	}
	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public abstract class REFRESH_TOKEN {
		public static final String PATH = "/refresh-token";
		public static final String FULL_PATH = RESOURCE_PATH + PATH;
		public static final String DESCRIPTION = "Refresh token, api para generar nuevo jwt";
	}

}
