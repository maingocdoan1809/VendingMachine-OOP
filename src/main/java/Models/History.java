/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class History {
    private String username;
    private int productId;
    private float price;

    public History(String username, int productId, float price) {
        this.username = username;
        this.productId = productId;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public History setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public History setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public History setPrice(float price) {
        this.price = price;
        return this;
    }
    
}
