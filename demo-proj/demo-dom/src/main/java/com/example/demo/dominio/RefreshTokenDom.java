package com.example.demo.dominio;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
@Table(name = "REFRESH_TOKEN", schema = "DEMO")
@Entity
public class RefreshTokenDom {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "DEMO_REFRESH_TOKEN_GENERATOR", sequenceName = "DEMO.SEQ_REFRESH_TOKEN_GENERATOR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMO_REFRESH_TOKEN_GENERATOR")
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_USUARIO")
	private UserDom user;
	
	@Column(name = "VALUE", length = 100, nullable = false)
	private String value;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGISTER_DATE", length = 7)
	private Date registerDate;
}
