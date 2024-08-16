package com.teamD.RevTaskManagement.controller;

import com.teamD.RevTaskManagement.model.Client;
import com.teamD.RevTaskManagement.service.ClientService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Client>> getClientById(@PathVariable Long id) {
        BaseResponse<Client> baseResponse = new BaseResponse<>("Client Fetched successfully",clientService.getClientById(id),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Client>>> getAllClients() {
        BaseResponse<List<Client>> baseResponse = new BaseResponse<>("All Clients are Fetched successfully",clientService.getAllClients(),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Client>> createClient(@RequestBody Client client) {
        BaseResponse<Client> baseResponse = new BaseResponse<>("Client is Created Successfully",clientService.createClient(client),HttpStatus.CREATED.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Client>> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        BaseResponse<Client> baseResponse = new BaseResponse<>("Client Updated Successfully",clientService.updateClient(id,clientDetails),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Client>> deleteClientById(@PathVariable Long id) {
        BaseResponse<Client> baseResponse = new BaseResponse<>("Client is deleted Successfully",clientService.deleteClientById(id),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<BaseResponse<Client>> findClientByName(@RequestParam String name) {
        BaseResponse<Client> baseResponse = new BaseResponse<>("Client fetched successfully",clientService.getClientByName(name),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
}