package com.example.green_connect_backend_api.service;

import com.example.green_connect_backend_api.controller.GetMassageDTO;
import com.example.green_connect_backend_api.controller.GetMassageThreadDTO;
import com.example.green_connect_backend_api.entity.Message;
import com.example.green_connect_backend_api.entity.MessageThread;
import com.example.green_connect_backend_api.repository.MessageRepository;
import com.example.green_connect_backend_api.repository.MessageThreadRepository;
import com.example.green_connect_backend_api.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageThreadService {
    private final MessageThreadRepository messageThreadRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;


    public MessageThreadService(MessageThreadRepository messageThreadRepository, MessageRepository messageRepository, UserService userService) {
        this.messageThreadRepository = messageThreadRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public MessageThread saveMessageThread(MessageThread messageThread) {
        List<Message> messages = messageThread.getMessages();
        messageRepository.saveAll(messages);

        MessageThread save = messageThreadRepository.save(messageThread);

        messages.forEach(message -> {
            message.setMessageThread(save);
            messageRepository.save(message);
        });

        return save;
    }

    public List<MessageThread> getAllMessageThreads() {
        return (List<MessageThread>) messageThreadRepository.findAll();
    }

    public MessageThread getMessageThreadById(Long id) {
        return messageThreadRepository.findById(id).orElse(null);
    }

    public List<GetMassageThreadDTO> getAllMessageThreadsForCurrentUser() {
//        messageThreadRepository
        List<MessageThread> threads = messageThreadRepository.findMessageThreadsByUsersIsContaining(userService.getCurrentUser());
        return threads.stream().map(messageThread -> {
                    GetMassageThreadDTO dto = new GetMassageThreadDTO();

                    dto.setThreadId(messageThread.getId());
                    dto.setListingId(messageThread.getListing().getId());

                    dto.setMessages(messageThread.getMessages().stream().map(message -> {
                        GetMassageDTO messageDto = new GetMassageDTO();
                        messageDto.setContent(message.getContent());
                        messageDto.setSentOn(message.getSentOn());
                        messageDto.setSenderUsername(message.getSender().getUsername());
                        return messageDto;
                    }).toList());

                    return dto;
                }
        ).toList();
    }

    @Transactional
    public void deleteMessageThread(Long id) {
        Optional<MessageThread> messageThreadOptional = messageThreadRepository.findById(id);
        if (messageThreadOptional.isPresent()) {
            MessageThread messageThread = messageThreadOptional.get();
            List<Message> messages = messageThread.getMessages();
            messageRepository.deleteAll(messages);
            messageThreadRepository.delete(messageThread);
        } else {
            throw new ResourceNotFoundException("MessageThread not found with id " + id);
        }
    }
}
