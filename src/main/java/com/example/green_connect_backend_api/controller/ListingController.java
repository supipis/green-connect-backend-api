package com.example.green_connect_backend_api.controller;

import com.example.green_connect_backend_api.entity.Listing;
import com.example.green_connect_backend_api.service.ListingService;
import com.example.green_connect_backend_api.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("category") String category,
                                                 @RequestParam("location") String location,
                                                 @RequestParam("quantity") int quantity) {
        try {
            Listing incomingListing = new Listing();
            incomingListing.setId(id);
            incomingListing.setName(name);
            incomingListing.setCategory(category);
            incomingListing.setLocation(location);
            incomingListing.setQuantity(quantity);
            Listing updated = listingService.updateListing(id, incomingListing, image);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getImageForListing(@PathVariable String imageId) {
        Path imagePath = Paths.get("app_data/images/" + imageId);

        try {
            byte[] imageData = Files.readAllBytes(imagePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust based on image format
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Handle file not found or other IO exceptions
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Listing> getAllListingsForUser() {
        return listingService.getAllListingsForUser();
    }

    @GetMapping("/all")
    public List<Listing> getAllListings(){
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
}

