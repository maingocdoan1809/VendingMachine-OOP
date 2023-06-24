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
public class DecorateButtonCommand extends SystemCommand {

    private JButton button;

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
