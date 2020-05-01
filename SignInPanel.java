import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author  Kyle Bye
 */
@SuppressWarnings("Serial")
public class SignInPanel extends JPanel {

    ///
    /// Properties, Getters, Setters
    ///

    private JTextField userNameField;

    public JTextField getUserNameField() {
        if (userNameField == null) {
            System.err.println("getUserNameField() called when userNameField is null in SignInPanel");
        }
        return userNameField;
    }

    public void setUserNameField(JTextField userNameFieldIn) {
        if (userNameFieldIn != null) userNameField = userNameFieldIn;
        else {
            System.err.println(
                "null userNameFieldIn @ setUserNameField(JTextField) in SignInPanel"
                );
        }
    }

    private JTextField passWordField;

    public JTextField getPassWordField() {
        if (passWordField == null) {
            System.err.println("getPassWordField() called when passWordField is null in SignInPanel");
        }
        return passWordField;
    }

    public void setPassWordField(JTextField passWordFieldIn) {
        if (passWordFieldIn != null) passWordField = passWordFieldIn;
        else {
            System.err.println(
                "null passWordFieldIn @ setPassWordField(JTextField) in SignInPanel"
                );
        }
    }

    private JButton okButton;

    public JButton getOkButton() {
        if (okButton == null) {
            System.err.println("getOkButton() called when okButton is null in SignInPanel");
        }
        return okButton;
    }

    public void setOkButton(JButton okButtonIn) {
        if (okButtonIn != null) okButton = okButtonIn;
        else {
            System.err.println(
                "null okButtonIn @ setOkButton(JButton) in SignInPanel"
                );
        }
    }

    private BudgetTrackerModel model;

    public BudgetTrackerModel getModel() {
        if (model == null ) {
            System.err.println(
                "getModel() called when model is null in SignInPanel"
                );
        }
        return model;
    }

    public void setModel(BudgetTrackerModel modelIn) {
        if (modelIn != null) model = modelIn;
        else {
            System.err.println(
                "null modelIn @ setModel(BudgetTrackerModel) in SignInPanel"
                );
        }
    }

    ///
    /// Constructors
    ///

    public SignInPanel(BudgetTrackerModel modelIn) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //  userNameField

        JTextField uField = new JTextField();

        //  passWordField

        JTextField pField = new JTextField();

        //  okButton

        JButton oButton = new JButton();
        oButton.setText("OK");

        //  cancelButton

        JButton cButton = new JButton();
        cButton.setText("Cancel");

        setModel(modelIn);
        setUserNameField(uField);
        setPassWordField(pField);
        setOkButton(oButton);

        add(uField);
        add(pField);
        add(oButton);
        add(cButton);
    }

}