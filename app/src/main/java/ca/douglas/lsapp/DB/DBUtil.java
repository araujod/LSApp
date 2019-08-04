package ca.douglas.lsapp.DB;

import java.util.ArrayList;

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
    public static ArrayList<StoreProduct> getStoreProductList() {
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
    public static ArrayList<Product> getProductList() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Bud Light","Bud Light Description", "Beer","BudLight.PNG", 2.99f, true));
        products.add(new Product(2, "Coors Light"," Coors Light Description", "Beer","CoorsLight.PNG", 2.99f, false));
        products.add(new Product(3, "Corona Extra","Corona Extra Description", "Beer","CoronaExtra.PNG", 2.99f, false));
        products.add(new Product(4, "Miller Lite","Miller Lite Description", "Beer","MillerLite.PNG", 2.99f, false));
        products.add(new Product(5, "Stella Artois","Stella Artois Description", "Beer","StellaArtois.PNG", 2.99f, false));


        return products;
    }
}
