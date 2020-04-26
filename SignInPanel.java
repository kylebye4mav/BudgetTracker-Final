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

    ///         LEFT OFF HERE
    ///

    private JButton cancelButton;

    ///
    /// Constructors
    ///

    public SignInPanel() {
        super();
    }

}