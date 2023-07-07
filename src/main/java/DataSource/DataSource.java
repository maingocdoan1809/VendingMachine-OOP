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
/**
 * Class Base cho các repository
 */
public abstract class DataSource<T> {
    // mặc định lưu một connection string tới mysql database.
    public static String connectionString = "jdbc:mysql://localhost:3306/vendingmachine";
                    
    public static String username = "root";
    public static String password = "";
    
    // hàm trả về một connection tới database theo connectionString
    public static Connection getConnection() {
        try {
          return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            return null;
        }
        
    }
    // trả về tất cả các Object có kiểu dữ liệu T
    abstract public ArrayList<T> all();
    // trả về một object T, thỏa mãn các tham số id được truyền vào
    abstract public T get(Object ...id);
    // thêm mới một object T vào database
    abstract public boolean insert(T object) throws Exception;    
    // update một object trong database
    abstract public boolean update(T object) throws Exception;

    
}
