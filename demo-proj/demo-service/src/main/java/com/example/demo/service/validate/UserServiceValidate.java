package com.example.demo.service.validate;

import org.apache.commons.lang3.Validate;

import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.service.error.ErrorCode;
import com.example.demo.service.utils.ValidateObject;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceValidate {

	public static ValidateObject<?> validateUser(SecurityRqDTO securityRqDTO, String trakingId) {
		
		ValidateObject<?> validationInputs = new ValidateObject<>();
		validationInputs.setSuccess(Boolean.FALSE);
		
		try {
			
			Validate.notNull(securityRqDTO, "SecurityRqDTO  is null");
			Validate.notBlank(securityRqDTO.getAccessKeyId(), "SecurityRqDTO.accessKeyId is blank");
			Validate.notBlank(securityRqDTO.getSecretAccessKey(), "SecurityRqDTO.secretAccessKey is blank");
			validationInputs.setSuccess(Boolean.TRUE);			
		}catch(NullPointerException | IllegalArgumentException  ex) {
			validationInputs.setError(ErrorCode.EMPTY_FIELDS.getCode());
			log.error("processLogin() | trackingId: {} - validacion de requestBody erronea: {}", trakingId, ex.getMessage());
		}
		
		return validationInputs;
	}
}
