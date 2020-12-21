package com.example.demo.service;


import com.example.demo.dto.ResponseRsDTO;
import com.example.demo.dto.SecurityRqDTO;
import com.example.demo.dto.SecurityRsDTO;
import com.example.demo.dto.TokenCredentialRqDTO;

public interface IUserService {

	ResponseRsDTO<SecurityRsDTO> login(SecurityRqDTO securityRqDTO);

	ResponseRsDTO<SecurityRsDTO> refreshToken(TokenCredentialRqDTO tokenCredentialRqDTO);

}
