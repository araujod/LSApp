package ca.douglas.lsapp.DB;

import java.io.Serializable;

public class OrderStatus implements Serializable {

    private int storeID;
    private int orderID;
    private String userID;
    private String orderStatus;

    public OrderStatus(int storeID, int orderID, String userID, String orderStatus)
    {
        this.storeID = storeID;
        this.orderID = orderID;
        this.userID = userID;
        this.orderStatus = orderStatus;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String userID) {
        this.orderStatus = orderStatus;
    }


}
