import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class SignInFrame extends JFrame implements ActionListener {

    ///
    /// Properties, Getters, Setters
    ///

    private BudgetTrackerModel model;

    public BudgetTrackerModel getModel() {
        if (model == null ) {
            System.err.println(
                "getModel() called when model is null in SignInFrame"
                );
        }
        return model;
    }

    public void setModel(BudgetTrackerModel modelIn) {
        if (modelIn != null) model = modelIn;
        else {
            System.err.println(
                "null modelIn @ setModel(BudgetTrackerModel) in SignInFrame"
                );
        }
    }

    private SignInPanel panel;

    public SignInPanel getPanel() {
        return panel;
    }

    public void setPanel(SignInPanel panelIn) {
        panel = panelIn;
    }

    ///
    /// Events
    ///

    public void actionPerformed(ActionEvent ae) {
        boolean logInSuccessful = true;
        String userName, passWord;

        userName = panel.getUserNameField().getText().trim();
        passWord = panel.getPassWordField().getText().trim();

        if (userName == null || userName.equals(new String()))

        System.out.println(userName);

        //  Check if username exists
        if (!model.retrieveAccountUserNames().contains(userName)) {
            logInSuccessful = false;
            System.err.format("Username \"%s\" does not exist.\n", userName);
        }
        //  Retrieve account since it exists.
        else {
            Account acc = model.findAccount(userName);

            //  Now check password
            if (!acc.compareKey(passWord)) {
               logInSuccessful = false; 
               System.err.format(
                   "Password \"%s\" does not match account \"%s\".\n",
                   passWord, acc.toNameString()
                   );
            }
            //  Password matches, so set the selected account.
            else {
                System.out.println("@Notification: Setting account.");
                model.setSelectedAccount(acc);
                setVisible(false);
                dispose();
            }
        }

        if (logInSuccessful) {
            System.out.format("@Notification: Account with username %s logged in.\n", userName);
        }
        else {
            System.out.println("@Notification: Login failed.");
            JOptionPane.showMessageDialog(null, "Login Failed!", "Login Failed!", JOptionPane.WARNING_MESSAGE);
        }
    }


    ///
    /// Constructors
    ///

    public SignInFrame(BudgetTrackerModel modelIn) {
        super();
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 400, 500);
        setModel(modelIn);

        //  Panel
        SignInPanel pan = new SignInPanel(modelIn);
        pan.getOkButton().addActionListener(this);
        setPanel(pan);
        add(pan);

        setVisible(true);
    }

}