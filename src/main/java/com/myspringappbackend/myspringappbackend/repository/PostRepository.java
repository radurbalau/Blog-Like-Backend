package com.myspringappbackend.myspringappbackend.repository;

import com.myspringappbackend.myspringappbackend.models.Posts;
import com.myspringappbackend.myspringappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer> {
        List<Posts> findAllByUser(User user);

}
