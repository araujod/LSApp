
/* Final Project - CSIS4175 - Create Database and Tables */

/* DROP the database if it exists */
DROP DATABASE IF EXISTS LSApp;

/* CREATE THE DATABASE */
CREATE DATABASE LSApp;

/* Use the database */
USE LSApp;

CREATE TABLE Store ( 
    StoreID     INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Email       VARCHAR(50) NOT NULL,
    Phone       VARCHAR(20) NOT NULL,
    Name        VARCHAR(100) NOT NULL,
    Address     VARCHAR(100) NOT NULL,
    LogoURL     VARCHAR(200) NOT NULL,
    Lng         VARCHAR(30) NOT NULL,
	Lat         VARCHAR(30) NOT NULL,
    CONSTRAINT 	Store_PK PRIMARY KEY(StoreID)
);

CREATE TABLE Product ( 
    ProductID   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Name        VARCHAR(40) NOT NULL,
    Description VARCHAR(200) NOT NULL,
    Category    VARCHAR(50) NOT NULL,
    PictureURL  VARCHAR(200) NOT NULL,
    Price       FLOAT(6,2) NOT NULL,
    Highlight   BOOLEAN NOT NULL,
    CONSTRAINT  Product_PK PRIMARY KEY(ProductID)
);

CREATE TABLE StoreProduct ( 
	StoreID     INT UNSIGNED NOT NULL,
    ProductID   INT UNSIGNED NOT NULL,
    Available   BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT 	StoreProduct_PK PRIMARY KEY (ProductID, StoreID),
   	CONSTRAINT 	StProd_Product_FK FOREIGN KEY (ProductID) 
                                    REFERENCES Product(ProductID)
                                    ON DELETE CASCADE,
    CONSTRAINT 	StProd_Store_FK FOREIGN KEY (StoreID) 
                                    REFERENCES Store(StoreID)
                                    ON DELETE CASCADE
);

CREATE TABLE Orders ( 
    OrderID     INT UNSIGNED NOT NULL AUTO_INCREMENT,
    StoreID     INT UNSIGNED NOT NULL,
    UserID      INT UNSIGNED NOT NULL,
    DateTime    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Total       FLOAT(8,2) NOT NULL,
    Total_Items SMALLINT NOT NULL,
    CONSTRAINT 	Orders_PK PRIMARY KEY (OrderID),
   	CONSTRAINT 	Orders_Store_FK FOREIGN KEY (StoreID) 
                                    REFERENCES Store(StoreID)
                                    ON DELETE CASCADE
);

CREATE TABLE Order_Detail ( 
    OrderID     INT UNSIGNED NOT NULL,
    ProductID   INT UNSIGNED NOT NULL,
    Quantity    SMALLINT UNSIGNED NOT NULL,
    SubTotal    FLOAT(8,2) not null,
    CONSTRAINT Order_Detail_PK PRIMARY KEY (OrderID, ProductID),
    CONSTRAINT OrdDet_Orders_FK FOREIGN KEY (OrderID) 
                                    REFERENCES Orders(OrderID),
    CONSTRAINT OrdDet_Product_FK FOREIGN KEY (ProductID) 
                                    REFERENCES Product(ProductID)
);