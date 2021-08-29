package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUserName(String userName);
  User findByEmail(String email);

//  @Query(value = "select u.* from User ")
//  User findByUserNam(String userName);
}
