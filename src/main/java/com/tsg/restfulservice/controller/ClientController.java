package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.model.Client;
import com.tsg.restfulservice.dao.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tsg")
@CrossOrigin
public class ClientController {

    @Autowired
    public ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> clientList = clientDAO.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(clientList);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        try {
            Client client = clientDAO.getClientById(id);
            return new ResponseEntity(client, HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/client")
    public ResponseEntity<String> addClient(@RequestBody Client client) {
        clientDAO.addClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("New Client: " + client.getClientId() + " added");
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<String> removeClient(@PathVariable int id) {
        clientDAO.deleteClientById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Client: " + id + " deleted");
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody Client client) {
        Client updatedClient = clientDAO.updateClientById(id, client);
        return new ResponseEntity(updatedClient, HttpStatus.OK);
    }
}
