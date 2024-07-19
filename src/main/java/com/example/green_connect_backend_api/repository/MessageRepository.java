package com.example.green_connect_backend_api.repository;


import com.example.green_connect_backend_api.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
