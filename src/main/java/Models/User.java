/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class User {
    private String username = "";
    private String password = "";
    private BankAccount account = null;
    
    public User(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }
    public BankAccount getAccount() {
        return account;
    }

    public User setAccount(BankAccount account) {
        this.account = account;
        return this;
    }
    
    
    
}
