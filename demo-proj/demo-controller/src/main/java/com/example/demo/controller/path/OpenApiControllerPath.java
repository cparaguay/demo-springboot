package com.example.demo.controller.path;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class OpenApiControllerPath {

	public static final String RESOURCE = "/swagger-ui.html";
	
	public static final String RESOURCE_UI = "/swagger-ui/**";

	public static final String RESOURSE_OWN = "/v3/api-docs/**";
	
	public static final String RESOURCE_DOC = "/v3/api-docs.yaml";
	
}
