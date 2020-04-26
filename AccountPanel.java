import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class AccountPanel extends JPanel implements MouseListener {

    ///
    /// Properties, Getters, Setters
    ///

    private Account account;

    public Account getAccount() {
        if (account == null) {
            System.err.println("getAccount() called when account is null in AccountPanel");
        }
        return account;
    }

    public void setAccount(Account accountIn) {
        if (accountIn != null) {
            account = accountIn;
            accountChanged();
        }
        else {
            System.err.println("null accountIn @ setAccount(Account) in AccountPanel");
        }
    }

    private AccountBudget accountBudget;

    public AccountBudget getAccountBudget() {
        if (accountBudget == null) {
            System.err.println("getAccountBudget() called when accountBudget is null in AccountPanel");
        }
        return accountBudget;
    }

    public void setAccountBudget(AccountBudget accountBudgetIn) {
        if (accountBudgetIn != null) {
            accountBudget = accountBudgetIn;
            accountBudgetChanged();
        }
        else {
            System.err.println("null accountBudgetIn @ setAccountBudget(AccountBudget) in AccountPanel");
        }
    }

    private JLabel accountNameLabel;

    public JLabel getAccountNameLabel() {
        if (accountNameLabel == null) {
            System.err.println("getAccountNameLabel() called when accountNameLabel is null in AccountPanel");
        }
        return accountNameLabel;
    }

    public void setAccountNameLabel(JLabel accountNameLabelIn) {
        if (accountNameLabelIn != null) {
            accountNameLabel = accountNameLabelIn;
        }
        else {
            System.err.println("null accountNameLabelIn @ setAccountNameLabel(JLabel) in AccountPanel");
        }
    }

    private JLabel accountBudgetLabel;
    
    public JLabel getAccountBudgetLabel() {
        if (accountBudgetLabel == null) {
            System.err.println("getAccountBudgetLabel() called when accountBudgetLabel is null in AccountPanel");
        }
        return accountBudgetLabel;
    }

    public void setAccountBudgetLabel(JLabel accountBudgetLabelIn) {
        if (accountBudgetLabelIn != null) {
            accountBudgetLabel = accountBudgetLabelIn;
        }
        else {
            System.err.println("null accountBudgetLabelIn @ setAccountBudgetLabel(JLabel) in AccountPanel");
        }
    }

    ///
    /// Events
    ///

    public void accountChanged() {
        //  Null Check accountNameLabel
        if (accountNameLabel == null) {
            System.err.println("accountChanged() called when accountNameLabel is null in AccountPanel");
            return;
        }

        String accountName;

        if (account == null) {
            System.err.println("accountChanged() called when account is null in AccountPanel");
            accountName = "Click here to Sign In";
        }
        else accountName = account.toNameString();

        accountNameLabel.setText(accountName);
        repaint();
    }

    public void accountBudgetChanged() {
        //  Null Check accountNameLabel
        if (accountBudgetLabel == null) {
            System.err.println("accountBudgetChanged() called when accountBudgetLabel is null in AccountPanel");
            return;
        }

        String budget;
        if (accountBudget == null) {
            System.err.println("accountChanged() called when account is null in AccountPanel");
            budget = new String();
        }
        else budget = accountBudget.toBalanceString();

        accountBudgetLabel.setText(budget);
        repaint();
    }

    public void mouseClicked(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {
        System.out.println(
            "@Notification Sign in sequence activated by clicking AccountPanel.accountNameLabel"
            );
    }
    public void mouseReleased(MouseEvent me) {}

    ///
    /// Constructors
    ///

    public AccountPanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        //  accountNameLabel
        setAccountNameLabel(new JLabel("Click here to sign in."));
        accountNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        accountNameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        accountNameLabel.addMouseListener(this);
        add(accountNameLabel);

        //  accountBudgetLabel
        setAccountBudgetLabel(new JLabel());
        accountBudgetLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        add(accountBudgetLabel);

        setAccount(null);
        setAccountBudget(null);
        setVisible(true);
    }

}