import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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

    private JButton cancelButton;

    public JButton getCancelButton() {
        if (cancelButton == null) {
            System.err.println("getCancelButton() called when cancelButton is null in SignInPanel");
        }
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButtonIn) {
        if (cancelButtonIn != null) cancelButton = cancelButtonIn;
        else {
            System.err.println(
                "null cancelButtonIn @ setCancelButton(JButton) in SignInPanel"
                );
        }
    }

    ///
    /// Constructors
    ///

    public SignInPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //  Instruction Label
        JPanel instructionPanel = new JPanel();
        JLabel instructionLabel = new JLabel();
        instructionLabel.setText("<html><div style='text-align: center'>" +
            "Please type in your login credentials below.<br><br>" + 
            "Make sure CAPS LOCK is not on." + "<br><br>" +
            "</html>"
        );
        instructionPanel.add(instructionLabel);
        
        //  userNameField
        JPanel uPanel = new JPanel();
        JTextField uField = new JTextField();
        uField.setText("<TYPE HERE>");
        JLabel uLabel = new JLabel("Username:");
        uPanel.add(uLabel);
        uPanel.add(uField);

        //  passWordField
        JPanel pPanel = new JPanel();
        JTextField pField = new JTextField();
        pField.setText("<TYPE HERE>");
        JLabel pLabel = new JLabel("Password:");
        pPanel.add(pLabel);
        pPanel.add(pField);

        //  okButton
        JButton oButton = new JButton();
        oButton.setText("OK");

        //  cancelButton
        JButton cButton = new JButton();
        cButton.setText("Cancel");

        //  Button Panel
        JPanel bPanel = new JPanel();
        bPanel.add(oButton);
        bPanel.add(cButton);

        setUserNameField(uField);
        setPassWordField(pField);
        setOkButton(oButton);
        setCancelButton(cButton);

        add(instructionPanel);
        add(uPanel);
        add(pPanel);
        add(bPanel);
    }

}