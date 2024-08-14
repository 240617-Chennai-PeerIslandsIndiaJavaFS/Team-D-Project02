package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.MessageDAO;
import com.teamD.RevTaskManagement.exceptions.MessageNotFoundException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private MessageDAO messageDAO;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Employee createEmployee(long id) {
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        return employee;
    }

    private Message createMessage(long id, Employee sender, Employee receiver) {
        Message message = new Message();
        message.setMessageId(id);
        message.setSubject("Test Subject");
        message.setContext("Test Context");
        message.setSender(sender);
        message.setReceiver(receiver);
        return message;
    }

    @Test
    public void testCreateMessage() {
        Employee sender = createEmployee(1L);
        Employee receiver = createEmployee(2L);
        Message message = createMessage(1L, sender, receiver);
        when(messageDAO.save(any(Message.class))).thenReturn(message);

        Message createdMessage = messageService.createMessage(message);
        assertNotNull(createdMessage);
        assertEquals("Test Subject", createdMessage.getSubject());
        assertEquals(sender, createdMessage.getSender());
        assertEquals(receiver, createdMessage.getReceiver());
        verify(messageDAO, times(1)).save(message);
    }

    @Test
    public void testGetMessagesBySender() {
        Employee sender = createEmployee(1L);
        Message message = createMessage(1L, sender, null);
        when(messageDAO.findBySenderId(anyLong())).thenReturn(Collections.singletonList(message));

        List<Message> messages = messageService.getMessagesBySender(sender.getEmployeeId());
        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("Test Subject", messages.get(0).getSubject());
        assertEquals(sender, messages.get(0).getSender());
        verify(messageDAO, times(1)).findBySenderId(sender.getEmployeeId());
    }

    @Test
    public void testGetMessagesByReceiver() {
        Employee receiver = createEmployee(2L);
        Message message = createMessage(1L, null, receiver);
        when(messageDAO.findByReceiverId(anyLong())).thenReturn(Collections.singletonList(message));

        List<Message> messages = messageService.getMessagesByReceiver(receiver.getEmployeeId());
        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("Test Subject", messages.get(0).getSubject());
        assertEquals(receiver, messages.get(0).getReceiver());
        verify(messageDAO, times(1)).findByReceiverId(receiver.getEmployeeId());
    }

    @Test
    public void testGetMessagesBySenderNotFound() {
        when(messageDAO.findBySenderId(anyLong())).thenReturn(Collections.emptyList());

        List<Message> messages = messageService.getMessagesBySender(1L);
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
        verify(messageDAO, times(1)).findBySenderId(1L);
    }

    @Test
    public void testGetMessagesByReceiverNotFound() {
        when(messageDAO.findByReceiverId(anyLong())).thenReturn(Collections.emptyList());

        List<Message> messages = messageService.getMessagesByReceiver(2L);
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
        verify(messageDAO, times(1)).findByReceiverId(2L);
    }
}
