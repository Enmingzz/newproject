package data_access.interfaces;

import entity.product.Product;

import java.sql.SQLException;

public interface ProductUpdateTransferEmailDataAccessInterface {
    void updateProductEmail(Product product, String eMail) throws SQLException;
}
