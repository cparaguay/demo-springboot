package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dominio.ConfigurationValueDom;

@Repository
public interface IConfigValueRepository extends JpaRepository<ConfigurationValueDom, Long>{

	@Query("Select v from ConfigurationValueDom v where v.tipo.clave = :tipoPadre and v.clave = :claveHijo")
	ConfigurationValueDom cacheFindOne(@Param("tipoPadre") String tipoPadre, @Param("claveHijo") String claveHijo);

}
