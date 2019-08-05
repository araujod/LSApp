package ca.douglas.lsapp.DB;

import com.google.protobuf.Timestamp;
import com.google.type.Date;

import java.util.ArrayList;

import ca.douglas.lsapp.Shared.Commom;

public class DBUtil {

    /*
    |       1 | store_logo | 778-837-4001 | COLLINGWOOD-KINGSWAY GLS 113                  | -123,0314733 | 49,2320012 |
    |       2 | store_logo | 778-837-4002 | CHAMPLAIN SQUARE GLS 194                      | -123,040184  | 49,2190888 |
    |       3 | store_logo | 778-837-4003 | BROADWAY & MAPLE GLS 117                      | -123,152006  | 49,2637476 |
    |       4 | store_logo | 778-837-4004 | BROADWAY & LILLOOET GLS 300                   | -123,0364495 | 49,260829  |
    |       5 | store_logo | 778-837-4005 | WHOLESALE CUSTOMER CENTRE (WCC VANCOUVER) 100 | -123,0383672 | 49,2618115 |
     */
    /*
    public static ArrayList<Restaurant> getRestaurantList() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant( 1 , "store_logo" , "778-837-4001","COLLINGWOOD-KINGSWAY GLS 113",49.2320012, -123.0314733  ));
        restaurants.add(new Restaurant(2 , "store_logo" , "778-837-4002" , "CHAMPLAIN SQUARE GLS 194", 49.2190888  , -123.040184));
        restaurants.add(new Restaurant( 3 , "store_logo" , "778-837-4003" , "BROADWAY & MAPLE GLS 117", 49.2637476 , -123.152006));
        restaurants.add(new Restaurant( 4 , "store_logo" , "778-837-4004" , "BROADWAY & LILLOOET GLS 300",49.260829  , -123.0364495));
        restaurants.add(new Restaurant( 5 , "store_logo", "778-837-4005", "WHOLESALE CUSTOMER CENTRE (WCC VANCOUVER) 100" , 49.2618115 , -123.0383672));
        return restaurants;
    }
    */

    /**
     * +---------+-----------+-----------+
     * | StoreID | ProductID | Available |
     * +---------+-----------+-----------+
     * |       5 |         1 | TRUE      |
     * |       5 |         2 | TRUE      |
     * |       5 |         3 | TRUE      |
     * |       5 |         4 | TRUE      |
     * |       5 |         5 | TRUE      |
     */
   /* public static ArrayList<StoreProduct> getStoreProductList() {
        ArrayList<StoreProduct> productAvailable = new ArrayList<>();
        productAvailable.add(new StoreProduct(  5,1 , true ));
        productAvailable.add(new StoreProduct(  5,2 , false ));
        productAvailable.add(new StoreProduct(  5,3 , true ));
        productAvailable.add(new StoreProduct(  5,4 , true ));
        productAvailable.add(new StoreProduct(  5,5 , false ));

        return productAvailable;
    }

    /**
     * +-----------+----------------------------------+----------------------------------------------+-----------+----------------------------------+-------+-----------+
     * | ProductID | Name                             | Description                                  | Category  | PictureURL                       | Price | Highlight |
     * +-----------+----------------------------------+----------------------------------------------+-----------+----------------------------------+-------+-----------+
     * |         1 | Bud Light                        | Bud Light Description                        | Beer      | budlight.png                     |  2.99 | TRUE      |
     * |         2 | Coors Light                      | Coors Light Description                      | Beer      | CoorsLight.png                   |  2.99 | FALSE     |
     * |         3 | Corona Extra                     | Corona Extra Description                     | Beer      | CoronaExtra.png                  |  2.99 | FALSE     |
     * |         4 | Miller Lite                      | Miller Lite Description                      | Beer      | MillerLite.png                   |  2.99 | FALSE     |
     * |         5 | Stella Artois                    | Stella Artois Description                    | Beer      | StellaArtois.png                 |  2.99 | FALSE     |
     */
    /*public static ArrayList<Product> getProductList() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Bud Light","Bud Light Description", "Beer","budlight", 2.99f, true));
        products.add(new Product(2, "Coors Light"," Coors Light Description", "Beer","coorslight", 2.99f, false));
        products.add(new Product(3, "Corona Extra","Corona Extra Description", "Beer","coronaextra", 2.99f, false));
        products.add(new Product(4, "Miller Lite","Miller Lite Description", "Beer","millerlite", 2.99f, false));
        products.add(new Product(5, "Stella Artois","Stella Artois Description", "Beer","stellaartois", 2.99f, false));


        return products;
    }

    /**
     *     OrderID     INT UNSIGNED NOT NULL,
     *     ProductID   INT UNSIGNED NOT NULL,
     *     Quantity    SMALLINT UNSIGNED NOT NULL,
     *     SubTotal    FLOAT(8,2) not null,
     */
    /*public static ArrayList<OrderDetail> getOrderDetailList(int orderID) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(new OrderDetail(orderID, 1,2,5.98f));
        orderDetails.add(new OrderDetail(orderID, 3,2,5.98f));
        orderDetails.add(new OrderDetail(orderID, 10,5,15f));
        return orderDetails;
    }

    /**
     *     OrderID     INT UNSIGNED NOT NULL AUTO_INCREMENT,
     *     StoreID     INT UNSIGNED NOT NULL,
     *     UserID      INT UNSIGNED NOT NULL,
     *     DateTime    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     *     Total       FLOAT(8,2) NOT NULL,
     *     Total_Items SMALLINT NOT NULL,
     */
  /*  public static ArrayList<Order> getOrderList(String userID) {
        ArrayList<Order> orders = new ArrayList<>();
        /*orders.add(new Order(0, 1,userID, Commom.getDate(2019,11,11),9.9f,10,"4300 Parkwood"));
        orders.add(new Order(1, 10,userID, Commom.getDate(2019,10,10),19.19f,1,"700 Royal Ave"));
        orders.add(new Order(2, 20,userID, Commom.getDate(2019,9,9),29.29f,2,"100 Kingsway"));
        return orders;
    }

    public static Restaurant getStoreFromID(int storeID) {
        return new Restaurant( storeID, "001LiquorStore@gmail.com", "778-837-4001", "COLLINGWOOD-KINGSWAY GLS 113","3436 KINGSWAY AVE, VANCOUVER, BC, Canada","store_logo",-123.0314733,49.2320012);
    }

    public static int getCountOrderDetail(int orderID) {
        return 3;
    }*/
}
