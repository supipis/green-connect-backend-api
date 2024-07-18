package com.example.green_connect_backend_api.service;

import com.example.green_connect_backend_api.entity.Post;
import com.example.green_connect_backend_api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<Post> getAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    public Post savePost(String message) {
        Post newPost = new Post();
        newPost.setMessage(message);
        newPost.setAuthor(userService.getCurrentUser());
        return postRepository.save(newPost);
    }

    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
