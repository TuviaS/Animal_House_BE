package Animal_House_BE.order;

import java.util.List;

public interface OrderRepository {
    Order getTemporalOrderByClientId (int clientId);
    void createOrderByClientId(String orderDate, int clientId, int itemNumber, String deliveryAddress,int itemPrice, String status);
    void addToOrderByClientId(int clientIed, int itemNumber, int totalPrice);
    void removeFromOrderByClientId(int clientIed, int itemNumber, int totalPrice);
    List<Order> getClosedOrdersByClientId (int clientId);
    void deleteTemporalOrderByClientId(int clientId);
    void deleteClosedOrdersByClientId(int clientId);

    void closeTemporalOrder(int clientId);
}
