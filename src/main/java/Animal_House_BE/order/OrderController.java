package Animal_House_BE.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your frontend URL
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping (value = "/order/create/{clientEmail}")
    public void createOrderByClientEmail(@PathVariable String clientEmail, @RequestBody int itemNumber) throws Exception {
        orderService.createOrderByClientEmail(clientEmail, itemNumber);
    }
    @PutMapping (value = "order/AddToOrder/{clientEmail}")
    public void addToOrderByClientEmail(@PathVariable String clientEmail, @RequestBody int itemNumber) throws Exception {
        orderService.addToOrderByClientEmail (clientEmail, itemNumber);
    }

    @PutMapping (value = "order/removeFromOrder/{clientEmail}")
    public void removeFromOrderByClientEmail(@PathVariable String clientEmail, @RequestBody int itemNumber) throws Exception {
        orderService.removeFromOrderByClientEmail (clientEmail, itemNumber);
    }

    @DeleteMapping (value = "order/delete/{clientEmail}")
    public void deleteTemporalOrderByClientEmail (@PathVariable String clientEmail) throws Exception {
        orderService.deleteTemporalOrderByClientEmail(clientEmail);
    }

    @GetMapping(value = "/order/getTemporalOrder/{clientEmail}")
    public Order getTemporalOrderByClientEmail(@PathVariable String clientEmail) {
        return orderService.getTemporalOrderByClientEmail(clientEmail);

    }

    @GetMapping(value = "/order/getClosedOrders/{clientEmail}")
    public List<Order> getClosedOrdersByClientEmail(@PathVariable String clientEmail) {
        return orderService.getClosedOrdersByClientEmail(clientEmail);
    }

    @PutMapping(value = "/order/closeOrder/{clientEmail}")
    public void closeTemporalOrder(@PathVariable String clientEmail) {
        orderService.closeTemporalOrder(clientEmail);
    }


}
