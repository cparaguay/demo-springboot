package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.controller.utils.ControllerHeaderUtils.*;

import com.example.demo.controller.path.SecurityControllerPath;
import static com.example.demo.controller.utils.SecurityControllerUtils.*;
import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.example.demo.service.IUserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static com.example.demo.util.log4j.Log4jUtil.*;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = SecurityControllerPath.RESOURCE_PATH)
@Log4j2
public class SecurityController {

	@Autowired
	private IUserService iUserService;

	@ApiResponse(description = SecurityControllerPath.LOGIN.DESCRIPTION)
	@PostMapping(value = SecurityControllerPath.LOGIN.PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseRsDTO<SecurityRsDTO> login(@RequestBody SecurityRqDTO securityRqDTO, @RequestAttribute(name = KEY_TRACKING_ID_HEADER, required = false ) String idTracking) {

		securityRqDTO.setTrackingId(idTracking);

		log.info(initLog("login() | trackingId: {0}", securityRqDTO.getTrackingId()));

		ResponseRsDTO<SecurityRsDTO> response = iUserService.login(securityRqDTO);

		log.info(endLog("login() | trackingId: {0}", securityRqDTO.getTrackingId()));
		
		return response;
	}

	@ApiResponse(description = SecurityControllerPath.REFRESH_TOKEN.DESCRIPTION)
	@PostMapping(value = SecurityControllerPath.REFRESH_TOKEN.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseRsDTO<SecurityRsDTO> refreshToken(@RequestHeader(name = KEY_AUTHORIZATION_HEADER, required = true) String authorization,
			@RequestHeader(name = KEY_REFRESH_TOKEN_HEADER, required = true) String refeshToken,
			@RequestHeader(name = KEY_TRACKING_ID_HEADER, required = true) String trackingId) {

		log.info(initLog("refreshToken() | {0}: {1}", KEY_TRACKING_ID_HEADER, trackingId));

		ResponseRsDTO<SecurityRsDTO> response = iUserService.refreshToken(buildTokenCredentialRqDTO(trackingId, authorization, refeshToken));

		log.info(endLog("refreshToken() | {0}: {1}", KEY_TRACKING_ID_HEADER, trackingId));
	
		return response;
	}
}
