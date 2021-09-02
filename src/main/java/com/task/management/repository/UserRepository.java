package com.task.management.repository;

import com.task.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUserName(String userName);
  User findByEmail(String email);

//  @Query(value = "select u.* from User ")
//  User findByUserNam(String userName);
}
