package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByName(String role);



}
