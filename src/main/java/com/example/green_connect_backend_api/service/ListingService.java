package com.example.green_connect_backend_api.service;

import com.example.green_connect_backend_api.entity.Listing;
import com.example.green_connect_backend_api.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserService userService;

    private static final String UPLOAD_DIR = "uploads/";

    public List<Listing> getListingsByUserId(Long userId) {
        return listingRepository.findByUserId(userId);
    }

    public Listing saveListing(MultipartFile image, String name, String category, String location, int quantity) {
        System.out.println("in the service");
        String fileId = saveImageFile(image);

        Listing listing = new Listing();
        listing.setImage(fileId);
        listing.setName(name);
        listing.setCategory(category);
        listing.setLocation(location);
        listing.setQuantity(quantity);

        listing.setUser(userService.getCurrentUser());

        return listingRepository.save(listing);
    }

    private String saveImageFile(MultipartFile image) {
        String fileId = UUID.randomUUID().toString();
        Path filePath = Paths.get(IMAGE_DIRECTORY + fileId);
        System.out.println(filePath);
        try {
            System.out.println("trying to save file");
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
        return fileId;
    }

    private static final String IMAGE_DIRECTORY = "app_data/images/";

    public void deleteImage(String fileId) {
        Path filePath = Paths.get(IMAGE_DIRECTORY + fileId);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Handle the exception, e.g., log the error
            throw new RuntimeException("Failed to delete image", e);
        }
    }


    public List<Listing> getAllListings() {
        return (List<Listing>) listingRepository.findAll();
    }

    public List<Listing> getAllListingsForUser(){
        return listingRepository.findAllByUser(userService.getCurrentUser());
    }

    public Listing getListingById(Long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        return listing.orElse(null);
    }

    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    /*public Listing updateListing(Long id, Listing updatedListing) {
        return listingRepository.findById(id)
                .map(existingListing -> {
                    existingListing.setImage(updatedListing.getImage());
                    existingListing.setName(updatedListing.getName());
                    existingListing.setCategory(updatedListing.getCategory());
                    existingListing.setLocation(updatedListing.getLocation());
                    existingListing.setQuantity(updatedListing.getQuantity());
                    return listingRepository.save(existingListing);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id " + id));
    }*/

    public Listing updateListing(Long id, Listing updatedListing, MultipartFile image) {
        return listingRepository.findById(id)
                .map(existingListing -> {
                    // Update only the fields that are supposed to be updated
                    existingListing.setName(updatedListing.getName());
                    existingListing.setCategory(updatedListing.getCategory());
                    existingListing.setLocation(updatedListing.getLocation());
                    existingListing.setQuantity(updatedListing.getQuantity());

                    if (image != null) {
                        String existingImageId = existingListing.getImage();
                        deleteImage(existingImageId);

                        String imageId = saveImageFile(image);
                        existingListing.setImage(imageId);
                    }

                    return listingRepository.save(existingListing);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id " + id));
    }
}

