/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import Models.BankAccount;
import Models.Product;
import java.net.ConnectException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ProductRepository extends DataSource<Product> {

    public static String tableName = "products";
    public static String PRIMARYKEY = "id";
    public static String PRODUCTNAME = "name";
    public static String PRODUCTPRICE = "price";
    public static String PRODUCTREMAIN = "remain";
    public static String PRODUCTIMAGE = "image";

    public int getNextId() throws Exception {
        var connection = DataSource.getConnection();
        if (connection == null) {
            throw new ConnectException("Cannot connect to database");
        }
        try ( var stm = connection.createStatement()) {

            var result = stm.executeQuery("Select MAX(%s) + 1 as nextID from %s".formatted(PRIMARYKEY, tableName));
            result.next();
            return result.getInt("nextID");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error.");
        } finally {
            connection.close();
        }
    }

    @Override
    public ArrayList<Product> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Product get(Object... id) {
        BankAccount account = null;
        try ( var connection = DataSource.getConnection()) {
            if (connection == null) {
                throw new ConnectException("Cannot connect to database");
            }
            try ( var stm = connection.createStatement()) {
                var result = stm.executeQuery("Select * from %s where %s = '%s' ".formatted(tableName, PRIMARYKEY, id[0]));
                if (result.next()) {
                    return new Product(Integer.parseInt((String) id[0]))
                            .setName(result.getString(PRODUCTNAME))
                            .setImgPath(result.getString(PRODUCTIMAGE))
                            .setPrice(result.getFloat(PRODUCTPRICE))
                            .setRemainNums(result.getInt(PRODUCTREMAIN));

                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public Product getTopSelling() {
        BankAccount account = null;
        try ( var connection = DataSource.getConnection()) {
            if (connection == null) {
                throw new ConnectException("Cannot connect to database");
            }
            try ( var stm = connection.createStatement()) {
                var result = stm.executeQuery(
                        "SELECT * from products WHERE products.id IN ("
                        + "   SELECT id from ("
                        + "        SELECT history.product as id, COUNT(history.product) as cnt from history\n"
                        + "        GROUP by history.product"
                        + "        ORDER BY cnt DESC"
                        + "        Limit 3"
                        + "   ) as b1"
                        + ")");
                if (result.next()) {
                    return new Product(result.getInt(PRIMARYKEY))
                            .setName(result.getString(PRODUCTNAME))
                            .setImgPath(result.getString(PRODUCTIMAGE))
                            .setPrice(result.getFloat(PRODUCTPRICE))
                            .setRemainNums(result.getInt(PRODUCTREMAIN));

                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(Product object) throws Exception {

        String name = object.getName();
        float price = object.getPrice();
        int remain = object.getRemainNums();

        if (name.equals("") || price <= 0.0 || remain < 0) {
            JOptionPane.showMessageDialog(null, "Invalid input");
            return false;
        }
        var connection = DataSource.getConnection();
        if (connection == null) {
            throw new ConnectException("Cannot connect to database");
        }
        try ( var stm = connection.createStatement()) {
            connection.setAutoCommit(false);
            System.out.println(String.format("Insert into %s(%s, %s, %s) values('%s', '%s', %d)",
                    tableName, PRODUCTNAME, PRODUCTPRICE, PRODUCTREMAIN,
                    name, price + "", remain));
            stm.execute(
                    String.format("Insert into %s(%s, %s, %s) values('%s', '%s', %d)",
                            tableName, PRODUCTNAME, PRODUCTPRICE, PRODUCTREMAIN,
                            name, price + "", remain));

            connection.commit();
            JOptionPane.showMessageDialog(null, "Add new Product successfully.");
            return true;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.close();
        }

    }

    @Override
    public boolean update(Product object) throws Exception {
        var connection = DataSource.getConnection();
        if (connection == null) {
            throw new ConnectException("Cannot connect to database");
        }
        try ( var stm = connection.createStatement()) {
            connection.setAutoCommit(false);

            stm.execute(
                    String.format("Update %s set %s = %d where %s = '%s'",
                            tableName, PRODUCTREMAIN, object.getRemainNums(), PRIMARYKEY, object.getId()));
            connection.commit();
            return true;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.close();
        }
    }

}
