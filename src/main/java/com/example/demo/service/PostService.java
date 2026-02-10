package com.example.demo.service;

import com.example.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    private List<Post> posts;

    public PostService() {
        posts = new ArrayList<>(Arrays.asList(
                new Post("Post 1", new Date()),
                new Post("Post 2", new Date()),
                new Post("Post 3", new Date())
        ));
    }

    public List<Post> listAllPosts(){
        return posts;
    }

    public void create(String text) {
        posts.add(new Post(text, new Date()));
    }
}
