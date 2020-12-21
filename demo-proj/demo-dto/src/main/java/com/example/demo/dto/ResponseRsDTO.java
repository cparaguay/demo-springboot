package com.example.demo.dto;


import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "ResponseRsDTO")
public class ResponseRsDTO <T> {

	@Schema(description = "Resultado del proceso", required = true, example = "true")
	private boolean success = Boolean.FALSE;
	
	@JsonInclude(value = Include.NON_NULL)
	@Schema(description = "Error en el proceso", example = StringUtils.EMPTY)
	private String errorCode;
	
	@JsonInclude(value = Include.NON_NULL)
	@Schema(description = "Tracking id para seguir el proceso", required = true, example = "xAQdXsadHgGthh")
	private String trackingId;
	
	@JsonInclude(value = Include.NON_NULL)
	private T object;
	
	public ResponseRsDTO(T object) {
		this.object = object;
	}
	
	public ResponseRsDTO() {
	}

	public ResponseRsDTO <T> setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	
	public ResponseRsDTO <T> setObject(T object) {
		this.object = object;
		return this;
	}
}
