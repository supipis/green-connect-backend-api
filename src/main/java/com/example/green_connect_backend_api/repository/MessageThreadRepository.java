package com.example.green_connect_backend_api.repository;


import com.example.green_connect_backend_api.entity.Message;
import com.example.green_connect_backend_api.entity.MessageThread;
import com.example.green_connect_backend_api.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageThreadRepository extends CrudRepository<MessageThread, Long> {
    List<MessageThread> findMessageThreadsByUsersContaining(User user);

    List<MessageThread> findMessageThreadsByUsersIsContaining(User user);

    List<MessageThread> findByUsersId(Long userId);
}
