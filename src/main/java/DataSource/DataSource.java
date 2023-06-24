/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public abstract class DataSource<T> {
    public static String connectionString = "jdbc:mysql://localhost:3306/vendingmachine";
                    
    public static String username = "root";
    public static String password = "";
    
    public static Connection getConnection() {
        try {
          return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            return null;
        }
        
    }
    
    abstract public ArrayList<T> all();
    abstract public T get(Object ...id);
    abstract public boolean insert(T object) throws Exception;    
    abstract public boolean update(T object) throws Exception;

    
}
