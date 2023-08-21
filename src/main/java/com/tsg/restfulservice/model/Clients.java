package com.tsg.restfulservice.model;

import java.util.ArrayList;
import java.util.List;

public class Clients {

    private List<Client> clientList;

    public List<Client> getClientList(){
        if(clientList == null){
            clientList = new ArrayList<>();
        }

        return clientList;
    }

    public void setClientList(List<Client> clientList){
        this.clientList = clientList;
    }
}
