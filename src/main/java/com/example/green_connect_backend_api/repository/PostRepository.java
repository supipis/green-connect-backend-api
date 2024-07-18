package com.example.green_connect_backend_api.repository;

import com.example.green_connect_backend_api.entity.Listing;
import com.example.green_connect_backend_api.entity.Post;
import com.example.green_connect_backend_api.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByAuthorId(Long userId);

    List<Post> findAllByAuthor(User user);
}
