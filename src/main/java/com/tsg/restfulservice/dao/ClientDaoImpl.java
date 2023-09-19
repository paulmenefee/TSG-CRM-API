package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDAO {

    private JdbcTemplate jdbcTemplate;

    public ClientDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Client addClient(Client client) {
        String sql = "INSERT INTO " +
                "Client(clientName, " +
                "clientAddress, clientCity, clientState, clientZip, " +
                "clientContactFirstName, clientContactLastName, " +
                "clientContactEmail, clientContactPhone) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getCompanyName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getCity());
            statement.setString(4, client.getState());
            statement.setString(5, client.getZip());
            statement.setString(6, client.getContactFirstName());
            statement.setString(7, client.getContactLastName());
            statement.setString(8, client.getContactEmail());
            statement.setString(9, client.getContactPhone());
            return statement;
        }, keyHolder);
        client.setClientId(keyHolder.getKey().intValue());

        return client;
    }

    @Override
    public List<Client> getAllClients() {
        String sql = "select * from client";
        List<Client> clientList = jdbcTemplate.query(sql, new ClientMapper());
        return clientList;
    }

    @Override
    public Client getClientById(int id) {
        String sql = "select * from client where clientId = ?";
        Client client = jdbcTemplate.queryForObject(sql, new ClientMapper(), id);
        return client;
    }

    @Override
    public void deleteClientById(int id) {
        String sql = "DELETE FROM Client where clientId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Client updateClientById(int id, Client client) {
        String sql = "Update Client set ClientId = ?, " +
                "ClientName = ?, " +
                "ClientAddress = ?, " +
                "ClientCity = ?, " +
                "ClientState = ?, " +
                "ClientZip = ?, " +
                "ClientContactFirstName = ?, " +
                "ClientContactLastName = ?, " +
                "ClientContactEmail = ?, " +
                "ClientContactPhone = ? " +
                "Where clientId = ?";
        jdbcTemplate.update(sql, client.getClientId(), client.getCompanyName(),
                client.getAddress(), client.getCity(),
                client.getState(), client.getZip(),
                client.getContactFirstName(), client.getContactLastName(),
                client.getContactEmail(), client.getContactPhone(),
                client.getClientId());
        return client;
    }

    public class ClientMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int index) throws SQLException {
            Client client = new Client();
            client.setClientId(rs.getInt("ClientId"));
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
