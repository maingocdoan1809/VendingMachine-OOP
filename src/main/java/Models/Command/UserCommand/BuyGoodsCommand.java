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
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class BuyGoodsCommand extends ProductCommand {

    private User user;

    public BuyGoodsCommand(User user, Product product) {
        super(product);
        this.user = user;
    }

    @Override
    public void execute() {
        String price = Utils.Utility.toMoney(this.product.getPrice());
        int isConfirmed = JOptionPane.showConfirmDialog(null,
                "Bạn có muốn thanh toán "
                + price + " cho sản phẩm này?");
        if (isConfirmed == ConfirmationCallback.YES) {

            float newAmount = this.user.getCurrentMoney() - this.product.getPrice();

            if (newAmount < 0) {
                JOptionPane.showMessageDialog(null,
                        "Tài khoản của bạn hiện không đủ " + price + " vui lòng nạp thêm tiền.");
            } else {
                BankAccount userAccount = this.user.getAccount();
                JOptionPane.showMessageDialog(null, "Mua thành công, xin mời nhận hàng.");
                this.user.setCurrentMoney(newAmount);
                float currBalance = userAccount.getBankBalance();

                try {
                    // update:
                    BankAccountRepository bankRepos = new BankAccountRepository();
                    ProductRepository productRepos = new ProductRepository();
                    HistoryRepository historyRepos = new HistoryRepository();
                    
                    bankRepos.update(
                        new BankAccount(userAccount.getBankAccountNumber())
                        .setBankBalance(currBalance - this.product.getPrice())
                    );
                    productRepos.update(
                        new Product(this.product.getId()).
                        setRemainNums(this.product.getRemainNums() - 1)
                    );
                    // save history:
                    historyRepos.insert(new History(this.user.getUsername(), 
                            this.product.getId(), this.product.getPrice()));
                    // make changes to local data:
                    userAccount.setBankBalance(currBalance - this.product.getPrice());
                    this.product.setRemainNums(this.product.getRemainNums() - 1);
                    // notify
                    this.product.notifyObservers();
                    this.user.notifyObservers();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra, xin thử lại.");
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "No");
        }
    }

}
