package Animal_House_BE.order;
import Animal_House_BE.item.Item;
import Animal_House_BE.item.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class orderServiceImplement implements OrderService {
    private static final String CLIENT_TABLE_NAME = "clients";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderRepository orderRepository;
@Autowired
    private ItemService itemService;
    @Override
    public Order getTemporalOrderByClientEmail(String clientEmail) {
        String sql = "SELECT client_id FROM " + CLIENT_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{clientEmail}, Integer.class);
        return orderRepository.getTemporalOrderByClientId(clientId);
    }

    @Override
    public void createOrderByClientEmail(String clientEmail, int itemNumber) {
        String sql = "SELECT client_id FROM " + CLIENT_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{clientEmail}, Integer.class);

        sql = "SELECT address FROM " + CLIENT_TABLE_NAME + " WHERE email=?";
        String deliveryAddress = jdbcTemplate.queryForObject(sql, new Object[]{clientEmail}, String.class);
        LocalDate currentDate = LocalDate.now();  // Get the current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD/MM/YYYY");
        String orderDate = currentDate.format(formatter);
        Item firstItem= itemService.getItemByNumber(itemNumber);
        int itemPrice = itemService.getItemPrice(itemNumber);
        String status="TEMPORAL";
        orderRepository.createOrderByClientId(orderDate, clientId, itemNumber, deliveryAddress,itemPrice, status);
    }

    @Override
    public List<Order> getClosedOrdersByClientEmail(String clientEmail) {
        String sql = "SELECT client_id FROM " + CLIENT_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{clientEmail}, Integer.class);
        return orderRepository.getClosedOrdersByClientId(clientId);
    }

    @Override
    public void addToOrderByClientEmail(String email, int itemNumber) {
        Order tempOrder = getTemporalOrderByClientEmail(email);
        int clientId=tempOrder.getClientId();
        int totalPrice = 0;

        //get exiting list, turn it into an int array

        String existingItemOrder = getTemporalOrderByClientEmail(email).getItemIdList();
        String[] existingItemListAsStringArray = existingItemOrder.split(" ");
        int[] existingItemListAsIntArray = new int[existingItemListAsStringArray.length];
        for (int i = 0; i < existingItemListAsIntArray.length; i++) {
            existingItemListAsIntArray[i] = Integer.parseInt(existingItemListAsStringArray[i]);
        }
        // calculate the total price with new item
        for (int i = 0; i < existingItemListAsIntArray.length; i++) {
            totalPrice += itemService.getItemPrice(existingItemListAsIntArray[i]);
        }
        totalPrice+= (itemService.getItemPrice(itemNumber));
        orderRepository.addToOrderByClientId(clientId,itemNumber,totalPrice);

    }

    @Override
    public void removeFromOrderByClientEmail(String email, int itemNumber) {
        Order tempOrder = getTemporalOrderByClientEmail(email);
        int clientId=tempOrder.getClientId();
        int totalPrice = 0;

        //get exiting list, turn it into an int array

        String existingItemOrder = getTemporalOrderByClientEmail(email).getItemIdList();
        String[] existingItemListAsStringArray = existingItemOrder.split(" ");
        int[] existingItemListAsIntArray = new int[existingItemListAsStringArray.length];
        for (int i = 0; i < existingItemListAsIntArray.length; i++) {
            existingItemListAsIntArray[i] = Integer.parseInt(existingItemListAsStringArray[i]);
        }
        // calculate the total price with new item
        for (int i = 0; i < existingItemListAsIntArray.length; i++) {
            totalPrice += itemService.getItemPrice(existingItemListAsIntArray[i]);
        }
        totalPrice-= (itemService.getItemPrice(itemNumber));
        orderRepository.removeFromOrderByClientId(clientId, itemNumber, totalPrice);
    }

    @Override
    public void deleteTemporalOrderByClientEmail(String clientEmail) {
        Order tempOrder = getTemporalOrderByClientEmail(clientEmail);
        int clientId = tempOrder.getClientId();
        orderRepository.deleteTemporalOrderByClientId(clientId);
    }

    @Override
    public void closeTemporalOrder(String clientEmail) {
        Order temporalOrder = getTemporalOrderByClientEmail(clientEmail);
        int clientId = temporalOrder.getClientId();
        orderRepository.closeTemporalOrder(clientId);
    }


}


