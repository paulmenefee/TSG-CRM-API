package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.model.Client;
import com.tsg.restfulservice.model.ClientDAO;
import com.tsg.restfulservice.model.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    @Autowired
    public ClientDAO clientDAO;

    @GetMapping(path = "", produces = "application/json")
    public Clients getClients(){
        return clientDAO.getAllClients();
    }

    @PostMapping(path="/", consumes = "application/json", produces="application/json")
    public ResponseEntity<Object> addClient(@RequestBody Client client){

        int id = clientDAO.getAllClients().getClientList().size() + 1;

        client.setId(id);
        clientDAO.addClient(client);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
