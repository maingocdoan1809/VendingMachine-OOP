/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MoneyRepository extends DataSource {

    public HashMap<Integer, Integer> getAll() {
        HashMap<Integer, Integer> all = null;

        try ( var conn = DataSource.getConnection();  var stm = conn.createStatement()) {

            String queryString = "Select * from cash";

            var result = stm.executeQuery(queryString);
            all = new HashMap<>();
            while (result.next()) {
                all.put(result.getInt("value"), result.getInt("count"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error when processing money bank.");
            e.printStackTrace();
            return null;
        }

        return all;
    }

    @Override
    public ArrayList all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object get(Object... id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean delete(Object object) {
        try ( var conn = DataSource.getConnection();  var stm = conn.createStatement()) {

            int value = (Integer) object;
            String getCount = "Select count from cash where value = " + value;

            var result = stm.executeQuery(getCount);
            if (result.next() && result.getInt("count") > 0) {
                String sqlQuery = "Update cash set count = count - 1 where value = " + value + " and count > 0";
                stm.execute(sqlQuery);
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error when processing money bank.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean insert(Object object) {
        try ( var conn = DataSource.getConnection();  var stm = conn.createStatement()) {

            if (object instanceof Integer) {
                int value = (Integer) object;
                String sqlQuery = "Update cash set count = count + 1 where value = " + value;
                stm.execute(sqlQuery);
            } else if (object instanceof Map.Entry) {
                var newObj = (Map.Entry<Integer, Integer>) object;
                
                int value = newObj.getKey();
                int count = newObj.getValue();
                
                // check count:
                
                var result = stm.executeQuery("Select count from cash where value = " + value);
                if ( result.next() && (result.getInt("count") + count) >=0 ) {
                    String sqlQuery = "Update cash set count = count + " + count + " where value = " + value;
                    stm.execute(sqlQuery);
                    
                } else {
                    //error:
                    throw  new Exception("Lượng tiền còn lại dưới 0, không hợp lệ.");
                }
                
                
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Object object) throws Exception {

        return true;
    }

}
