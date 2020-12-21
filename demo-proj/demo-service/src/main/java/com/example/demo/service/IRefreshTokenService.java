package com.example.demo.service;

import com.example.demo.dominio.RefreshTokenDom;
import com.example.demo.dominio.UserDom;

public interface IRefreshTokenService {

	RefreshTokenDom doGenerateRefreshToken(UserDom userDom, String trackingId);

	RefreshTokenDom getByRefreshToken(String refreshToken, String trackingId);

}
