package com.example.demo.service;

import com.example.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    private List<Post> posts;

    public PostService() {
        posts = new ArrayList<>(Arrays.asList(
                new Post(0L, "Post 1", new Date()),
                new Post(1L, "Post 2", new Date()),
                new Post(2L, "Post 3", new Date())
        ));
    }

    public List<Post> listAllPosts(){
        return posts;
    }

    public void create(Long id, String text) {
        posts.add(new Post(id, text, new Date()));
    }
}
