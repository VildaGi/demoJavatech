package com.example.demo.service;

import com.example.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    public List<Post> listAllPosts(){
        return List.of(
                new Post("Post 1", 0),
                new Post("Post 2", 0),
                new Post("Post 3", 0)
        );
    }
}
