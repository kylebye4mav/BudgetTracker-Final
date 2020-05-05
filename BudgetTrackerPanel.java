import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible for handling the button
 * and label components.
 * 
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class BudgetTrackerPanel extends JPanel {

    ///
    /// Properties, Getters, Setters
    ///

    private BudgetTrackerModel model;

    public BudgetTrackerModel getModel() {
        if (model == null ) {
            System.err.println(
                "getModel() called when model is null in BudgetTrackerPanel"
                );
        }
        return model;
    }

    public void setModel(BudgetTrackerModel modelIn) {
        if (modelIn != null) model = modelIn;
        else {
            System.err.println(
                "null modelIn @ setModel(BudgetTrackerModel) in BudgetTrackerPanel"
                );
        }
    }

    private AccountPanel accountPanel;

    public AccountPanel getAccountPanel() {
        if (accountPanel == null ) {
            System.err.println(
                "getAccountPanel() called when accountPanel is null in BudgetTrackerPanel"
                );
        }
        return accountPanel;
    }

    public void setAccountPanel(AccountPanel accountPanelIn) {
        if (accountPanelIn != null) accountPanel = accountPanelIn;
        else {
            System.err.println(
                "null accountPanelIn @ setAccountPanel(AccountPanel) in BudgetTrackerPanel"
                );
        }
    }

    private JButton editBudgetButton;

    public JButton getEditBudgetButton() {
        if (editBudgetButton == null ) {
            System.err.println(
                "getEditBudgetButton() called when editBudgetButton is null in BudgetTrackerPanel"
                );
        }
        return editBudgetButton;
    }

    public void setEditBudgetButton(JButton editBudgetButtonIn) {
        if (editBudgetButtonIn != null) editBudgetButton = editBudgetButtonIn;
        else {
            System.err.println(
                "null editBudgetButtonIn @ setEditBudgetButton(JButton) in BudgetTrackerPanel"
                );
        }
    }

    ///
    /// Constructors
    ///

    public BudgetTrackerPanel() {
        super();
        setLayout(new BorderLayout());

        //  Model
        setModel(new BudgetTrackerModel());

        // Buttons and Panel
        JButton editButton = new JButton();
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                model.openInput();
            }
        });
        AccountPanel aPanel = new AccountPanel(model);

        // Edit Button
        editButton.setText("Deposit/Withdraw");
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(editButton, BorderLayout.SOUTH);

        // Account Panel
        add(aPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}