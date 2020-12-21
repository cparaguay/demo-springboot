package com.example.demo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dominio.ErrorInfoDom;
import com.example.demo.dto.error.ErrorInfoRqDTO;
import com.example.demo.dto.error.ErrorInfoRsDTO;
import com.example.demo.repository.IErrorRepository;
import com.example.demo.service.IErrorInfoService;
import com.google.gson.Gson;

import static com.example.demo.util.log4j.Log4jUtil.*;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ErrorInfoServiceImpl implements IErrorInfoService {

	@Autowired
	private IErrorRepository iErrorRepository;
	
	@Override
	public ErrorInfoRsDTO save(ErrorInfoRqDTO errorInfoRqDTO) {
		log.debug(initLog("save() | idTracking: {0}", errorInfoRqDTO.getIdTracking()));
		
		ErrorInfoDom errorDom = new ErrorInfoDom();
				
		BeanUtils.copyProperties(errorInfoRqDTO, errorDom);
		
		log.debug(initLog("save() | errorDom:{0} - idTracking: {1}", new Gson().toJson(errorDom), errorInfoRqDTO.getIdTracking()));
		
		ErrorInfoDom errorInfoDom = iErrorRepository.save(errorDom);
		
		ErrorInfoRsDTO errorInfoRsDTO = new ErrorInfoRsDTO();
		
		BeanUtils.copyProperties(errorInfoDom, errorInfoRsDTO);
		
		log.debug(endLog("save() | idTracking: {0}", errorInfoRqDTO.getIdTracking()));
		return errorInfoRsDTO;
	}

}
