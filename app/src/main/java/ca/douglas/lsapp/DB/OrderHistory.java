package ca.douglas.lsapp.DB;

import java.util.ArrayList;

public class OrderHistory {
    private Order order;
    private Restaurant store;
    private ArrayList<OrderDetail> orderDetails;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Restaurant getStore() {
        return store;
    }

    public void setStore(Restaurant store) {
        this.store = store;
    }
}
