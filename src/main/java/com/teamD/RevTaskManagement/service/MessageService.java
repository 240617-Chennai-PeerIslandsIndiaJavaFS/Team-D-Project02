package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.dao.MessageDAO;
import com.teamD.RevTaskManagement.exceptions.MessageNotFoundException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    // Method to create a new message
    public Message createMessage(Message message) {
        return messageDAO.save(message);
    }

    // Method to get all messages from a specific sender
    public List<Message> getMessagesBySender(Long senderId) {
        Employee sender = employeeDAO.findById(senderId)
                .orElseThrow(() -> new MessageNotFoundException("Sender not found for ID: " + senderId));
        return messageDAO.findBySender(sender);
    }

    // Method to get all messages for a specific receiver
    public List<Message> getMessagesByReceiver(Long receiverId) {
        Employee receiver = employeeDAO.findById(receiverId)
                .orElseThrow(() -> new MessageNotFoundException("Receiver not found for ID: " + receiverId));
        return messageDAO.findByReceiver(receiver);
    }
}
