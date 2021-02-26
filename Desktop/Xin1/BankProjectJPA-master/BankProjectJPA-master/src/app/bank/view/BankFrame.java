package app.bank.view;

import app.bank.model.BankAccount;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BankFrame extends JFrame {

    private JLabel fromLabel = new JLabel("From bank account");
    private JLabel toLabel = new JLabel("To bank account");
    private JLabel amountLabel = new JLabel("Amount");
    private JComboBox<BankAccount> fromBankAccountComboBox = new JComboBox<>();
    private JComboBox<BankAccount> toBankAccountComboBox = new JComboBox<>();
    private JTextField amountTextField = new JTextField(5);
    private JButton transferButton = new JButton("Transfer");

    public BankFrame() {
        super("Bank account transfer");
        setSize(450, 260);
        setLayout(new GridLayout(4, 1));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        panel1.add(fromLabel);
        for (BankAccount bankAccount : BankAccount.loadAll()) {
            fromBankAccountComboBox.addItem(bankAccount);
        }
        panel1.add(fromBankAccountComboBox);

        panel2.add(toLabel);
        for (BankAccount bankAccount : BankAccount.loadAll()) {
            toBankAccountComboBox.addItem(bankAccount);
        }
        panel2.add(toBankAccountComboBox);
        panel3.add(amountLabel);
        panel3.add(amountTextField);

        transferButton.addActionListener(this::executeTransfer);
        panel4.add(transferButton);
    }

    private void executeTransfer(ActionEvent e) {
        BankAccount fromAccount = (BankAccount) fromBankAccountComboBox.getSelectedItem();
        BankAccount toAccount = (BankAccount) toBankAccountComboBox.getSelectedItem();
        Double amount = Double.parseDouble(amountTextField.getText());
        BankAccount.transferMoney(fromAccount, toAccount, amount);
        fromBankAccountComboBox.removeAllItems();
        toBankAccountComboBox.removeAllItems();
        for (BankAccount bankAccount : BankAccount.loadAll()) {
            fromBankAccountComboBox.addItem(bankAccount);
        }
        for (BankAccount bankAccount : BankAccount.loadAll()) {
            toBankAccountComboBox.addItem(bankAccount);
        }
    }

    public void showFrame() {
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        //BankAccount.loadBankAccounts().forEach(System.out::println);
        BankFrame bankFrame = new BankFrame();
        SwingUtilities.invokeLater(bankFrame::showFrame);
    }
}
