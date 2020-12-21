package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dominio.ErrorInfoDom;

@Repository
public interface IErrorRepository extends JpaRepository<ErrorInfoDom, Long> {

}
