package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TokenCredentialRqDTO {

	private String refreshToken;
	private String jwt;
	private String trackingId;
}
