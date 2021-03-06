import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is responsible for displaying the account data and
 * account budget data from <code>BudgetTrackerModel</code>. Also
 * holds a clickable label to start the sign in sequence of the
 * program.
 * 
 * @author  Kyle Bye
 * @see BudgetTrackerModel
 * @see MouseListener
 * @see Updatable
 * @see Runnable
 */
@SuppressWarnings("serial")
public class AccountPanel extends JPanel implements MouseListener, Updatable, Runnable {

    ///
    /// Properties, Getters, Setters
    ///

    private BudgetTrackerModel model;

    public BudgetTrackerModel getModel() {
        if (model == null ) {
            System.err.println(
                "getModel() called when model is null in AccountPanel"
                );
        }
        return model;
    }

    public void setModel(BudgetTrackerModel modelIn) {
        if (modelIn != null) model = modelIn;
        else {
            System.err.println(
                "null modelIn @ setModel(BudgetTrackerModel) in AccountPanel"
                );
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
            accountBudgetChanged();
        }
        else {
            System.err.println("null accountBudgetLabelIn @ setAccountBudgetLabel(JLabel) in AccountPanel");
        }
    }

    ///
    /// Functions
    ///

    /**
     * Calls <code>update()</code>
     * 
     * @see AccountPanel#update()
     */
    public void run() {
        update();
    }

    /**
     * Calls <code>accountChange()</code> and
     * <code>accountBudgetChanged</code>.
     * 
     * @see AccountPanel#accountChanged()
     * @see AccountPanel#accountBudgetChanged()
     */
    public void update() {
        accountChanged();
        accountBudgetChanged();        
    }

    ///
    /// Events
    ///

    /**
     * Updates the accountLabel text if accountNameLabel and
     * selectedAccount of model is not null.
     * <br><br>
     * Prints warning message if accountNameLabel is null
     * 
     * @see BudgetTrackerModel
     */
    public void accountChanged() {
        //  Null Check accountNameLabel
        if (accountNameLabel == null) {
            System.err.println("accountChanged() called when accountNameLabel is null in AccountPanel");
            return;
        }

        String accountName;

        Account selectedAccount = model.getSelectedAccount();

        if (selectedAccount == null) {
            System.err.println("accountChanged() called when account is null in AccountPanel");
            accountName = "Click here to Sign In";
        }
        else accountName = selectedAccount.toNameString();

        accountNameLabel.setText(accountName);
        repaint();
    }

    /**
     * Updates the accountBudgetLabel text if accountBudgetLabel and
     * accountBudget of model is not null.
     * <br><br>
     * Prints warning message if accountBudget is null
     * 
     * @see BudgetTrackerModel
     */
    public void accountBudgetChanged() {
        //  Null Check accountNameLabel
        if (accountBudgetLabel == null) {
            System.err.println("accountBudgetChanged() called when accountBudgetLabel is null in AccountPanel");
            return;
        }

        String budget;
        AccountBudget accountBudget = model.getAccountBudget();

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
        model.openSignIn();
    }
    public void mouseReleased(MouseEvent me) {}

    ///
    /// Constructors
    ///

    public AccountPanel(BudgetTrackerModel modelIn) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        setModel(modelIn);

        // Label Panel
        JPanel labelPanel = new JPanel();
        labelPanel.add(new JPanel());
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        //  accountNameLabel
        setAccountNameLabel(new JLabel("Click here to sign in."));
        accountNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        accountNameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        accountNameLabel.addMouseListener(this);
        accountNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(accountNameLabel);

        //  accountBudgetLabel
        setAccountBudgetLabel(new JLabel());
        accountBudgetLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 90));
        accountBudgetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.add(accountBudgetLabel);

        //  Label Panel Finishes
        labelPanel.add(new JPanel());
        add(labelPanel);

        setVisible(true);

        //  Since AccountPanel is updatable because of its labels,
        //  it will be added to the update list.
        model.getUpdatables().add(this);
    }

}