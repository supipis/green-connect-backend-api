package com.example.green_connect_backend_api.controller;

import com.example.green_connect_backend_api.entity.Listing;
import com.example.green_connect_backend_api.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @PostMapping
    public Listing addListing(@RequestParam("image") MultipartFile image,
                              @RequestParam("name") String name,
                              @RequestParam("category") String category,
                              @RequestParam("location") String location,
                              @RequestParam("quantity") int quantity) {
        System.out.println("before service");
        return listingService.saveListing(image, name, category, location, quantity);
    }




    @GetMapping
    public List<Listing> getAllListings() {
        return listingService.getAllListings();
    }

    @GetMapping("/{id}")
    public Listing getListingById(@PathVariable Long id) {
        return listingService.getListingById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
    }

    @PutMapping("/{id}")
    public Listing updateListing(@PathVariable Long id, @RequestBody Listing listing) {
        return listingService.updateListing(id, listing);
    }


}

