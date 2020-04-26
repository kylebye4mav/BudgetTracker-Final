import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;

/**
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

    private JButton viewBudgetButton;

    public JButton getViewBudgetButton() {
        if (viewBudgetButton == null ) {
            System.err.println(
                "getViewBudgetButton() called when viewBudgetButton is null in BudgetTrackerPanel"
                );
        }
        return viewBudgetButton;
    }

    public void setViewBudgetButton(JButton viewBudgetButtonIn) {
        if (viewBudgetButtonIn != null) viewBudgetButton = viewBudgetButtonIn;
        else {
            System.err.println(
                "null viewBudgetButtonIn @ setViewBudgetButton(JButton) in BudgetTrackerPanel"
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
        return viewBudgetButton;
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
        JButton viewButton = new JButton();
        JButton editButton = new JButton();
        AccountPanel aPanel = new AccountPanel();

        // View Button
        viewButton.setText("View Budget");
        add(viewButton, BorderLayout.LINE_START);

        // Edit Button
        editButton.setText("Deposit/Withdraw");
        add(editButton, BorderLayout.LINE_END);

        // Account Panel
        add(aPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}