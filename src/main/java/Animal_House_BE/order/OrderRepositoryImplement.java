package Animal_House_BE.order;


import Animal_House_BE.client.Client;

import Animal_House_BE.item.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

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


        String sql = "SELECT * FROM " + ORDER_TABLE_NAME + " WHERE client_id=? AND order_status='TEMPORAL' ";
        Order fetchedOrder = jdbcTemplate.queryForObject(sql, new OrderMapper(),clientId);
        return fetchedOrder;
    }

    @Override
    public void createOrderByClientId(String orderDate, int clientId, int itemNumber, String deliveryAddress, int itemPrice, String status) {
       String sql = "INSERT INTO " + ORDER_TABLE_NAME + " (order_date, client_id,item_id_list,delivery_address, total_price, order_status) VALUES (?,?,?,?,?,?)";
       jdbcTemplate.update(
               sql,
               orderDate,clientId,itemNumber,deliveryAddress,itemPrice, status);
        int existingItemQuantity = itemService.getItemQuantity(Math.abs(itemNumber));
        existingItemQuantity -= 1;
        sql = "UPDATE " + ITEM_TABLE_NAME + " SET item_quantity = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, existingItemQuantity, Math.abs(itemNumber));
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

        // Subtract an item from the stock

        int existingItemQuantity = itemService.getItemQuantity(Math.abs(itemNumber));
        existingItemQuantity -= 1;
        sql = "UPDATE " + ITEM_TABLE_NAME + " SET item_quantity = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, existingItemQuantity, Math.abs(itemNumber));

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

        //add back to items stock
        // Subtract an item from the stock

        int existingItemQuantity = itemService.getItemQuantity(itemNumber);
        existingItemQuantity += 1;
        sql = "UPDATE " + ITEM_TABLE_NAME + " SET item_quantity = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, existingItemQuantity, itemNumber);
        if (newItemIdList.equals("")) {
            sql = "DELETE FROM " + ORDER_TABLE_NAME + " WHERE order_id = ?";
            jdbcTemplate.update(sql, orderId);
        }

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


}
