// src/test/java/com/example/demo/service/PostServiceTest.java
package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private List<Post> mockPosts;

    @BeforeEach
    void setUp() {
        Post post1 = new Post(1L, "Первый пост", null);
        post1.setLikes(10);
        Post post2 = new Post(2L, "Второй пост", null);
        post2.setLikes(5);
        mockPosts = Arrays.asList(post1, post2);
    }

    @Test
    void testListAllPosts() {
        when(postRepository.findAll()).thenReturn(mockPosts);

        List<Post> result = postService.listAllPosts();

        assertEquals(2, result.size());
        assertEquals("Первый пост", result.get(0).getText());
        assertEquals("Второй пост", result.get(1).getText());
        assertEquals(10, result.get(0).getLikes());
        assertEquals(5, result.get(1).getLikes());

        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testCreatePost() {
        String postText = "Новый созданный пост";

        postService.create(postText);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository, times(1)).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();
        assertEquals(postText, savedPost.getText());
        assertEquals(0, savedPost.getLikes());
        assertNotNull(savedPost.getCreationDate());
        assertNull(savedPost.getId()); // id должен быть null, т.к. генерируется БД
    }

    @Test
    void testCreateEmptyPost() {
        String emptyText = "";

        postService.create(emptyText);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository, times(1)).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();
        assertEquals(emptyText, savedPost.getText());
        assertNotNull(savedPost.getCreationDate());
    }
}