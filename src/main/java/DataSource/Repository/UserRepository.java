/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataSource.Repository;

import DataSource.DataSource;
import Models.User;
import java.net.ConnectException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class UserRepository extends DataSource<User> {

    public static String tableName = "users";
    public static String PRIMARYKEY = "username";
    public static String PASSWORD = "password";
    public static String BANKACCOUNT = "bank_account";

    @Override
    public ArrayList<User> all() {
        ArrayList<User> users = null;
        try ( var connection = DataSource.getConnection()) {
            if (connection == null) {
                throw new ConnectException("Cannot connect to database");
            }
            users = new ArrayList<>();

            try ( var stm = connection.createStatement()) {
                var result = stm.executeQuery("Select * from " + tableName);
                BankAccountRepository accountRepos = new BankAccountRepository();
                while (result.next()) {

                    users.add(
                            new User(
                                    result.getString(PRIMARYKEY)).setAccount(
                                    accountRepos.get(result.getString(BANKACCOUNT))
                            )
                    );

                }
                return users;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return users;
    }

    @Override
    public User get(Object... id) {
        User user = null;
        try ( var connection = DataSource.getConnection()) {
            if (connection == null) {
                throw new ConnectException("Cannot connect to database");
            }
            try ( var stm = connection.createStatement()) {

                if (id.length == 1) {
                    var result = stm.executeQuery("Select * from " + tableName
                            + " where " + PRIMARYKEY + " = '" + id[0] + "'");
                    if (result.next()) {
                        return new User(
                                result.getString(PRIMARYKEY)
                        );
                    }
                } else if (id.length == 2) {
                    var result = stm.executeQuery("Select * from " + tableName
                            + " where " + PRIMARYKEY + " = '" + id[0] + "' and " + PASSWORD + " = '" + id[1] + "'");
                    BankAccountRepository accountRepos = new BankAccountRepository();
                    if (result.next()) {
                        return new User(result.getString(PRIMARYKEY)).setAccount(
                                accountRepos.get(result.getString(BANKACCOUNT)));
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(User object) throws Exception {

        try ( var connection = DataSource.getConnection();  var stm = connection.createStatement()) {
            User user = get(object.getUsername());

            if (user == null) {
                stm.execute("Insert into %s(%s, %s, %s) values('%s', '%s', '%s')".formatted(
                        tableName, PRIMARYKEY, PASSWORD, BANKACCOUNT,
                        object.getUsername(), object.getPassword(),
                        object.getAccount().getBankAccountNumber()
                ));
            } else {
                throw new Exception(object.getUsername() + " đã tồn tại, vui lòng chọn tên khác.");
            }
            return true;
        }
    }

    @Override
    public boolean update(User object) {
        return false;
    }

}
