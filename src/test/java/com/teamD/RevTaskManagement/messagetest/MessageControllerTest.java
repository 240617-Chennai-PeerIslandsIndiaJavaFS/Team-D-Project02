package com.teamD.RevTaskManagement.messagetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.MessageController;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Message;
import com.teamD.RevTaskManagement.service.MessageService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void testCreateMessage() throws Exception {
        Message message = createMessage(1L, createEmployee(1L), createEmployee(2L));
        when(messageService.createMessage(message)).thenReturn(message);

        mockMvc.perform(post("/api/messages/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(message)))
                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(
                        new BaseResponse<>("Message created successfully", message, 201))));
    }

    @Test
    void testGetMessagesBySender() throws Exception {
        Message message = createMessage(1L, createEmployee(1L), createEmployee(2L));
        when(messageService.getMessagesBySender(1L)).thenReturn(Arrays.asList(message));

        mockMvc.perform(get("/api/messages/sender/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(
                        new BaseResponse<>("Messages retrieved successfully", Arrays.asList(message), 200))));
    }

    @Test
    void testGetMessagesByReceiver() throws Exception {
        Message message = createMessage(1L, createEmployee(1L), createEmployee(2L));
        when(messageService.getMessagesByReceiver(2L)).thenReturn(Arrays.asList(message));

        mockMvc.perform(get("/api/messages/receiver/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(
                        new BaseResponse<>("Messages retrieved successfully", Arrays.asList(message), 200))));
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
}
