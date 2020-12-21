package com.example.demo.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.demo.dominio.config.ConfiguracionDominio;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CFG_VALUE", schema = ConfiguracionDominio.SCHEMA)
@Getter@Setter
public class ConfigurationValueDom {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "CFG_VALUE_ID_GENERATOR", sequenceName = "DEMO.SEQ_CFG_VALUE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CFG_VALUE_ID_GENERATOR")
	private Long id;
	
	 //Es el valor del padre
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PADRE")
	private ConfigurationValueDom tipo;
	
	private String clave;
	
	private String valor;
	
	@Column(name = "VALOR_ALTERNO")
	private String valorAlterno;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION", nullable = false)
	private Date fechaCreacion;
	
	private Boolean estado = Boolean.TRUE;
}
