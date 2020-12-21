package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO Response del proceso del loguin")
public class SecurityRsDTO {

	@Schema(description = "Token para la autenticacion", required = true, example = "Bearer eyJhbGciOiJ...")
	private String token;
	
	@Schema(description = "Refresh token for reload request", required = true, example = "eyJhbGciOiJ...")
	private String refreshToken;
}
