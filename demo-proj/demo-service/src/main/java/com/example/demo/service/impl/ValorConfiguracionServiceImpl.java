package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dominio.ConfigurationValueDom;
import com.example.demo.repository.IConfigValueRepository;
import com.example.demo.service.IValorConfiguracionService;

@Service
public class ValorConfiguracionServiceImpl implements IValorConfiguracionService {

	@Autowired
	private IConfigValueRepository iValorConfiguracionRepository;
	
	@Override
	public ConfigurationValueDom obtenerValor(String tipoPadre, String claveHijo) {
		return iValorConfiguracionRepository.cacheFindOne(tipoPadre, claveHijo);
	}

}
