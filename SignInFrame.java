import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * This class is responsible for hosting the window
 * for the sign in procedure.
 * 
 * @author  Kyle Bye
 * @see BudgetTrackerModel
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
    /// Functions
    ///

    /**
     * Takes user input from textfields of the panel, 
     * verifies the credentials, and initates the sign in sequence
     * by calling <code>model.setSelectedAccount(Account)</code>.
     * <br><br>
     * If the verification fails, a notification message is printed
     * and window pops up informing the user that the credentials are
     * invalid.
     * 
     * @see BudgetTrackerModel#setSelectedAccount(Account)
     */
    public void loginProcedure() {
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
    /// Events
    ///

    /**
     * Calls <code>loginProcedure()</code>
     */
    public void actionPerformed(ActionEvent ae) {
        loginProcedure();
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

        //
        //  Panel
        //      1.) Have OkButton begin the login procedure when clicked.
        //      2.) Have CancelButton close and dispose this instance when clicked.
        //  
        SignInPanel pan = new SignInPanel();
        pan.getOkButton().addActionListener(this);
        pan.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) { dispose(); }
        });
        setPanel(pan);
        add(pan);
        pack();

        setVisible(true);
    }

}