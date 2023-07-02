/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Models.Observer.Observer;
import Models.Observer.Subject;

/**
 *
 * @author Admin
 */
public class User implements Subject {

    private String username = "";
    private String password = "";
    private float currentMoney = (float)0.0;
    private BankAccount account = null;
    private Observer observer = null;
    private boolean _isAdmin = false;
    public User(String username) {
        this.username = username;
    }
    public boolean isAdmin() {
        return this._isAdmin;
    }
 
    public User setAdmin() {
        this._isAdmin = true;
        return this;
    }
    public String getPassword() {
        return password;
    }
    
    public float getCurrentMoney() {
        return this.currentMoney;
    }
    public float setCurrentMoney( float amount ) {
        if (amount < 0) {
            currentMoney = 0;
        } else {
            this.currentMoney = amount;
        }
        return amount;
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

    @Override
    public void register(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        if (this.observer != null) {
            this.observer.update(this);

        }
    }

}
