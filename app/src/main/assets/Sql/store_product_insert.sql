
/**

Final Project - CSIS4175 - Insert Availability into Store_Product Table

CREATE TABLE StoreProduct (
	StoreID     INT UNSIGNED NOT NULL,
    ProductID   INT UNSIGNED NOT NULL,
    Available   CHAR(5) NOT NULL DEFAULT 'TRUE',
    CONSTRAINT 	StoreProduct_PK PRIMARY KEY (ProductID, StoreID),
   	CONSTRAINT 	StProd_Product_FK FOREIGN KEY (ProductID) 
                                    REFERENCES Product(ProductID)
                                    ON DELETE CASCADE,
    CONSTRAINT 	StProd_Store_FK FOREIGN KEY (StoreID) 
                                    REFERENCES Store(StoreID)
                                    ON DELETE CASCADE
);

*/

INSERT INTO StoreProduct (
    StoreID,
	ProductID    
)
SELECT 
	StoreID,
    ProductID
FROM 
    Store,Product;