/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Command.SystemCommand;

import Models.Observer.Observer;
import Views.AddProduct;
import java.awt.CardLayout;
import javax.swing.JFrame;

/**
 *
 * @author MAI NGOC DOAN
 */
public class AddProductCommand extends SystemCommand{
    private Observer caller;
    public  AddProductCommand(Observer caller) {
        this.caller = caller;
    }
    @Override
    public void execute() {
        JFrame frame = new JFrame();
        
        frame.setLayout(new CardLayout());
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        var panel = new AddProduct();
        panel.register(caller);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
