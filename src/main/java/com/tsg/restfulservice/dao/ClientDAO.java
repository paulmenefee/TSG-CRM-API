package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Client;


import java.util.List;

public interface ClientDAO {

    Client addClient(Client client);

    List<Client> getAllClients();

    Client getClientById(int id);

    Client updateClientById(int id, Client client);

    void deleteClientById(int id);
}
