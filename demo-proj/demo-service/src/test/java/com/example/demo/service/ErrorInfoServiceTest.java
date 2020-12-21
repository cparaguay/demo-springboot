package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import com.example.demo.dominio.ErrorInfoDom;
import com.example.demo.dto.error.ErrorInfoRqDTO;
import com.example.demo.dto.error.ErrorInfoRsDTO;
import com.example.demo.repository.IErrorRepository;
import com.example.demo.service.impl.ErrorInfoServiceImpl;

@ExtendWith(MockitoExtension.class) // Inicializa los mocks
public class ErrorInfoServiceTest {

	@Mock
	IErrorRepository iErrorRepository;
	
	@InjectMocks
	IErrorInfoService iErrorInfoService = new ErrorInfoServiceImpl();
	
	@Test
	@DisplayName("Save, success")
	void saveSuccess(){
		
		// Given
		ErrorInfoRqDTO errorInfoRqDTO = new ErrorInfoRqDTO();
		errorInfoRqDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfoRqDTO.setTimeStamp(new Date());
		errorInfoRqDTO.setIdTracking(RandomStringUtils.randomAlphanumeric(5));
		errorInfoRqDTO.setUriRequested(StringUtils.join("/security", "/refreshToken"));
		errorInfoRqDTO.setTrace("java.lang.NullPointerException");
		errorInfoRqDTO.setMessage("null");
		errorInfoRqDTO.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		
		// Mocks
		
		ErrorInfoDom errorInfoDomMock = new ErrorInfoDom();
		
		BeanUtils.copyProperties(errorInfoRqDTO, errorInfoDomMock);
		errorInfoDomMock.setId(RandomUtils.nextLong());
		
		// When
		when(iErrorRepository.save(Mockito.any(ErrorInfoDom.class))).thenReturn(errorInfoDomMock);
		ErrorInfoRsDTO errorInfoDom = iErrorInfoService.save(errorInfoRqDTO);
		
		
		// Then
		assertNotNull(errorInfoDom);
		assertThat(errorInfoDom.getStatus()).isNotZero();
		assertThat(errorInfoDom.getError()).isNotBlank();
		assertThat(errorInfoDom.getIdTracking()).isNotBlank();
		assertNotNull(errorInfoDom.getTimeStamp());
		assertThat(errorInfoDom.getUriRequested()).isNotBlank();
	}
}
