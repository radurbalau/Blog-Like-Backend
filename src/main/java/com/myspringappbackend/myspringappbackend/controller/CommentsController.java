package com.myspringappbackend.myspringappbackend.controller;


import com.myspringappbackend.myspringappbackend.models.Comments;
import com.myspringappbackend.myspringappbackend.models.Posts;
import com.myspringappbackend.myspringappbackend.models.User;
import com.myspringappbackend.myspringappbackend.repository.CommentsRepository;
import com.myspringappbackend.myspringappbackend.repository.PostRepository;
import com.myspringappbackend.myspringappbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class CommentsController {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostMapping(value = "/{userId}/{postId}/comment")
    public ResponseEntity<Object> postAComment(
            @RequestBody String comments,
            @PathVariable Integer userId, @PathVariable Integer postId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not in db",HttpStatus.NOT_FOUND);
        }

        Optional<Posts> post = postRepository.findById(postId);

        if(post.isEmpty()){
            return  new ResponseEntity<>("post not in db",HttpStatus.NOT_FOUND);
        }


        comments = comments.substring(17);
        String requiredString = comments.replace("\"", "");

        requiredString = requiredString.substring(0,requiredString.length() - 2);
        System.out.println(requiredString);
            Comments comments1 = new Comments(requiredString,user.get(),post.get());
            System.out.println(comments1.toString());
        commentsRepository.save(comments1);


        return new ResponseEntity<>("Added a new Comment",HttpStatus.OK);

    }

    @DeleteMapping("/{userId}/{postId}/{commentId}")
    public ResponseEntity<Object> deleteCommentById(@PathVariable Integer userId, @PathVariable Integer postId,
                                                    @PathVariable Integer commentId){

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not in db",HttpStatus.NOT_FOUND);
        }

        Optional<Posts> post = postRepository.findById(postId);

        if(post.isEmpty()){
            return  new ResponseEntity<>("post not in db",HttpStatus.NOT_FOUND);
        }

        Optional<Comments> comments =  commentsRepository.findById(commentId);
        if(comments.isEmpty()){
            return  new ResponseEntity<>("Comment not in the Dataase",HttpStatus.NOT_FOUND);
        }

        commentsRepository.deleteById(commentId);

        return new ResponseEntity<>("Deleted comment with id = " + commentId,HttpStatus.OK);
    }

    @GetMapping("/{userId}/{postId}")
    public ResponseEntity<Object> getAllCommentsFromAPost(@PathVariable Integer userId, @PathVariable Integer postId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id not found in db");
        }

        Optional<Posts> posts = postRepository.findById(postId);
        if(posts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with this id not found in db");
        }

        return  new ResponseEntity<>(commentsRepository.getAllByPostId(postId),HttpStatus.OK);
    }


}
