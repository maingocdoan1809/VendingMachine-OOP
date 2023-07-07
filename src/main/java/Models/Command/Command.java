/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Command;

/**
 *
 * @author Admin
 */

 /**
  * Base class cho các command.
  */    
public interface Command {
    // mỗi command sẽ có hàm execute, thực thi các câu lệnh tương ứng với command đó
    void execute();
}
