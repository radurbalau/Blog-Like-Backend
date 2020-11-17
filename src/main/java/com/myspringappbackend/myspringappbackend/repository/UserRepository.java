package com.myspringappbackend.myspringappbackend.repository;

import com.myspringappbackend.myspringappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByUsernameAndPassword(String username, String password);
}
