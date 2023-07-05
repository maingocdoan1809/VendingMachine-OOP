/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Observer;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
/**
 * Interface cho các subject (hay publisher), đây là class sẽ có chức năng cho phép một observer 'lắng nghe' một sự kiện nào đó của subject này. Cứ mỗi khi Subject thay đổi trạng thái thì nó sẽ thông báo đến cho các Observer đã đăng kí sự kiện
 */
public interface Subject {
    // subcribe hay đăng kí cho một observer 'lắng nghe' sự thay đổi của Subject
    public void register(Observer observer);
    // remove hay xóa một observer ra khỏi danh sách đăng kí
    public void removeObserver(Observer observer);
    // thông báo cho observers biết về sự thay đổi của Subject
    public abstract void notifyObservers();
}
