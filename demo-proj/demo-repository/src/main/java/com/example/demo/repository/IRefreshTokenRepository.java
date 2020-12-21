package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dominio.RefreshTokenDom;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshTokenDom, Long> {

	@Query("from RefreshTokenDom where value = :refreshToken")
	RefreshTokenDom getByValue(@Param(value = "refreshToken")String refreshToken);
}
