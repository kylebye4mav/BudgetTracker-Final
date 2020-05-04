import javax.swing.JFrame;
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
    /// Events
    ///

    public void actionPerformed(ActionEvent ae) {
        if (panel.isNull()) {
            System.err.println("Cannot process input in InputPanel due to null componenets");
        }
        
        String userInput = panel.getInputField().getText();
        userInput = panel.cleanInput(userInput);
    }

    ///
    /// Constructors
    ///

    public InputFrame(BudgetTrackerModel model) {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 600, 400);

        InputPanel panel = new InputPanel();
        panel.getOkButton().addActionListener(this);

        setPanel(panel);

        add(panel);

        setVisible(true);
    }
}