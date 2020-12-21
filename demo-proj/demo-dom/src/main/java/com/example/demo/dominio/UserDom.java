package com.example.demo.dominio;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.demo.dominio.config.ConfiguracionDominio;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USUARIO_APP", schema = ConfiguracionDominio.SCHEMA)
@Getter @Setter
public class UserDom {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "USUARIO_APP_GENERATOR", sequenceName = "DEMO.SEQ_USUARIO_APP_GENERATOR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_APP_GENERATOR")
	private Long id;
	
	@Column(name = "ACCESSKEYID")
	private String accessKeyId;
	
	@Column(name = "SECRETACCESSKEY")
	private String secretAccessKey;
	
	@Column(name = "LOCKED")
	private Boolean locked;
	
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_ROLE")
	private RoleDom roleDom;

}
