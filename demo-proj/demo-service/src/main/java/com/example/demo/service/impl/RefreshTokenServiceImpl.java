package com.example.demo.service.impl;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dominio.RefreshTokenDom;
import com.example.demo.dominio.UserDom;
import com.example.demo.repository.IRefreshTokenRepository;
import com.example.demo.service.IRefreshTokenService;
import com.example.demo.util.log4j.Log4jUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RefreshTokenServiceImpl implements IRefreshTokenService {

	@Autowired
	private IRefreshTokenRepository iRefreshTokenRepository;
	
	@Override
	public RefreshTokenDom doGenerateRefreshToken(UserDom userDom, String trackingId) {
		
		log.debug(Log4jUtil.initLog(MessageFormat.format("doGenerateRefreshToken() | tracking-id: {0}", trackingId)));

		RefreshTokenDom refreshTokenDom = new RefreshTokenDom();
		refreshTokenDom.setUser(userDom);
		refreshTokenDom.setRegisterDate(new Date());
		String refreshTokenValue = Base64.getEncoder().encodeToString(String.valueOf(System.currentTimeMillis()).getBytes());
		refreshTokenDom.setValue(refreshTokenValue);
		
		refreshTokenDom = iRefreshTokenRepository.save(refreshTokenDom);
				
		log.debug(Log4jUtil.endLog(MessageFormat.format("doGenerateRefreshToken() | tracking-id: {0}", trackingId)));
	
		return refreshTokenDom;
	}

	@Override
	public RefreshTokenDom getByRefreshToken(String refreshToken, String trackingId) {

		log.debug(Log4jUtil.initLog("getByRefreshToken() | tracking-id: {0}", trackingId));
		
		RefreshTokenDom refreshTokenDom = iRefreshTokenRepository.getByValue(refreshToken);
		
		log.debug(Log4jUtil.endLog("getByRefreshToken() | tracking-id: {0}", trackingId));

		return refreshTokenDom;
	}

}
