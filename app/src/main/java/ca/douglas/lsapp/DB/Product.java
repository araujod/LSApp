package ca.douglas.lsapp.DB;

public class Product {
    /*
    +-------------+------------------+------+-----+---------+----------------+
    | Field       | Type             | Null | Key | Default | Extra          |
    +-------------+------------------+------+-----+---------+----------------+
    | ProductID   | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
    | Name        | varchar(40)      | NO   |     | NULL    |                |
    | Description | varchar(200)     | NO   |     | NULL    |                |
    | Category    | varchar(50)      | NO   |     | NULL    |                |
    | PictureURL  | varchar(200)     | NO   |     | NULL    |                |
    | Price       | float(6,2)       | NO   |     | NULL    |                |
    | Highlight   | varchar(10)      | NO   |     | NULL    |                |
    +-------------+------------------+------+-----+---------+----------------+
     */
    private int ProductID;
    private String Name;
    private String Description;
    private String Category;
    private String PictureURL;
    private Double Price;
    private Boolean Highlight;

    public Product(int productID, String name, String description, String category, String pictureURL, Double price, Boolean highlight) {
        this.ProductID = productID;
        this.Name = name;
        this.Description = description;
        this.Category = category;
        this.PictureURL = pictureURL;
        this.Price = price;
        this.Highlight = highlight;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(String pictureURL) {
        PictureURL = pictureURL;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Boolean getHighlight() {
        return Highlight;
    }

    public void setHighlight(Boolean highlight) {
        Highlight = highlight;
    }
}
