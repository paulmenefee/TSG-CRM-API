package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.model.Client;
import com.tsg.restfulservice.dao.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {

    @Autowired
    public ClientDAO clientDAO;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> clientList = clientDAO.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(clientList);
    }

}
