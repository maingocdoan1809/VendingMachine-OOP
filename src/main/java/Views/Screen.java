/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import DataSource.Repository.ProductRepository;
import Models.Command.SystemCommand.AddProductCommand;
import Models.Command.SystemCommand.DecorateButtonCommand;
import Models.Command.SystemCommand.RechargeCommand;
import Models.Command.SystemCommand.ViewReportCommand;
import Models.Command.UserCommand.BuyGoodsCommand;
import Models.Observer.Observer;
import Models.Observer.Subject;
import Models.Product;
import Models.User;
import Utils.Utility;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import Utils.PaymentMethod;
/**
 *
 * @author Admin
 */
/**
 * Đây là giao diện chính của app này.
 */


public class Screen extends javax.swing.JPanel implements Observer {

    /**
     * Creates new form Screen
     */
    private HashMap<String, JButton> buttons;
    private User user;
    private JButton selectedButton = null;
    private PaymentMethod paymentMethod = null;
    private HashMap<Integer, Integer> cash = new HashMap<>();
    // implement singleton pattern;
    private static Screen _instance = null;

    public static Screen getFirstCurrentInstance() {
        if (_instance == null) {
            _instance = new Screen();
        }
        return _instance;
    }
    public HashMap<Integer, Integer> getUserInputCash() {
        return this.cash;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod p) {
        this.paymentMethod = p;
    }

    public Screen() {
        initComponents();
        initButtons();

        this.txtPName.setText("No selection");
        this.txtPNum.setText("No selection");
        this.txtPPrice.setText("No selection");

        showUserInfo(false);
        this.btnAddProduct.setVisible(false);
        this.btnViewReport.setVisible(false);

        ProductRepository p = new ProductRepository();

        this.jbestsellingtxt.setText("Best selling in month: " + p.getTopSelling().getName());

    }

