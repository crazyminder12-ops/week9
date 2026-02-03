package database;

import model.*;
import java.sql.*;

public class ProductDAO {

    public void addProduct(Product product) throws SQLException {
        Connection c = DatabaseConnection.getConnection();
        String sql = "INSERT INTO products(name, type, price) VALUES (?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, product.getName());
        ps.setString(2, product.getType());
        ps.setDouble(3, product.getPrice());

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public Product[] getAllProducts() throws SQLException {
        Connection c = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM products";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        Product[] temp = new Product[100];
        int count = 0;

        while (rs.next()) {
            String name = rs.getString("name");
            String type = rs.getString("type");
            double price = rs.getDouble("price");

            if (type.equals("Fresh")) {
                temp[count++] = new FreshProduct(name, price, 3);
            } else {
                temp[count++] = new PackagedProduct(name, price, 1);
            }
        }

        rs.close();
        ps.close();
        c.close();

        Product[] result = new Product[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    public boolean updateProduct(String name, double newPrice) throws SQLException {
        Connection c = DatabaseConnection.getConnection();
        String sql = "UPDATE products SET price = ? WHERE name = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setDouble(1, newPrice);
        ps.setString(2, name);

        boolean ok = ps.executeUpdate() > 0;
        ps.close();
        c.close();
        return ok;
    }

    public boolean deleteProduct(String name) throws SQLException {
        Connection c = DatabaseConnection.getConnection();
        String sql = "DELETE FROM products WHERE name = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, name);

        boolean ok = ps.executeUpdate() > 0;
        ps.close();
        c.close();
        return ok;
    }

    public Product[] searchByName(String keyword) throws SQLException {
        Connection c = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM products WHERE name ILIKE ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        Product[] temp = new Product[100];
        int count = 0;

        while (rs.next()) {
            String name = rs.getString("name");
            String type = rs.getString("type");
            double price = rs.getDouble("price");

            if (type.equals("Fresh")) {
                temp[count++] = new FreshProduct(name, price, 3);
            } else {
                temp[count++] = new PackagedProduct(name, price, 1);
            }
        }

        rs.close();
        ps.close();
        c.close();

        Product[] result = new Product[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    public Product[] searchByPriceRange(double min, double max) throws SQLException {
        Connection c = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ? ORDER BY price DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDouble(1, min);
        ps.setDouble(2, max);
        ResultSet rs = ps.executeQuery();

        Product[] temp = new Product[100];
        int count = 0;

        while (rs.next()) {
            String name = rs.getString("name");
            String type = rs.getString("type");
            double price = rs.getDouble("price");

            if (type.equals("Fresh")) {
                temp[count++] = new FreshProduct(name, price, 3);
            } else {
                temp[count++] = new PackagedProduct(name, price, 1);
            }
        }

        rs.close();
        ps.close();
        c.close();

        Product[] result = new Product[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
}
