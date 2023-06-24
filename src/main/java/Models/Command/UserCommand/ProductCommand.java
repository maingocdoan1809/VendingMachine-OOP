/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Command.UserCommand;

import Models.Command.Command;
import Models.Product;

/**
 *
 * @author Admin
 */
public abstract class ProductCommand implements Command{
    protected Product product;

    public ProductCommand(Product product) {
        this.product = product;
    }
    
}