    private void initButtons() {
        buttons = new HashMap<>();
        // mỗi màn hình sẽ có sức chứa 25 slot. Mỗi một button là một khay chứa nước.
        /**
         * Mỗi một sản phẩm sẽ tương ứng với một button Một HashMap dùng để lưu
         * các nút này kèm theo giá trị id của sản phẩm trong bảng product.
         */
        /**
         * Nếu this.btnSlot1 muốn cấu hình cho nó bán mặt hàng '123' thì ta sẽ
         * lưu như sau: buttons.put("123", this.btnSlot1);
         */
        buttons.put("1", this.btnSlot1);
        buttons.put("2", this.btnSlot2);
        buttons.put("3", this.btnSlot3);
        buttons.put("4", this.btnSlot4);
        buttons.put("5", this.btnSlot5);
        buttons.put("6", this.btnSlot6);
        buttons.put("7", this.btnSlot7);
        buttons.put("8", this.btnSlot8);
        buttons.put("9", this.btnSlot9);
        buttons.put("10", this.btnSlot10);
        buttons.put("11", this.btnSlot11);
        buttons.put("12", this.btnSlot12);
        buttons.put("13", this.btnSlot13);
        buttons.put("14", this.btnSlot14);
        buttons.put("15", this.btnSlot15);
        buttons.put("16", this.btnSlot16);
        buttons.put("17", this.btnSlot17);
        buttons.put("18", this.btnSlot18);
        buttons.put("19", this.btnSlot19);
        buttons.put("20", this.btnSlot20);
        buttons.put("21", this.btnSlot21);
        buttons.put("22", this.btnSlot22);
        buttons.put("23", this.btnSlot23);
        buttons.put("24", this.btnSlot24);
        buttons.put("25", this.btnSlot25);
       
        
        
        ProductRepository pRespo = new ProductRepository();

        for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
            // lấy sản phẩm ra từ repository
            Product p = pRespo.get(entry.getKey());
            // gán màu sắc và thêm sự kiện onclick cho nó
            populateButtonWithProduct(entry.getValue(), p);
        }
    }

    /**
     * Hàm này dùng để update lại số lượng của các slot trong Screen
     */
    private void updateProduct(Product p) {
        String input = JOptionPane.showInputDialog(null, "Input new Quantity");
        int inputInt = 0;
        try {
            if (input.equals("")) {
                return;
            }
            inputInt = Integer.parseInt(input);

            ProductRepository pRepos = new ProductRepository();
            p.setRemainNums(inputInt);
            if (pRepos.update(p)) {
                JOptionPane.showMessageDialog(null, "Updated");
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }

    /**
     * Tô màu cho các nút bấm, dễ phân biệt:))
     */
    final private void populateButtonWithProduct(JButton button, Product p) {
        if (p != null) {
            button.setText(p.getName());
            if (p.getRemainNums() == 0) {
                new DecorateButtonCommand(
                        button,
                        DecorateButtonCommand.ButtonState.SOLDOUT)
                        .execute();
                selectedButton = null;
            } else {
                new DecorateButtonCommand(
                        button,
                        DecorateButtonCommand.ButtonState.READY)
                        .execute();
            }
            p.register(this);
        } else {
            new DecorateButtonCommand(
                    button,
                    DecorateButtonCommand.ButtonState.EMPTY)
                    .execute();
        }
        // nếu có sự kiện nào khác, hãy xóa nó
        for (var i : button.getMouseListeners()) {
            button.removeMouseListener(i);
        }

        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
    
                if (p != null && p.getRemainNums() > 0) {
                    if (selectedButton != null) {
                        new DecorateButtonCommand(
                                selectedButton,
                                DecorateButtonCommand.ButtonState.READY)
                                .execute();
                    }
                    selectedButton = button;

                    new DecorateButtonCommand(
                            selectedButton,
                            DecorateButtonCommand.ButtonState.SELECTED)
                            .execute();
                    txtPName.setText(p.getName());
                    txtPNum.setText(p.getRemainNums() + "");
                    txtPPrice.setText(p.getPriceFormat());
                    if (evt.getClickCount() == 2) {
                        handleUserBuyingProductEvent(p);
                    }
                } else {
                    if (user != null && user.isAdmin()) {
                        updateProduct(p);
                        populateButtonWithProduct(button, p);
                    } else {
                        JOptionPane.showMessageDialog(button, "Not available");
                    }
                }

            }
        });

    }

    // Kiểm tra sự xác thực khi mua hàng.
    private void handleUserBuyingProductEvent(Product product) {
        if (this.paymentMethod == PaymentMethod.CREDIT) {
            // nếu không có user nào thì ta phải show form đăng nhập
            if (this.user == null) {
                new RechargeCommand(this).execute();
            } else {
                // nếu đã đăng nhập rồi thì ok
                new BuyGoodsCommand(this.user, product).execute();
            }
        } else {
            new BuyGoodsCommand(null, product).execute();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnRecharge = new javax.swing.JButton();
        jLabelAname = new javax.swing.JLabel();
        jLabelABalance = new javax.swing.JLabel();
        txtUName = new javax.swing.JLabel();
        txtAvalibility = new javax.swing.JLabel();
        btnDone = new javax.swing.JButton();
        btnAddMore = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        btnViewReport = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        txtPName = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        txtPNum = new javax.swing.JLabel();
        txtPPrice = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSlot1 = new javax.swing.JButton();
        btnSlot2 = new javax.swing.JButton();
        btnSlot3 = new javax.swing.JButton();
        btnSlot4 = new javax.swing.JButton();
        btnSlot5 = new javax.swing.JButton();
        btnSlot6 = new javax.swing.JButton();
        btnSlot7 = new javax.swing.JButton();
        btnSlot8 = new javax.swing.JButton();
        btnSlot9 = new javax.swing.JButton();
        btnSlot10 = new javax.swing.JButton();
        btnSlot11 = new javax.swing.JButton();
        btnSlot12 = new javax.swing.JButton();
        btnSlot13 = new javax.swing.JButton();
        btnSlot14 = new javax.swing.JButton();
        btnSlot15 = new javax.swing.JButton();
        btnSlot16 = new javax.swing.JButton();
        btnSlot17 = new javax.swing.JButton();
        btnSlot18 = new javax.swing.JButton();
        btnSlot19 = new javax.swing.JButton();
        btnSlot20 = new javax.swing.JButton();
        btnSlot21 = new javax.swing.JButton();
        btnSlot22 = new javax.swing.JButton();
        btnSlot23 = new javax.swing.JButton();
        btnSlot24 = new javax.swing.JButton();
        btnSlot25 = new javax.swing.JButton();
        jbestsellingtxt = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 349));

        btnRecharge.setBackground(new java.awt.Color(153, 153, 255));
        btnRecharge.setText("Nạp tiền");
        btnRecharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechargeActionPerformed(evt);
            }
        });

        jLabelAname.setText("Tài khoản");

        jLabelABalance.setText("Số dư còn lại");

        txtUName.setBackground(new java.awt.Color(51, 255, 51));
        txtUName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtUName.setForeground(new java.awt.Color(0, 51, 255));

        txtAvalibility.setBackground(new java.awt.Color(51, 255, 51));
        txtAvalibility.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtAvalibility.setForeground(new java.awt.Color(0, 51, 255));

        btnDone.setBackground(new java.awt.Color(153, 153, 255));
        btnDone.setText("Rút tiền và hoàn tất");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        btnAddMore.setBackground(new java.awt.Color(0, 153, 255));
        btnAddMore.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMore.setText("Nạp thêm");
        btnAddMore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMoreActionPerformed(evt);
            }
        });

        btnAddProduct.setBackground(new java.awt.Color(153, 153, 255));
        btnAddProduct.setText("Thêm sản phẩm");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        btnViewReport.setBackground(new java.awt.Color(153, 153, 255));
        btnViewReport.setText("Xem thống kê tháng");
        btnViewReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRecharge, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddMore, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUName, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelABalance, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAname, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAvalibility, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jLabelAname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUName)
                .addGap(18, 18, 18)
                .addComponent(jLabelABalance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAvalibility)
                .addGap(32, 32, 32)
                .addComponent(btnAddMore)
                .addGap(7, 7, 7)
                .addComponent(btnDone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(btnViewReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddProduct)
                .addGap(18, 18, 18)
                .addComponent(btnRecharge)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setBackground(new java.awt.Color(253, 249, 249));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Bạn đang chọn");

        txtPName.setBackground(new java.awt.Color(51, 255, 51));
        txtPName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPName.setForeground(new java.awt.Color(0, 51, 255));
        txtPName.setText("Coca Cola");

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Giá tiền");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Số lượng còn lại");

        txtPNum.setBackground(new java.awt.Color(51, 255, 51));
        txtPNum.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPNum.setForeground(new java.awt.Color(0, 51, 255));
        txtPNum.setText("20");

        txtPPrice.setBackground(new java.awt.Color(51, 255, 51));
        txtPPrice.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPPrice.setForeground(new java.awt.Color(0, 51, 255));
        txtPPrice.setText("10,000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPName, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPNum, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPName))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPPrice))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPNum))
                .addContainerGap())
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel4.setLayout(new java.awt.GridLayout(5, 5));

        btnSlot1.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot1);

        btnSlot2.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot2);

        btnSlot3.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot3);

        btnSlot4.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot4);

        btnSlot5.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot5);

        btnSlot6.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot6);

        btnSlot7.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot7);

        btnSlot8.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot8);

        btnSlot9.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot9);

        btnSlot10.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot10);

        btnSlot11.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot11);

        btnSlot12.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot12);

        btnSlot13.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot13);

        btnSlot14.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot14);

        btnSlot15.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot15);

        btnSlot16.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot16);

        btnSlot17.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot17);

        btnSlot18.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot18);

        btnSlot19.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot19);

        btnSlot20.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot20);

        btnSlot21.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot21);

        btnSlot22.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot22);

        btnSlot23.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot23);

        btnSlot24.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot24);

        btnSlot25.setBackground(new java.awt.Color(204, 204, 255));
        btnSlot25.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.add(btnSlot25);

        jbestsellingtxt.setBackground(new java.awt.Color(0, 0, 0));
        jbestsellingtxt.setFont(new java.awt.Font("Agent Orange", 2, 14)); // NOI18N
        jbestsellingtxt.setForeground(new java.awt.Color(51, 0, 102));
        jbestsellingtxt.setText("Best selling: ");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Agent Orange", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 102));
        jLabel7.setText("Live every drop");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbestsellingtxt))
                        .addGap(0, 1756, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jLabel7)
                    .addContainerGap(1943, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jbestsellingtxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addGap(42, 42, 42))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(jLabel7)
                    .addContainerGap(418, Short.MAX_VALUE)))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRechargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechargeActionPerformed

        var chooseMethod = new ChoosePaymentMethod(this);
        chooseMethod.setVisible(true);
    }//GEN-LAST:event_btnRechargeActionPerformed
    public void updateCashAmount(int cashValue) {
        
        System.out.println(this.cash);
        
        String currCash = this.txtAvalibility.getText();
        this.jLabelABalance.setVisible(true);
        this.txtAvalibility.setVisible(true);
        this.btnAddMore.setVisible(true);
        this.btnDone.setVisible(true);
        if (currCash.equals("")) {
            this.txtAvalibility.setText(cashValue + "");
        } else {
            int currCashInt = Integer.parseInt(currCash);
            this.txtAvalibility.setText((cashValue + currCashInt) + "");
        }
    }
    public float getCurrentUserAmount() {
        if (this.txtAvalibility.getText().trim().equals("")) {
            return 0;
        }
        return Float.parseFloat(this.txtAvalibility.getText());
    }
    private void showUserInfo(boolean state) {

        this.txtAvalibility.setVisible(state);
        this.txtUName.setVisible(state);
        this.jLabelABalance.setVisible(state);
        this.jLabelAname.setVisible(state);
        this.btnDone.setVisible(state);
        this.btnAddMore.setVisible(state);
    }

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed

        this.user = null;
        showUserInfo(false);
        // turn of admin buttons:
        this.btnAddProduct.setVisible(false);
        this.btnViewReport.setVisible(false);
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnAddMoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMoreActionPerformed
        if (this.paymentMethod == PaymentMethod.CREDIT) {
            String input = JOptionPane.showInputDialog("Nhập số tiền bạn muốn nạp thêm", 0);

            float userInputMoney = (float) 0.0;
            try {
                userInputMoney = Float.parseFloat(input);
                this.user.setCurrentMoney(this.user.getCurrentMoney() + userInputMoney);
                this.user.notifyObservers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Bạn phải nhập một số, vui lòng thử lại");
            }
        } else {
            this.btnRechargeActionPerformed(null);
        }
    }//GEN-LAST:event_btnAddMoreActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        new AddProductCommand(this).execute();
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnViewReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportActionPerformed
        new ViewReportCommand().execute();
    }//GEN-LAST:event_btnViewReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMore;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnRecharge;
    private javax.swing.JButton btnSlot1;
    private javax.swing.JButton btnSlot10;
    private javax.swing.JButton btnSlot11;
    private javax.swing.JButton btnSlot12;
    private javax.swing.JButton btnSlot13;
    private javax.swing.JButton btnSlot14;
    private javax.swing.JButton btnSlot15;
    private javax.swing.JButton btnSlot16;
    private javax.swing.JButton btnSlot17;
    private javax.swing.JButton btnSlot18;
    private javax.swing.JButton btnSlot19;
    private javax.swing.JButton btnSlot2;
    private javax.swing.JButton btnSlot20;
    private javax.swing.JButton btnSlot21;
    private javax.swing.JButton btnSlot22;
    private javax.swing.JButton btnSlot23;
    private javax.swing.JButton btnSlot24;
    private javax.swing.JButton btnSlot25;
    private javax.swing.JButton btnSlot3;
    private javax.swing.JButton btnSlot4;
    private javax.swing.JButton btnSlot5;
    private javax.swing.JButton btnSlot6;
    private javax.swing.JButton btnSlot7;
    private javax.swing.JButton btnSlot8;
    private javax.swing.JButton btnSlot9;
    private javax.swing.JButton btnViewReport;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelABalance;
    private javax.swing.JLabel jLabelAname;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel jbestsellingtxt;
    private javax.swing.JLabel txtAvalibility;
    private javax.swing.JLabel txtPName;
    private javax.swing.JLabel txtPNum;
    private javax.swing.JLabel txtPPrice;
    private javax.swing.JLabel txtUName;
    // End of variables declaration//GEN-END:variables

    public User getUser() {
        return this.user;
    }

    @Override
    public void update(Subject subject) {

        if (subject instanceof Recharge) {
            Recharge l = (Recharge) subject;
            User user = l.getLoggedInUser();
            this.user = user;
            this.user.register(this);
            this.txtUName.setText(user.getUsername());
            this.txtAvalibility.setText(Utility.toMoney(user.getCurrentMoney()));
            this.btnAddProduct.setVisible(user.isAdmin());
            this.btnViewReport.setVisible(user.isAdmin());
            showUserInfo(true);
        } else if (subject == this.user) {
            this.user.register(this);
            this.txtUName.setText(user.getUsername());
            this.txtAvalibility.setText(Utility.toMoney(this.user.getCurrentMoney()));
            showUserInfo(true);
        } else if (subject instanceof Product) {
            Product p = (Product) subject;
            this.txtPNum.setText(p.getRemainNums() + "");
            this.txtPPrice.setText(Utility.toMoney(p.getPrice()));
            this.txtPName.setText(p.getName());

            JButton btnWithP = this.buttons.get(p.getId() + "");
            populateButtonWithProduct(btnWithP, p);

        } else if (subject instanceof AddProduct) {
            ProductRepository pRespo = new ProductRepository();

            Product p = ((AddProduct) subject).getNewProduct();
            var button = buttons.get(p.getId() + "");
            populateButtonWithProduct(button, p);

        }

    }
}
