package com.example.demo.test.controller;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controller.SecurityController;
import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.example.demo.dto.TokenCredentialRqDTO;
import com.example.demo.repository.IRefreshTokenRepository;
import com.example.demo.service.IUserService;
import com.example.demo.service.error.ErrorCode;

@ExtendWith(MockitoExtension.class)
public class SecurityControllerTest extends BaseControllerTest{

	@Mock
	private IUserService iUsuarioService;
	
	@Mock
	private IRefreshTokenRepository iRefreshTokenRepository;
	
	@InjectMocks
	private SecurityController securityController;
	
	@DisplayName("Login, success")
	@Test
	void successLogin() throws Exception {

		//Given
				
		SecurityRqDTO securityRqDTO = new SecurityRqDTO();
		securityRqDTO.setAccessKeyId("XP92870");
		securityRqDTO.setSecretAccessKey("12345678");
		
		String idTracking = String.valueOf(System.currentTimeMillis());
		
		// Mock
		
		ResponseRsDTO<SecurityRsDTO> responseFinal = new ResponseRsDTO<>(new SecurityRsDTO());
		responseFinal.getObject().setToken(RandomStringUtils.randomAlphanumeric(5));
		responseFinal.getObject().setRefreshToken(RandomStringUtils.randomAlphanumeric(5));

		responseFinal.setSuccess(Boolean.TRUE);
		responseFinal.setTrackingId(idTracking);

		// When
		when(iUsuarioService.login(securityRqDTO)).thenReturn(responseFinal);
		ResponseRsDTO<SecurityRsDTO> result = securityController.login(securityRqDTO, idTracking);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.getTrackingId()).withFailMessage("trackingId No debeser nulo o vacio, el resultado fue <%s>", result.getTrackingId()).isNotNull().isNotEmpty();
		assertThat(result.getObject().getToken()).isNotNull().isNotEmpty();
		assertThat(result.getObject().getRefreshToken()).isNotNull().isNotEmpty();
		assertThat(result.getErrorCode()).isNull();

		verify(iUsuarioService).login(securityRqDTO);
	}
	
	@DisplayName("Login, login with Locked user")
	@Test
	void loginLockedUser() throws Exception {

		//Given
				
		SecurityRqDTO securityRqDTO = new SecurityRqDTO();
		securityRqDTO.setAccessKeyId("XP8888");
		securityRqDTO.setSecretAccessKey("88888888");
		
		String idTracking = String.valueOf(System.currentTimeMillis());

		// Mock
		
		ResponseRsDTO<SecurityRsDTO> responseFinal = new ResponseRsDTO<>(new SecurityRsDTO());
		responseFinal.getObject().setToken(null);
		responseFinal.setSuccess(Boolean.FALSE);
		responseFinal.setErrorCode(ErrorCode.USER_LOCKED.getCode());
		responseFinal.setTrackingId(idTracking);

		// When
		when(iUsuarioService.login(securityRqDTO)).thenReturn(responseFinal);
		
		ResponseRsDTO<SecurityRsDTO> result = securityController.login(securityRqDTO, idTracking);
		
		// Then
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getTrackingId()).withFailMessage("trackingId No debeser nulo o vacio, el resultado fue <%s>", result.getTrackingId()).isNotNull().isNotEmpty();
		assertThat(result.getObject().getToken()).isNull();
		assertThat(result.getErrorCode()).isEqualTo(ErrorCode.USER_LOCKED.getCode());
		
		verify(iUsuarioService).login(securityRqDTO);
	}
	
	@DisplayName("RefreshToken, success")
	@Test
	void refreshTokenSucccess() throws Exception {
		
		//Given
		String refreshToken = RandomStringUtils.randomAlphabetic(7);
		String jwt = RandomStringUtils.randomAlphabetic(7);
		String trackingId = RandomStringUtils.randomAlphabetic(7);

		
		// Mock
		ResponseRsDTO<SecurityRsDTO> responseFinal = new ResponseRsDTO<>(new SecurityRsDTO());
		responseFinal.getObject().setToken(refreshToken);
		responseFinal.getObject().setRefreshToken(RandomStringUtils.randomAlphanumeric(5));
		responseFinal.setTrackingId(trackingId);
		responseFinal.setSuccess(Boolean.TRUE);
		
		//When
		when(iUsuarioService.refreshToken(Mockito.any(TokenCredentialRqDTO.class))).thenReturn(responseFinal);
		ResponseRsDTO<SecurityRsDTO> result = securityController.refreshToken(jwt, refreshToken, trackingId);
		
		// Then
		assertThat(result).withFailMessage("The result must to be exist, however, the result is <%s>", result).isNotNull();
		assertThat(result.isSuccess()).withFailMessage("The value must to be true, however, the value is <%s>").isTrue();
		assertThat(result.getTrackingId()).withFailMessage("The tracking-id must not to be empty or null, however, the tracking-id is <%s>", result.getTrackingId()).isNotNull().isNotEmpty();
		assertThat(result.getObject()).withFailMessage("The object must not to be null, however, the object is <%s>", result.getObject()).isNotNull();
		assertThat(result.getObject().getToken()).withFailMessage("The token must not to be empty or null, however, the token is <%s>", result.getObject().getToken()).isNotNull().isNotEmpty();
		assertThat(result.getObject().getRefreshToken()).withFailMessage("he refreshToken not to be empty or null, however, the refreshToken is <%s>", result.getObject().getRefreshToken()).isNotNull().isNotEmpty();
		assertThat(result.getErrorCode()).withFailMessage("The errorCode must to be null, however, the errorCode is", result.getErrorCode()).isNull();

		verify(iUsuarioService).refreshToken(Mockito.any(TokenCredentialRqDTO.class));
	}
}
