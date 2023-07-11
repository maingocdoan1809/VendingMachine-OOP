/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Command.UserCommand;

import DataSource.Repository.BankAccountRepository;
import DataSource.Repository.HistoryRepository;
import DataSource.Repository.ProductRepository;
import Models.BankAccount;
import Models.History;
import Models.Product;
import Models.User;
import Views.Screen;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JOptionPane;
import Utils.PaymentMethod;
import Views.ChoosePaymentMethod;
import Views.ListMoney;

/**
 *
 * @author Admin
 */
/**
 * Class command cho việc người dùng mua hàng
 */
public class BuyGoodsCommand extends ProductCommand {

    private User user;

    // cần lưu xem ai mua, và mua cái gì
    public BuyGoodsCommand(User user, Product product) {
        super(product);
        this.user = user;
    }

    private void handlePaymentWitdCreditCard() {

        String price = Utils.Utility.toMoney(this.product.getPrice());
        // hỏi người dùng xem họ có muốn thanh toán sản phẩm này không
        int isConfirmed = JOptionPane.showConfirmDialog(null,
                "Bạn có muốn thanh toán "
                + price + " cho sản phẩm này?");
        // ok, mua
        if (isConfirmed == ConfirmationCallback.YES) {
            // tính toán xem tài khoản có đủ tiền không
            float newAmount = this.user.getCurrentMoney() - this.product.getPrice();

            if (newAmount < 0) {
                // nếu không đủ thì thông báo
                JOptionPane.showMessageDialog(null,
                        "Tài khoản của bạn hiện không đủ " + price + " vui lòng nạp thêm tiền.");
            } else {
                // nếu đủ tiền, lấy thông tin trong tài khoản ngân hàng của ông user đó
                BankAccount userAccount = this.user.getAccount();
                JOptionPane.showMessageDialog(null, "Mua thành công, xin mời nhận hàng.");
                // trừ tiền
                this.user.setCurrentMoney(newAmount);

                float currBalance = userAccount.getBankBalance();

                try {
                    // update lại database sau khi trừ tiền
                    BankAccountRepository bankRepos = new BankAccountRepository();
                    ProductRepository productRepos = new ProductRepository();
                    HistoryRepository historyRepos = new HistoryRepository();

                    // update account, giảm tiền
                    bankRepos.update(
                            new BankAccount(userAccount.getBankAccountNumber())
                                    .setBankBalance(currBalance - this.product.getPrice())
                    );
                    // giảm số lượng sản phẩm
                    productRepos.update(
                            new Product(this.product.getId()).
                                    setRemainNums(this.product.getRemainNums() - 1)
                    );
                    // save history:
                    historyRepos.insert(new History(this.user.getUsername(),
                            this.product.getId(), this.product.getPrice()));

                    // giảm lượng tiền đặt cọc trên screen.
                    // lượng tiền này thực chất là lượng tiền đặt cọc cho mỗi lần mua hàng, nếu sau khi nạp mà không mua gì thì tài khoản sẽ không bị trừ tiền
                    userAccount.setBankBalance(currBalance - this.product.getPrice());
                    this.product.setRemainNums(this.product.getRemainNums() - 1);
                    // thông báo cho screen biết người dùng đã mua hàng thành công.
                    this.product.notifyObservers();
                    this.user.notifyObservers();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra, xin thử lại.");
                }

            }

        }
    }

    private void handlePaymentWithCash() {

        String price = Utils.Utility.toMoney(this.product.getPrice());
        // hỏi người dùng xem họ có muốn thanh toán sản phẩm này không
        int isConfirmed = JOptionPane.showConfirmDialog(null,
                "Bạn có muốn thanh toán "
                + price + " cho sản phẩm này?");
        // ok, mua
        if (isConfirmed == ConfirmationCallback.YES) {
            // tính toán xem tài khoản có đủ tiền không
            float newAmount = Screen.getFirstCurrentInstance().getCurrentUserAmount();
            if (newAmount < 0) {
                // nếu không đủ thì thông báo
                JOptionPane.showMessageDialog(null,
                        "Tài khoản của bạn hiện không đủ " + price + " vui lòng nạp thêm tiền.");
            } else {
                // update tien trong ket.

            }

        }
    }

    @Override
    public void execute() {
        var method = Screen.getFirstCurrentInstance().getPaymentMethod();
        if (method == PaymentMethod.CREDIT) {
            handlePaymentWitdCreditCard();
        } else if (method == PaymentMethod.CASH) {
            handlePaymentWithCash();
        } else {

            float currentAmount = Screen.getFirstCurrentInstance().getCurrentUserAmount();

            if (currentAmount == 0) {
                JOptionPane.showMessageDialog(null, "Xin mời đưa tiền vào máy...");
                new ListMoney().setVisible(true);
            }

        }
    }

}
