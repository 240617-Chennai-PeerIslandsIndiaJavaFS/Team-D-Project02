package com.teamD.RevTaskManagement.clientTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.ClientController;
import com.teamD.RevTaskManagement.model.Client;
import com.teamD.RevTaskManagement.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        client1 = new Client();
        client1.setClientId(1L);
        client1.setPointOfContact("Client One");
        client1.setEmail("client1@example.com");
        client1.setContact("1234567890");

        client2 = new Client();
        client2.setClientId(2L);
        client2.setEmail("client2@example.com");
        client2.setPointOfContact("Client Two");
        client2.setContact("0987654321");

    }

    @Test
    void testGetClientById() throws Exception {
        when(clientService.getClientById(anyLong())).thenReturn(client1);

        mockMvc.perform(get("/api/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.pointOfContact").value("Client One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.messages").value("Client Fetched successfully"));
    }

    @Test
    void testGetAllClients() throws Exception {
        List<Client> clientList = Arrays.asList(client1, client2);
        when(clientService.getAllClients()).thenReturn(clientList);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].pointOfContact").value("Client One"))
                .andExpect(jsonPath("$.data[1].pointOfContact").value("Client Two"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.messages").value("All Clients are Fetched successfully"));
    }

    @Test
    void testCreateClient() throws Exception {
        when(clientService.createClient(any(Client.class))).thenReturn(client1);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.pointOfContact").value("Client One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.messages").value("Client is Created Successfully"));
    }

    @Test
    void testUpdateClient() throws Exception {
        when(clientService.updateClient(anyLong(), any(Client.class))).thenReturn(client1);

        mockMvc.perform(put("/api/clients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.pointOfContact").value("Client One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.messages").value("Client Updated Successfully"));
    }

    @Test
    void testDeleteClient() throws Exception {
        when(clientService.deleteClientById(anyLong())).thenReturn(client1);

        mockMvc.perform(delete("/api/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.pointOfContact").value("Client One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.messages").value("Client is deleted Successfully"));
    }
}
