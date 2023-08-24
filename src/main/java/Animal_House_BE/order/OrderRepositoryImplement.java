package Animal_House_BE.order;

import Animal_House_BE.item.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImplement implements OrderRepository{
    @Autowired
    private ItemService itemService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private String ORDER_TABLE_NAME = "orders";


    private String CLIENT_TABLE_NAME = "clients";

    private String ITEM_TABLE_NAME = "items";

    @Override
    public Order getTemporalOrderByClientId(int clientId) {
        try {
            String sql = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE client_id=? AND order_status='TEMPORAL' ";
            Order fetchedOrder = jdbcTemplate.queryForObject(sql, new OrderMapper(), clientId);
            return fetchedOrder;
        } catch (EmptyResultDataAccessException ex) {
            // No temporal order found for the given clientId, return null or handle as needed
            return null;
        } catch (DataAccessException ex) {
            // Handle other data access exceptions if needed
            throw new RuntimeException("Failed to retrieve temporal order", ex);
        }
    }


    @Override
    public void createOrderByClientId(String orderDate, int clientId, int itemNumber, String deliveryAddress, int itemPrice, String status) {
        String sql = "INSERT INTO " + ORDER_TABLE_NAME + " (order_date, client_id,item_id_list,delivery_address, total_price, order_status) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                orderDate, clientId, itemNumber, deliveryAddress, itemPrice, status);
    }

    @Override
    public void addToOrderByClientId(int clientId, int itemNumber, int totalPrice) {
        String sql = "SELECT order_id FROM " + ORDER_TABLE_NAME + " WHERE client_id=? AND order_status='TEMPORAL' ";
        int orderId = jdbcTemplate.queryForObject(sql, Integer.class, clientId);
        Order existingTempOrder = getTemporalOrderByClientId(clientId);
        String existingItemIdList = existingTempOrder.getItemIdList();
        String newItemList = existingItemIdList + " " + String.valueOf(itemNumber);
        sql = "UPDATE " + ORDER_TABLE_NAME + " SET item_id_list = ?, total_price=? WHERE order_id = ?";
        jdbcTemplate.update(sql, newItemList, totalPrice, orderId);


        }

    @Override
    public void removeFromOrderByClientId(int clientId, int itemNumber, int totalPrice) {
        String sql = "SELECT order_id FROM " + ORDER_TABLE_NAME + " WHERE client_id=? AND order_status='TEMPORAL' ";
        int orderId = jdbcTemplate.queryForObject(sql, Integer.class, clientId);
        Order existingTempOrder = getTemporalOrderByClientId(clientId);
        String existingItemIdList = existingTempOrder.getItemIdList();
        //remove from existing list
        String itemNumberAsString = String.valueOf(itemNumber);
        String newItemIdList = existingItemIdList.replaceFirst("\\b" + itemNumberAsString + "\\b", "").trim();
        newItemIdList = newItemIdList.replaceAll("\\s+", " "); // Remove extra spaces
        sql = "UPDATE " + ORDER_TABLE_NAME + " SET item_id_list = ?, total_price=? WHERE order_id = ?";
        jdbcTemplate.update(sql, newItemIdList, totalPrice, orderId);

        }


    @Override
    public List<Order> getClosedOrdersByClientId(int clientId) {
        String sql = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE client_id=? AND order_status='CLOSED'";
        return jdbcTemplate.query(sql, new OrderMapper(), clientId);
    }

    @Override
    public void deleteTemporalOrderByClientId(int clientId) {
        String sql = "DELETE FROM " +  ORDER_TABLE_NAME + " WHERE client_id=? AND order_status='TEMPORAL' ";
        jdbcTemplate.update(sql, clientId);

    }

    @Override
    public void deleteClosedOrdersByClientId(int clientId) {
        String sql = "DELETE FROM " + ORDER_TABLE_NAME + " WHERE client_id=?";
        jdbcTemplate.update(sql, clientId);

    }

    @Override
    public void closeTemporalOrder(int clientId) {
        String sql = "UPDATE " + ORDER_TABLE_NAME + " SET order_status = ? WHERE client_id = ? AND order_status='TEMPORAL'";
        jdbcTemplate.update(sql, "CLOSED", clientId);


    }


}
