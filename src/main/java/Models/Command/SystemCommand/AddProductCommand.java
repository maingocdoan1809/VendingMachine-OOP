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

 // Xử lý việc thêm sản phẩm vào trong database khi người dùng yêu cầu
public class AddProductCommand extends SystemCommand{
    private Observer caller;
    // 

    // nhận vào một Observer caller. Object này ở đây sẽ là Screen frame, cái mà gọi command này
    public  AddProductCommand(Observer caller) {
        this.caller = caller;
    }
    @Override
    public void execute() {
        JFrame frame = new JFrame();
        
        frame.setLayout(new CardLayout());
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Add product là một subject. 
        var panel = new AddProduct();
        // thực viên việc đăng kí sự kiện cho caller, khi nào AddProduct thực hiện thêm sản phẩm thành công,
        // nó sẽ báo cho caller biết rồi update thông tin lên màn hình của nó.
        panel.register(caller);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
