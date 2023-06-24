/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import Models.BankAccount;
import java.net.ConnectException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class BankAccountRepository extends DataSource<BankAccount> {

    public static String tableName = "bankaccounts";
    public static String tableNameForeign = "banks";
    public static String tableNameForeignKey = "id";
    public static String foreignKey = "bank_id";
    public static String PRIMARYKEY = "account_number";
    public static String ACCOUNTNAME = "account_name";
    public static String ACCOUNTPASSWORD = "account_password";
    public static String ACCOUNTBALANCE = "account_balance";
    public static String BANKNAME = "name";

    @Override
    public ArrayList<BankAccount> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BankAccount get(Object... credential) {
        BankAccount account = null;
        try ( var connection = DataSource.getConnection()) {
            if (connection == null) {
                throw new ConnectException("Cannot connect to database");
            }
            try ( var stm = connection.createStatement()) {
                String queryStr = "";
                if (credential.length == 1) {
                    queryStr = String.format("Select * from %s inner join %s on %s = %s where %s = '%s'",
                            tableName, tableNameForeign, foreignKey, tableNameForeignKey, PRIMARYKEY, credential[0]);
                } else {
                    queryStr = String.format("Select * from %s inner join %s on %s = %s where %s = '%s' and %s = '%s'",
                            tableName, tableNameForeign, foreignKey, tableNameForeignKey, PRIMARYKEY, credential[0], ACCOUNTPASSWORD, credential[1]);
                }
                var result = stm.executeQuery(
                        queryStr);
                if (result.next()) {
                    return new BankAccount(
                            result.getString(PRIMARYKEY)
                    ).setBankAccountUsername(result.getString(ACCOUNTNAME))
                            .setBankName(result.getString(BANKNAME))
                            .setBankBalance(result.getFloat(ACCOUNTBALANCE));

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(BankAccount object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(BankAccount object) throws Exception {
        var connection = DataSource.getConnection();
        if (connection == null) {
            throw new ConnectException("Cannot connect to database");
        }
        try ( var stm = connection.createStatement()) {
            connection.setAutoCommit(false);

            stm.execute(
                    String.format("Update %s set %s = %f where %s = '%s'",
                            tableName, ACCOUNTBALANCE, object.getBankBalance(), PRIMARYKEY, object.getBankAccountNumber()));
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
