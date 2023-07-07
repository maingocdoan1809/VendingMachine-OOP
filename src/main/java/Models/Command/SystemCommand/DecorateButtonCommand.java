/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Command.SystemCommand;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author MAI NGOC DOAN
 */

 // Class này dùng để tô màu cho các nút chứa sản phẩm trên Screen.
public class DecorateButtonCommand extends SystemCommand {

    private JButton button;

    // Một enum lưu trạng thái của mỗi ô chứa nước như:
    // READY: ô này có nước và sẵn sàng để bán
    // EMPTY: ô này chưa được cài đặt nước.
    // SOLDOUT: ô này được cài đặt thông tin nước, nhưng tạm thời hết hàng
    // SELECTED: ô này đang được chọn.
    public static enum ButtonState {
        READY, EMPTY, SOLDOUT, SELECTED
    };

    private ButtonState state;

    public DecorateButtonCommand(JButton button, ButtonState state) {
        this.button = button;
        this.state = state;
    }

    @Override
    public void execute() {

        // với mỗi một state thì ta sẽ tô màu khác nhau cho button đó
        switch (state) {
            case EMPTY -> {
                this.button.setText("Empty");
                this.button.setBackground(new Color(255, 33, 113));
            }
            case READY -> {
                this.button.setBackground(new Color(204, 204, 255));
            }
            case SOLDOUT -> {
                this.button.setText( this.button.getText()+"(Sold out)");
                this.button.setBackground(new Color(255, 133, 81));
            }
            case SELECTED -> {
                this.button.setBackground(new Color(122, 168, 116));
            }
            default ->
                throw new AssertionError();
        }
    }

}
