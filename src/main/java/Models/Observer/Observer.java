/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Observer;

/**
 *
 * @author Admin
 */
/**
 * Interface cho những object lắng nghe sự kiện nào đó từ một subject. 
 */
public interface Observer {
    /**
     * Mỗi Observer sẽ có một hàm update, nhận vào một subject làm tham số. Subject này chính là những 'subcriber' mà observer này đã đăng kí trước đó.
     */
    public void update(Subject subject);
}
