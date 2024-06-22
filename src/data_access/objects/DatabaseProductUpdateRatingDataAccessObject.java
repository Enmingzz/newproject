package data_access.objects;

import data_access.interfaces.ProductUpdateRatingDataAccessInterface;
import entity.product.Product;

import java.sql.*;


public class DatabaseProductUpdateRatingDataAccessObject implements ProductUpdateRatingDataAccessInterface {
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private String query;

    public DatabaseProductUpdateRatingDataAccessObject() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlserver://207project.database.windows.net:1433;" +
                "database=207Project;user=root207@207project;password={Project207};encrypt=true;trustServerCertificate=false;" +
                "hostNameInCertificate=*.database.windows.net;loginTimeout=30");
    }

    @Override
    public void updateProductRating(Product product, int rating) throws SQLException {
        query = "UPDATE Products SET Rating = ? WHERE ProductID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, rating);
        preparedStatement.setString(2, product.getProductID());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

    }
}
