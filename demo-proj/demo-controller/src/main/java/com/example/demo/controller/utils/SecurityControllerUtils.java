package com.example.demo.controller.utils;

import com.example.demo.dto.TokenCredentialRqDTO;

public class SecurityControllerUtils {

	public static TokenCredentialRqDTO buildTokenCredentialRqDTO(String trackingId, String authorization, String refeshToken) {
		
		TokenCredentialRqDTO tokenCredentialRqDTO = new TokenCredentialRqDTO();
		
		tokenCredentialRqDTO.setRefreshToken(refeshToken);
		tokenCredentialRqDTO.setJwt(authorization);
		tokenCredentialRqDTO.setTrackingId(trackingId);
		
		return tokenCredentialRqDTO;
	}


}
