/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class BankAccount {
    private String bankAccountUsername;
    private String bankName;
    private String bankAccountNumber;    
    private float bankBalance;
    
    
    public BankAccount(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    
    public float getBankBalance() {
        return bankBalance;
    }

    public BankAccount setBankBalance(float bankBalance) {
        this.bankBalance = bankBalance;
        return this;
    }

    public String getBankAccountUsername() {
        return bankAccountUsername;
    }

    public BankAccount setBankAccountUsername(String bankAccountUsername) {
        this.bankAccountUsername = bankAccountUsername;
        return this;
    }
    
    public String getBankName() {
        return bankName;
    }

    public BankAccount setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public BankAccount setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }
    
}
