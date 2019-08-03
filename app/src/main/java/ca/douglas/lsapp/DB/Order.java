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
    private int StoreID;
    private String UserID;
    private Date DateTime;
    private float Total;
    private int Total_Items;
    private String deliveryAddress;

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

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
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

}
