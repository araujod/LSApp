package ca.douglas.lsapp.DB;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    /*
      OrderID     INT UNSIGNED NOT NULL AUTO_INCREMENT,
    StoreID     INT UNSIGNED NOT NULL,
    UserID      INT UNSIGNED NOT NULL,
    DateTime    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Total       FLOAT(8,2) NOT NULL,
    Total_Items SMALLINT NOT NULL,
     */
    private int OrderID;
    private int StoreID;
    private String UserID;
    private String DateTime;
    private float Total;
    private int Total_Items;
    private String deliveryAddress;


    public Order( int OrderID, int StoreID, String UserID, String DateTime, float Total, int Total_Items, String deliveryAddress) {
        this.OrderID = OrderID;
        this.StoreID = StoreID;
        this.UserID = UserID;
        this.DateTime = DateTime;
        this.Total = Total;
        this.Total_Items = Total_Items;
        this.deliveryAddress = deliveryAddress;
    }

    public Order(String deliveryAddress, String userId) {
        this.deliveryAddress = deliveryAddress;
        Total = 0;
        Total_Items=0;
        this.UserID = userId;
    }


    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public int getTotal_Items() {
        return Total_Items;
    }

    public void setTotal_Items(int total_Items) {
        Total_Items = total_Items;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }


    public String getValuesAsStringOrder(){
        String value;

        value = StoreID +","+UserID+","+Total+","+Total_Items+","+deliveryAddress.replace(","," ");

        return value;
    }



}
