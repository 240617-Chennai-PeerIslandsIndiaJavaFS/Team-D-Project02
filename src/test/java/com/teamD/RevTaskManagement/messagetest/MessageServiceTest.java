package com.teamD.RevTaskManagement.messagetest;

import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.dao.MessageDAO;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Message;
import com.teamD.RevTaskManagement.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageDAO messageDAO;

    @Mock
    private EmployeeDAO employeeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMessage() {
        Message message = new Message();
        when(messageDAO.save(message)).thenReturn(message);
        Message result = messageService.createMessage(message);
        assertEquals(message, result);
        verify(messageDAO, times(1)).save(message);
    }

    @Test
    void getMessagesBySender() {
        Long senderId = 1L;
        Employee sender = new Employee();
        Message message = new Message();
        when(employeeDAO.findById(senderId)).thenReturn(Optional.of(sender));
        when(messageDAO.findBySender(sender)).thenReturn(List.of(message));
        List<Message> result = messageService.getMessagesBySender(senderId);
        assertFalse(result.isEmpty());
        verify(messageDAO, times(1)).findBySender(sender);
    }

    @Test
    void getMessagesByReceiver() {
        Long receiverId = 1L;
        Employee receiver = new Employee();
        Message message = new Message();
        when(employeeDAO.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(messageDAO.findByReceiver(receiver)).thenReturn(List.of(message));
        List<Message> result = messageService.getMessagesByReceiver(receiverId);
        assertFalse(result.isEmpty());
        verify(messageDAO, times(1)).findByReceiver(receiver);
    }
}
