/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class Product {
    private int id ;
    private String name;
    private float price;
    private String priceStr;
    private String imgPath;
    private int remainNums;
    public Product(int id ) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Product setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }
    
    public String getPriceFormat() {
        Locale vn = new Locale("vn", "VN");
        Currency vndong = Currency.getInstance(vn);
        NumberFormat vndongFormat = NumberFormat.getCurrencyInstance(vn);
        return vndongFormat.format(this.price).substring(2);
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
}
