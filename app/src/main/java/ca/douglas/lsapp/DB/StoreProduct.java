package ca.douglas.lsapp.DB;

public class StoreProduct {
    private int StoreID;
    private int ProductID;
    private boolean Available;

    public StoreProduct(int storeID, int productID, boolean available)
    {
        this.StoreID = storeID;
        this.ProductID = productID;
        this.Available = available;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        Available = available;
    }
}
