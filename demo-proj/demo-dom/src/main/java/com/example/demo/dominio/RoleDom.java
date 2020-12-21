package com.example.demo.dominio;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
@Table(name = "ROLE_USER", schema = "DEMO")
@Entity
public class RoleDom {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "ROLE_USER_GENERATOR", sequenceName = "DEMO.SEQ_ROLE_USER_GENERATOR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_USER_GENERATOR")
	private Long id;
	
	@Column(name = "VALUE")
	private String value;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGISTER_DATE", length = 7)
	private Date registerDate;
}
