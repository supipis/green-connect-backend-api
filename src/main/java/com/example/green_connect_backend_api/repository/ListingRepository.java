package com.example.green_connect_backend_api.repository;


import com.example.green_connect_backend_api.entity.Listing;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Long> {
}