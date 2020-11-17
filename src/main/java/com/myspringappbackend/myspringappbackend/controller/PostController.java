package com.myspringappbackend.myspringappbackend.controller;

import com.myspringappbackend.myspringappbackend.models.Posts;
import com.myspringappbackend.myspringappbackend.models.User;
import com.myspringappbackend.myspringappbackend.repository.PostRepository;
import com.myspringappbackend.myspringappbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @PostMapping("/{userId}/post")
    public ResponseEntity<Object> createPost(@PathVariable Integer userId
            ,@RequestBody Posts posts){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        List<Posts> postsList = user.get().getPosts();
        postsList.add(posts);
        user.get().setPosts(postsList);

        posts.setUser(user.get());
        postRepository.save(posts);

        return  new ResponseEntity<>("Added a new post with name" + posts.getName(),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/post/{id}")
    public ResponseEntity<Object> deletePostById(@PathVariable Integer userId,
                                             @PathVariable Integer id){
        postRepository.deleteById(id);
        return new ResponseEntity<Object>("Deleted a post with name : " + postRepository.findById(id),HttpStatus.OK);
    }




    @GetMapping("/{userId}/post")
    public ResponseEntity<Object> getAllPostsByAnUser(@PathVariable Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Id not found in database");


        return ResponseEntity.status(HttpStatus.OK).body(postRepository.findAllByUser(user.get()));
    }
}
