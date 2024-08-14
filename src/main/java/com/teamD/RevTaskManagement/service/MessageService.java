package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.model.Message;
import com.teamD.RevTaskManagement.dao.MessageDAO;
import com.teamD.RevTaskManagement.exceptions.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDAO messageRepository;

    // Method to create a new message
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    // Method to get all messages from a specific sender
    public List<Message> getMessagesBySender(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    // Method to get all messages for a specific receiver
    public List<Message> getMessagesByReceiver(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }
}
