package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDAO {

    private JdbcTemplate jdbcTemplate;

    public ClientDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Client addClient(Client client) {
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        String sql = "select * from client";
        List<Client> clientList = jdbcTemplate.query(sql, new ClientMapper());
        return clientList;
    }

    @Override
    public Client getClientById(int id) {
        return null;
    }

    @Override
    public Client updateClientById(int id, Client client) {
        return null;
    }

    @Override
    public void deleteClientById(int id) {

    }

    public class ClientMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int index) throws SQLException {
            Client client = new Client();
            client.setId(rs.getInt("ClientId"));
            client.setCompanyName(rs.getString("ClientName"));
            client.setAddress(rs.getString("ClientAddress"));
            client.setCity(rs.getString("clientCity"));
            client.setState(rs.getString("ClientState"));
            client.setZip(rs.getString("ClientZip"));
            client.setContactFirstName(rs.getString("ClientContactFirstName"));
            client.setContactLastName(rs.getString("ClientContactLastName"));
            client.setContactEmail(rs.getString("ClientContactEmail"));
            client.setContactPhone(rs.getString("ClientContactPhone"));

            return client;
        }
    }
}
