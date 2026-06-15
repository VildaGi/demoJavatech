// src/test/java/com/example/demo/service/LikesServiceTest.java
package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikesServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikesService likesService;

    private Post testPost;

    @BeforeEach
    void setUp() {
        testPost = new Post(1L, "Тестовый пост", null);
        testPost.setLikes(5);
    }

    @Test
    void testLikeIncrementsLikesCount() {
        // Настройка мока
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // Вызов тестируемого метода
        int result = likesService.like(1L);

        // Проверка результатов
        assertEquals(6, result);
        assertEquals(6, testPost.getLikes());

        // Проверка вызовов моков
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(testPost);
    }

    @Test
    void testLikeOnNewPost() {
        // Пост с 0 лайков
        Post newPost = new Post(2L, "Новый пост", null);
        newPost.setLikes(0);

        when(postRepository.findById(2L)).thenReturn(Optional.of(newPost));
        when(postRepository.save(any(Post.class))).thenReturn(newPost);

        int result = likesService.like(2L);

        assertEquals(1, result);
        assertEquals(1, newPost.getLikes());
        verify(postRepository, times(1)).findById(2L);
        verify(postRepository, times(1)).save(newPost);
    }
}