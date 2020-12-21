package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO Request del proceso del loguin")
public class SecurityRqDTO {

	@Schema(description = "Credencial accesId del cliente", required = true)
	private String accessKeyId;
	
	@Schema(description = "Credencial secretAccess del cliente", required = true)
	private String secretAccessKey;
	
	@Schema(hidden = true)
	private String trackingId;
}
