package com.example.demo.service;

import com.example.demo.dto.error.ErrorInfoRqDTO;
import com.example.demo.dto.error.ErrorInfoRsDTO;

public interface IErrorInfoService {

	ErrorInfoRsDTO save(ErrorInfoRqDTO errorDTO);

}
