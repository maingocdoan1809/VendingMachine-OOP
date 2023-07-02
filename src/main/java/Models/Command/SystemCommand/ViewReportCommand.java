/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Command.SystemCommand;

import Views.Report;

/**
 *
 * @author MAI NGOC DOAN
 */
public class ViewReportCommand extends SystemCommand{

    @Override
    public void execute() {
        new Report().setVisible(true);
    }
    
}
