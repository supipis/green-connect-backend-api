package com.example.green_connect_backend_api.controller;

import com.example.green_connect_backend_api.entity.Listing;
import com.example.green_connect_backend_api.entity.Message;
import com.example.green_connect_backend_api.entity.MessageThread;
import com.example.green_connect_backend_api.service.ListingService;
import com.example.green_connect_backend_api.service.MessageThreadService;
import com.example.green_connect_backend_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("/api/message-threads")
public class MessageThreadController {
    private final MessageThreadService messageThreadService;
    private final UserService userService;
    private final ListingService listingService;

    @Autowired
    public MessageThreadController(MessageThreadService messageThreadService, UserService userService, ListingService listingService) {
        this.messageThreadService = messageThreadService;
        this.userService = userService;
        this.listingService = listingService;
    }

    @PostMapping
    public MessageThread createMessageThread(@RequestBody FirstMessagePostDTO body) {

        Long listingId = body.getListingId();
        String content = body.getContent();

        Listing listing = listingService.getListingById(listingId);

        List<Message> messages = new ArrayList<>();
        Message firstMessage = new Message();
        firstMessage.setSender(userService.getCurrentUser());
        firstMessage.setContent(content);
        messages.add(firstMessage);


        List users = new ArrayList();
        users.add(userService.getCurrentUser());
        users.add(listing.getUser());

        MessageThread messageThread = new MessageThread();
        messageThread.setMessages(messages);
        messageThread.setUsers(users);
        messageThread.setListing(listing);

        return messageThreadService.saveMessageThread(messageThread);
    }

    @GetMapping
    public List<GetMassageThreadDTO> getAllMessageThreads() {
        return messageThreadService.getAllMessageThreadsForCurrentUser();
    }

    @GetMapping("/{id}")
    public MessageThread getMessageThreadById(@PathVariable Long id) {
        return messageThreadService.getMessageThreadById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageThread(@PathVariable Long id) {
        messageThreadService.deleteMessageThread(id);
        return ResponseEntity.noContent().build();
    }
}
