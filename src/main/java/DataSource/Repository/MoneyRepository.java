/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MoneyRepository extends DataSource {

    @Override
    public ArrayList all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object get(Object... id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean delete(Object object) {
        try (var conn = DataSource.getConnection(); var stm = conn.createStatement()) {

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
        try (var conn = DataSource.getConnection(); var stm = conn.createStatement()) {

            int value = (Integer) object;

            String sqlQuery = "Update cash set count = count + 1 where value = " + value;
            stm.execute(sqlQuery);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error when processing money bank.");
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
