/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import static DataSource.Repository.BankAccountRepository.ACCOUNTBALANCE;
import static DataSource.Repository.BankAccountRepository.ACCOUNTNAME;
import static DataSource.Repository.BankAccountRepository.BANKNAME;
import static DataSource.Repository.BankAccountRepository.PRIMARYKEY;
import static DataSource.Repository.BankAccountRepository.foreignKey;
import static DataSource.Repository.BankAccountRepository.tableName;
import static DataSource.Repository.BankAccountRepository.tableNameForeign;
import static DataSource.Repository.BankAccountRepository.tableNameForeignKey;
import Models.BankAccount;
import Models.Product;
import java.net.ConnectException;
import java.util.ArrayList;
import javax.security.auth.login.AccountNotFoundException;
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
                    return new Product( Integer.parseInt( (String) id[0]) )
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
    public boolean insert(Product object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Product object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
