package com.tsg.restfulservice.model;

import com.tsg.restfulservice.model.Clients;
import org.springframework.stereotype.Repository;

@Repository

public class ClientDAO {

    private  static Clients list = new Clients();

    static
    {
        list.getClientList().add(new Client(1, "Prem", "Tiwari", "chapradreams@gmail.com"));
        list.getClientList().add(new Client(2, "Vikash", "Kumar", "abc@gmail.com"));
        list.getClientList().add(new Client(3, "Ritesh", "Ojha", "asdjf@gmail.com"));
    }

    // Method to return the list
    public Clients getAllClients(){
        return list;
    }

    // Method to add a client to the client list
    public void addClient(Client client){
        list.getClientList().add(client);
    }
}
