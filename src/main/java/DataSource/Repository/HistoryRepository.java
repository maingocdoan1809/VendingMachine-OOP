/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import Models.History;
import java.net.ConnectException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class HistoryRepository extends DataSource<History>{
    public static String TABLENAME = "history";
    public static String USERNAME = "user";
    public static String PRODUCT = "product";
    public static String PRICE = "price";
    public static String TIME = "timie"; 
    @Override
    public ArrayList<History> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public History get(Object... id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public ArrayList<History> getByTime(int month, int year) {
        ArrayList<History> histories = null;
        try ( var connection = DataSource.getConnection()) {
            if (connection == null) {
                throw new ConnectException("Cannot connect to database");
            }
            histories = new ArrayList<>();

            try ( var stm = connection.createStatement()) {
                var result = stm.executeQuery("Select * from " 
                        + TABLENAME + " where Month(time) = " + month + " and Year(time) = " + year);
                BankAccountRepository accountRepos = new BankAccountRepository();
                while (result.next()) {

                    histories.add(
                            new History( result.getString(USERNAME) ,
                                    result.getInt(PRODUCT) , result.getFloat(PRICE))
                    );

                }
                return histories;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return histories;
        
    }
    @Override
    public boolean insert(History object) throws Exception {
        
        
        String username = object.getUsername();
        float price = object.getPrice();
        int pId = object.getProductId();

        if (username.equals("") || price <= 0.0 || pId < 0) {
            return false;
        }
        var connection = DataSource.getConnection();
        if (connection == null) {
            throw new ConnectException("Cannot connect to database");
        }
        try ( var stm = connection.createStatement()) {
            connection.setAutoCommit(false);
            stm.execute(String.format("Insert into %s(%s, %s, %s) values('%s', %d, %f)",
                TABLENAME, USERNAME, PRODUCT, PRICE,
                username, pId  ,price));
            connection.commit();
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
    public boolean update(History object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 
    
}
