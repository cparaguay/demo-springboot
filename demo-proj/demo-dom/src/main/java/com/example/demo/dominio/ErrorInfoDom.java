package com.example.demo.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.demo.dominio.config.ConfiguracionDominio;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
@Table(name = "ERROR_INFO", schema = ConfiguracionDominio.SCHEMA)
public class ErrorInfoDom {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "DEMO_ERROR_INFO_GENERATOR", sequenceName = "DEMO.SEQ_ERROR_INFO_GENERATOR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMO_ERROR_INFO_GENERATOR")
	private Long id;
	
	@Column(name = "STATUS", length = 100, nullable = false)
	private int status;
	
	@Column(name = "ERROR", length = 100, nullable = false)
	private String error;
	
	@Column(name = "TRACE", length = 100, nullable = false)
	private String trace;
	
	@Column(name = "MESSAGE", length = 100, nullable = false)
	private String message;
	
	@Column(name = "URI_REQUESTED", length = 100, nullable = false)
	private String uriRequested;
	
	@Column(name = "TIME_STAMP", length = 100, nullable = false)		
	private Date timeStamp;

	@Column(name = "ID_TRACKING", length = 100, nullable = false)
	private String idTracking;

}
