package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dominio.RefreshTokenDom;
import com.example.demo.dominio.RoleDom;
import com.example.demo.dominio.UserDom;
import com.example.demo.repository.IRefreshTokenRepository;
import com.example.demo.service.impl.RefreshTokenServiceImpl;

@ExtendWith(MockitoExtension.class) // Inicializa los mocks
public class RefreshTokenServiceTest {

	
	@Mock
	IRefreshTokenRepository iRefreshTokenRepository;

	
	@InjectMocks
	IRefreshTokenService iRefreshTokenService = new RefreshTokenServiceImpl();
	
	@Test
	@DisplayName("getRefreshTokenSuccess, this is success")
	void getRefreshTokenSuccess() {
		
		// Given
		String refreshToken = RandomStringUtils.randomAlphanumeric(5);
		String trackingId = RandomStringUtils.randomAlphanumeric(5);
		
		// Mocks
		RefreshTokenDom refreshTokenDomMock = new RefreshTokenDom();
		refreshTokenDomMock.setValue(refreshToken);
		refreshTokenDomMock.setUser(new UserDom());
		refreshTokenDomMock.getUser().setAccessKeyId(RandomStringUtils.randomAlphanumeric(5));
		refreshTokenDomMock.getUser().setSecretAccessKey(RandomStringUtils.randomAlphanumeric(5));
		refreshTokenDomMock.getUser().setRoleDom(new RoleDom());
		refreshTokenDomMock.getUser().getRoleDom().setValue("ROLE_USER");
		
		// When
		when(iRefreshTokenRepository.getByValue(refreshToken)).thenReturn(refreshTokenDomMock);
		RefreshTokenDom refreshTokenDom = iRefreshTokenService.getByRefreshToken(refreshToken, trackingId);
		
		
		// Then
		assertNotNull(refreshTokenDom);
		assertNotNull(refreshTokenDom.getUser());
		assertThat(refreshTokenDom.getUser().getAccessKeyId()).isNotBlank();
		assertThat(refreshTokenDom.getUser().getSecretAccessKey()).isNotBlank();
		assertNotNull(refreshTokenDom.getUser().getRoleDom());
		assertThat(refreshTokenDom.getUser().getRoleDom().getValue()).isNotBlank();
		assertThat(refreshTokenDom.getValue()).isNotBlank();
		
		// Verify
		verify(iRefreshTokenRepository).getByValue(refreshToken);
	}
}
