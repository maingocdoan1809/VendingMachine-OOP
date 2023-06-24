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
public class Product implements Subject{
    private int id ;
    private String name;
    private float price;
    private String priceStr;
    private String imgPath;
    private int remainNums;
    private Observer observer;
    public Product(int id ) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public String getImgPath() {
        return imgPath;
    }

    public Product setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }
    
    public String getPriceFormat() {
        
        return this.priceStr;
    }

    public int getRemainNums() {
        return remainNums;
    }

    public Product setRemainNums(int remainNums) {
        this.remainNums = remainNums;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public float getPrice() {
        return price;
    }
    public Product setPrice(float price) {
        this.price = price;
        this.priceStr = Utils.Utility.toMoney(price);
        return this;
    }
    public Product setPriceStr(String priceStr) {
        this.priceStr = priceStr;
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
