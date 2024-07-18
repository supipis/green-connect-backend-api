package com.example.green_connect_backend_api.repository;


import com.example.green_connect_backend_api.entity.Listing;
import com.example.green_connect_backend_api.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ListingRepository extends CrudRepository<Listing, Long> {
    List<Listing> findByUserId(Long userId);

    List<Listing> findAllByUser(User user);
}