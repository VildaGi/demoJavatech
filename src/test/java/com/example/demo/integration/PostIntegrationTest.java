// src/test/java/com/example/demo/integration/PostsIntegrationTest.java
package com.example.demo.integration;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    void testCreateAndListPost() throws Exception {
        // Тест создания поста через контроллер и проверка его отображения в списке
        mockMvc.perform(post("/new")
                        .param("text", "Интеграционный тестовый пост"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Проверяем, что пост сохранился в БД
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", hasSize(1)))
                .andExpect(model().attribute("posts", hasItem(
                        hasProperty("text", is("Интеграционный тестовый пост"))
                )));
    }

    @Test
    void testLikePost() throws Exception {
        // Создаём пост в БД
        Post post = new Post(null, "Пост для лайка", new Date());
        Post savedPost = postRepository.save(post);

        // Отправляем лайк через контроллер
        mockMvc.perform(post("/post/" + savedPost.getId() + "/like"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        // Проверяем, что лайк увеличился в БД
        Post updatedPost = postRepository.findById(savedPost.getId()).get();
        assert updatedPost.getLikes() == 1;

        // Отправляем ещё один лайк
        mockMvc.perform(post("/post/" + savedPost.getId() + "/like"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        updatedPost = postRepository.findById(savedPost.getId()).get();
        assert updatedPost.getLikes() == 2;
    }
}