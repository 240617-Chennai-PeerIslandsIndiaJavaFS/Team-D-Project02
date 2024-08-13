package com.teamD.RevTaskManagement.clientTest;

import com.teamD.RevTaskManagement.dao.ClientDAO;
import com.teamD.RevTaskManagement.exceptions.ClientNotFoundException;
import com.teamD.RevTaskManagement.model.Client;
import com.teamD.RevTaskManagement.service.ClientService;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientDAO clientDAO;

    @Mock
    private ModelUpdater modelUpdater;

    @InjectMocks
    private ClientService clientService;

    private Client client1;
    private Client client2;
    private List<Client> clients;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        clients = Arrays.asList(client1, client2);
    }

    @Test
    void createClient() {
        when(clientDAO.save(any(Client.class))).thenReturn(client1);
        Client createdClient = clientService.createClient(client1);
        assertNotNull(createdClient);
        assertEquals(client1.getPointOfContact(), createdClient.getPointOfContact());
    }

    @Test
    void updateClient() {
        when(clientDAO.findById(anyLong())).thenReturn(Optional.of(client1));
        when(modelUpdater.updateFields(any(Client.class), any(Client.class))).thenReturn(client2);
        when(clientDAO.save(any(Client.class))).thenReturn(client2);

        Client updatedClient = clientService.updateClient(1L, client2);

        assertNotNull(updatedClient);
        assertEquals(client2.getPointOfContact(), updatedClient.getPointOfContact());
        verify(clientDAO, times(1)).save(any(Client.class));
    }


    @Test
    void getClientById() {
        when(clientDAO.findById(anyLong())).thenReturn(Optional.of(client1));

        Client foundClient = clientService.getClientById(1L);

        assertNotNull(foundClient);
        assertEquals(client1.getPointOfContact(), foundClient.getPointOfContact());
        verify(clientDAO, times(1)).findById(anyLong());
    }


    @Test
    void getAllClients() {
        when(clientDAO.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clientDAO, times(1)).findAll();
    }

    @Test
    void deleteClientById() {
        when(clientDAO.existsById(anyLong())).thenReturn(true);
        when(clientDAO.findById(anyLong())).thenReturn(Optional.of(client1));

        Client client = clientService.deleteClientById(1L);

        assertEquals(client1,client);

        verify(clientDAO, times(1)).deleteById(anyLong());
    }

}

