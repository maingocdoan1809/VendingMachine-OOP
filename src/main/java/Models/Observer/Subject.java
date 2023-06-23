/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Observer;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public interface Subject {
    
    public void register(Observer observer);
    public void removeObserver(Observer observer);
    public abstract void notifyObservers();
}
