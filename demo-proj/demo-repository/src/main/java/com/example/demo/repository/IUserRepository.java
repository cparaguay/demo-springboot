package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dominio.UserDom;

@Repository
public interface IUserRepository extends JpaRepository<UserDom, Long> {

	@Query("Select u from UserDom u where u.accessKeyId = :accessKeyId and u.secretAccessKey = :secretAccessKey")
	UserDom login(@Param(value = "accessKeyId") String accessKeyId, @Param(value = "secretAccessKey") String secretAccessKey);
}
