package Animal_House_BE.client;

import Animal_House_BE.order.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ClientRepositoryImplement implements ClientRepository {
    private String CLIENTS_TABLE_NAME = "clients";
    private String ORDER_TABLE_NAME = "orders";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    OrderRepository orderRepository;
    @Override
    public void createClient(Client client) {
        try {
            String sql = "INSERT INTO " + CLIENTS_TABLE_NAME +
                    "(first_name, last_name, email, password, address) VALUES (?, ?, ?, ?, ?)";

            jdbcTemplate.update(
                    sql,
                    client.getFirstName(),
                    client.getLastName(),
                    client.getEmail(),
                    client.getPassword(),
                    client.getAddress()
            );
        } catch (DataAccessException ex) {
            // Handle the exception or log the error
            throw new RuntimeException("Failed to create client", ex);
        }
    }




    @Override
    public ClientResponse getClientByEmail(String email) {
        String sql = "SELECT * FROM " + CLIENTS_TABLE_NAME + " WHERE email=?";
        try {
            Client fetchedClient = jdbcTemplate.queryForObject(sql, new ClientMapper(), email);
            ClientResponse clientToReturn = new ClientResponse(
                    fetchedClient.getFirstName(),
                    fetchedClient.getLastName(),
                    fetchedClient.getEmail(),
                    fetchedClient.getPassword(),
                    fetchedClient.getAddress()
            );
            return clientToReturn;
        } catch (EmptyResultDataAccessException ex) {
            // No client found with the given email, return an empty ClientResponse
            return new ClientResponse("", "", "", "", "");
        } catch (DataAccessException ex) {
            // Handle other data access exceptions
            throw new RuntimeException("Failed to retrieve client information", ex);
        }
    }


    @Override
    public void updateClientDetails(Client client) {
            String sql2 = "UPDATE " + CLIENTS_TABLE_NAME + " SET email=?, password=?, address=? WHERE first_name=? AND last_name=?";
            jdbcTemplate.update(sql2,
                    client.getFirstName(),
                    client.getLastName(),
                    client.getEmail(),
                    client.getPassword(),
                    client.getAddress());

        }

    @Override
    public void deleteClient(String email) {
        //get client id before he is erased
        String sql = "SELECT client_id FROM " + CLIENTS_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        orderRepository.deleteTemporalOrderByClientId(clientId);
        orderRepository.deleteClosedOrdersByClientId(clientId);
        sql = "DELETE FROM " + CLIENTS_TABLE_NAME + " WHERE client_id=?";
        jdbcTemplate.update(sql, clientId);
    }
}







