package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.dominio.RefreshTokenDom;
import com.example.demo.dominio.UserDom;
import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.example.demo.dto.TokenCredentialRqDTO;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IRefreshTokenService;
import com.example.demo.service.IUserService;
import com.example.demo.service.error.ErrorCode;
import com.example.demo.service.utils.ValidateObject;
import com.example.demo.service.validate.UserServiceValidate;

import static com.example.demo.util.log4j.Log4jUtil.*;

import java.util.Arrays;

import lombok.extern.log4j.Log4j2;

@Service
@Qualifier("IUserService")
@Log4j2
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserRepository iUserRepository;
	
	@Autowired
	private IRefreshTokenService iRefreshTokenService;
	
	@Autowired
	private JwtService jwtTokenService;
	
	@Override
	public ResponseRsDTO<SecurityRsDTO> login(SecurityRqDTO securityRqDTO) {
		
		log.debug(initLog("login() | idTracking: {0}", securityRqDTO.getTrackingId()));

		// --- Given
		ResponseRsDTO<SecurityRsDTO> responseFinal = new ResponseRsDTO<>();
		responseFinal.setTrackingId(securityRqDTO.getTrackingId());

		// --- Developing
		
		ValidateObject<?> validationInputs = UserServiceValidate.validateUser(securityRqDTO, securityRqDTO.getTrackingId());
		responseFinal.setSuccess(Boolean.FALSE);
		responseFinal.setErrorCode(validationInputs.getError());
		
		if(validationInputs.isSuccess()) {
			
			UserDom userDom = iUserRepository.login(securityRqDTO.getAccessKeyId(), securityRqDTO.getSecretAccessKey());
			
			if(userDom != null) {
				if(userDom.getLocked()) {
					responseFinal.setErrorCode(ErrorCode.USER_LOCKED.getCode());				
				}else {
					
					UserDetails userDetails = new User(userDom.getAccessKeyId(), userDom.getSecretAccessKey(), Arrays.asList(new SimpleGrantedAuthority(userDom.getRoleDom().getValue())));
										
					RefreshTokenDom refreshTokenDom = iRefreshTokenService.doGenerateRefreshToken(userDom, securityRqDTO.getTrackingId());

					responseFinal.setObject(new SecurityRsDTO());
					responseFinal.getObject().setToken(jwtTokenService.doGenerateToken(userDetails));
					responseFinal.getObject().setRefreshToken(refreshTokenDom.getValue());
					responseFinal.setSuccess(Boolean.TRUE);
				}
				
			}else {
				responseFinal.setErrorCode(ErrorCode.USER_INVALID.getCode());
			}
		}
		
		log.info("processLogin() | trackingId: {} - Generacion de token [EXITO={}]", responseFinal.getTrackingId(), responseFinal.isSuccess());
		
		
		log.debug(endLog("login() | idTracking: {0}", securityRqDTO.getTrackingId()));

		// --- Then
		return responseFinal;
	}

	@Override
	public ResponseRsDTO<SecurityRsDTO> refreshToken(TokenCredentialRqDTO tokenCredentialRqDTO) {
		
		log.debug(initLog("login() | id-tracking: {0}", tokenCredentialRqDTO.getTrackingId()));
		
		// --- Given
		ResponseRsDTO<SecurityRsDTO> responseFinal = new ResponseRsDTO<>();
		responseFinal.setTrackingId(tokenCredentialRqDTO.getTrackingId());
		
		RefreshTokenDom refreshTokenDom = iRefreshTokenService.getByRefreshToken(tokenCredentialRqDTO.getRefreshToken(),  tokenCredentialRqDTO.getTrackingId());
		
		if(refreshTokenDom != null) {
			
			UserDetails userDetails = new User(refreshTokenDom.getUser().getAccessKeyId(), refreshTokenDom.getUser().getSecretAccessKey(), Arrays.asList(new SimpleGrantedAuthority(refreshTokenDom.getUser().getRoleDom().getValue())));
			String jwt = jwtTokenService.doGenerateToken(userDetails);
			
			responseFinal.setObject(new SecurityRsDTO());
			responseFinal.getObject().setToken(jwt);
			responseFinal.getObject().setRefreshToken(refreshTokenDom.getValue());
			responseFinal.setSuccess(Boolean.TRUE);
		}else {
			responseFinal.setErrorCode("error");
		}
		
		log.info("login() | id-tracking: {}, refreshToken is success?: {}", tokenCredentialRqDTO.getTrackingId(), responseFinal.isSuccess());

		log.debug(endLog("login() | id-tracking: {0}", tokenCredentialRqDTO.getTrackingId()));
		return responseFinal;
	}
}
