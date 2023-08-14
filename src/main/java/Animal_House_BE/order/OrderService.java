package Animal_House_BE.order;

import java.util.List;

public interface OrderService {
    Order getTemporalOrderByClientEmail (String clientEmail);
    void createOrderByClientEmail (String clientEmail, int itemNumber);
    List<Order> getClosedOrdersByClientEmail (String clientEmail);
    void addToOrderByClientEmail (String email, int itemNumber);
    void removeFromOrderByClientEmail (String email, int itemNumber);
    void deleteTemporalOrderByClientEmail(String clientEmail);

    void closeTemporalOrder(String clientEmail);
}
