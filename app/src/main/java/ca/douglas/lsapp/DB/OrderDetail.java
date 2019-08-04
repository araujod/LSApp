package ca.douglas.lsapp.DB;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    /*
    OrderID     INT UNSIGNED NOT NULL,
    ProductID   INT UNSIGNED NOT NULL,
    Quantity    SMALLINT UNSIGNED NOT NULL,
    SubTotal    FLOAT(8,2) not null,
     */

    private int OrderID;
    private int ProductID;
    private int Quantity;
    private float SubTotal;

    public OrderDetail(float SubTotal, int ProductID) {
        Quantity = 1;
        this.SubTotal = SubTotal;
        this.ProductID = ProductID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(float subTotal) {
        SubTotal = subTotal;
    }
}
