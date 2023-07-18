package Animal_House_BE.order;

import java.time.format.SignStyle;

public class Order {
    private String orderDate;
    private int clientId;
    private String itemIdList;
    private String deliveryAddress;
    private int totalPrice;
    private OrderStatus orderStatus;

    public Order(String orderDate, int clientId, String itemIdList, String deliveryAddress, int price, OrderStatus orderStatus) {
        this.orderDate = orderDate;
        this.clientId = clientId;
        this.itemIdList = itemIdList;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = price;
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getClientId() {
        return clientId;
    }

    public String getItemIdList() {
        return itemIdList;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setItemIdList(String itemIdList) {
        this.itemIdList = itemIdList;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPrice(int price) {
        this.totalPrice = price;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
