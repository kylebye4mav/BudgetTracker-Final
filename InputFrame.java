import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class InputFrame extends JFrame implements ActionListener {

    ///
    /// Properties, Getters, Setters
    ///

    private BudgetTrackerModel model;

    public BudgetTrackerModel getModel() {
        if (model == null) {
            System.err.println(
                "getModel() called when model is null in InputFrame"
                );
        }
        return model;
    }

    public void setModel(BudgetTrackerModel modelIn) {
        if (modelIn != null) model = modelIn;
        else {
            System.err.println(
                "null modelIn @ setModel(BudgetTrackerModel) in InputFrame"
                );
        }
    }

    private InputPanel panel;

    public InputPanel getPanel() {
        if (panel == null) {
            System.err.println(
                "getPanel() called when panel is null in InputFrame"
                );
        }
        return panel;
    }

    public void setPanel(InputPanel panelIn) {
        if (panelIn != null) panel = panelIn;
        else {
            System.err.println(
                "null panelIn @ setPanel(InputPanel) in InputFrame"
                );
        }
    }

    ///
    /// Functions
    ///

    /**
     * Takes the user input from the <code>InputField</code>'s,
     * checks the input utilizing <code>InputPanel</code>'s <code>
     * acceptableInput(String)</code> method and updates <code>
     * AccountPanel</code>'s labels if the input is acceptable.
     * Otherwise, a warning window will pop up.
     * <br><br>
     * This method will not exit without doing anything if panel or 
     * any of its components are null.
     */
    public void processInput() {
        if (panel.isNull()) {
            System.err.println("Cannot process input in InputPanel due to null componenets");
            return;
        }

        System.out.println("@Notification: Managing user input in InputFrame.");
        
        String userInput = panel.getInputField().getText();
        userInput = panel.cleanInput(userInput);
        if (panel.acceptableInput(userInput)) {
            try {
                double val = Double.parseDouble(userInput);
                if (panel.getComboBox().getSelectedItem().equals(InputPanel.WITHDRAW)){
                    val *= -1;
                }

                AccountBudget budget = getModel().getAccountBudget();
                budget.setBalance(budget.getBalance() + val);
                budget.writeBudget(model.getSelectedAccount());
                model.update();
                this.dispose();
            }
            catch (Exception e) {}
        }
        else {
            JOptionPane.showMessageDialog(null, "The value that you provided is not valid.", "Invalid Input!", JOptionPane.WARNING_MESSAGE);
        }
    }

    ///
    /// Events
    ///

    /**
     * Executes <code>processInput()</code>.
     * 
     * @see InputFrame#processInput()
     */
    public void actionPerformed(ActionEvent ae) {
        processInput();
    }

    ///
    /// Constructors
    ///

    public InputFrame(BudgetTrackerModel modelIn) {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);
        setTitle("Editing Budget");

        InputPanel panel = new InputPanel();
        panel.getOkButton().addActionListener(this);

        setPanel(panel);
        setModel(modelIn);

        add(panel);

        pack();

        setVisible(true);
    }
}