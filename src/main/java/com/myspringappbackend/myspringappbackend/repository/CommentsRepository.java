package com.myspringappbackend.myspringappbackend.repository;

import com.myspringappbackend.myspringappbackend.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer> {
        List<Comments> getAllByPostId(Integer id);
}
