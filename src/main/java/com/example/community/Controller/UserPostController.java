/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community.Controller;

import com.example.community.model.UserPost;
import com.example.community.repository.UserPostRepository;
import com.example.community.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author Chaos Ruler
 */
@RestController
@RequestMapping("/posts/userposts")
public class UserPostController {
    @Autowired
    UserPostRepository userPostRepo;
    
    @GetMapping("/all")
    public List<UserPost> getAllPosts() {
        return userPostRepo.findAll();
    }
    @PostMapping("/add")
    public UserPost createNote(@Valid @RequestBody UserPost post) {
        return userPostRepo.save(post);
    }
    // Get a Single Note
    @GetMapping("/search/{userID,postID}")
    public UserPost getUserPostById(@PathVariable(value = "postID") Long postId,@PathVariable(value = "userID") Long userId) {
        return userPostRepo.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }
    @PutMapping("/{postid}")
    public UserPost updateNote(@PathVariable(value = "postid") Long postId, @Valid @RequestBody UserPost postDetails) {
        UserPost post = userPostRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setContent(postDetails.getContent());
        UserPost updatedNote = userPostRepo.save(post);
        return updatedNote;
    }
    @DeleteMapping("/{postid,userid}")
    public ResponseEntity<?> deletepost(@PathVariable(value = "postid") Long postId,@PathVariable(value = "userid") Long userId) {
        UserPost post = userPostRepo.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        userPostRepo.delete(post);
    return ResponseEntity.ok().build();
    }
    
}
