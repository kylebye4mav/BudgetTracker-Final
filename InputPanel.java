import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class InputPanel extends JPanel {

    ///
    /// Properties, Getters, Setters
    ///

    private static final String DEPOSIT = "Deposit";
    private static final String WITHDRAW = "Withdraw";

    private static final char[] acceptableChars = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'
    };

    private JComboBox<String> comboBox;

    public JComboBox<String> getComboBox() {
        if (comboBox == null) {
            System.err.println(
                "getComboBox() called when comboBox is null in InputPanel"
                );
        }
        return comboBox;
    }

    public void setComboBox(JComboBox<String> comboBoxIn) {
        if (comboBoxIn != null) comboBox = comboBoxIn;
        else {
            System.err.println(
                "null comboBoxIn @ setComboBox(JComboBox<String>) in InputPanel"
                );
        }
    }

    private JTextField inputField;

    public JTextField getInputField() {
        if (inputField == null) {
            System.err.println(
                "getInputField() called when inputField is null in InputPanel"
                );
        }
        return inputField;
    }

    public void setInputField(JTextField inputFieldIn) {
        if (inputFieldIn != null) inputField = inputFieldIn;
        else {
            System.err.println(
                "null inputFieldIn @ setInputField(JTextField) in InputPanel"
                );
        }
    }

    private JButton okButton;

    public JButton getOkButton() {
        if (okButton == null) {
            System.err.println("getOkButton() called when okButton is null in InputPanel");
        }
        return okButton;
    }

    public void setOkButton(JButton okButtonIn) {
        if (okButtonIn != null) okButton = okButtonIn;
        else {
            System.err.println(
                "null okButtonIn @ setOkButton(JButton) in InputPanel"
                );
        }
    }

    ///
    /// Functions
    ///

    /**
     * Returns true if and only if this instance and its fields
     * are initialized.
     * 
     * @return true or false depending on if this instance
     *  or any other field of this instance is null.
     */
    public boolean isNull() {
        return (this == null) && (getComboBox() == null) && 
        (getInputField() == null) && (getOkButton() == null);
    }

    /**
     * Returns true if and only if every character within the parameter
     * is acceptable in terms of being purely numeric. 
     * <br><br>
     * You must use cleanInput(String) before using this method or else
     * this method will absolutely fail by returning false when the input
     * may actually be fine.
     * 
     * @param   input   String to check
     * @return  true or false depending if the string is numeric.
     */
    public boolean acceptableInput(String input) {
        //  Check every character if is an acceptable character.
        //  See InputPanel.acceptableChars for the array of
        //  acceptable characters.
        for (int i = 0; i<input.length(); ++i) if (!acceptableChar(input.charAt(i))) {
            return false;
        }
        //  Check if there is a decimal. If so, decimal check.
        if (input.contains(".")) {
            //  Since input contains a decimal, this is a check
            //  for more than one decimal.
            //  Example:
            //      1.2.3 is not acceptable
            //      .....1 is not acceptable
            //      1.2 is acceptable
            if (input.indexOf(".") != input.lastIndexOf(".")) return false;
            String[] inputParts = input.split(".");

            //  Check if there are two numbers after the decimal
            //  Example:
            //      1.234 is not acceptable
            //      1.23 is acceptable
            //      1.2 is acceptable
            //      1. is acceptable
            String afterTheDecimal = inputParts[1];
            if (afterTheDecimal.length() > 2) return false;

        }
        return true;
    }

    /**
     * Returns true if and only if the parameter character is
     * part of the acceptableChars array. In other words, if the
     * character is numeric.
     * 
     * @param   c   character to check.
     * @return  true or false if character is numeric.
     */
    public boolean acceptableChar(char c) {
        for (char ch : acceptableChars) if (ch == c) return true;
        return false;
    }

    /**
     * 
     * @param   input
     * @return  returns a String with '$' and '-' removed 
     * in the first and/or second String positions.
     */
    public String cleanInput(String input) {
        String cleansedInput = new String();

        for (int i = 0; i<cleansedInput.length(); ++i) {
            char c = cleansedInput.charAt(i);
            if (i == 0 && (c == '$' |
        }
    }

    ///
    /// Constructors
    ///

    public InputPanel() {
        super();

        //  comboBox
        JComboBox<String> combo = new JComboBox<String>();
        combo.addItem(DEPOSIT);
        combo.addItem(WITHDRAW);

        //  Instruction Label
        JLabel instructionLabel = new JLabel();
        String instructions = "Using the dropdown menu below, please indicate whether this " +
        "is a deposit or a withdraw. Afterwards, type the amount you want to deposit or " +
        "withdraw in the box below. Please do not type in a '$' or a '-' character as those " +
        "are dealt with beforehand.";
        instructionLabel.setText(instructions);

        //  inputField
        JTextField input = new JTextField();
        input.setText("Type amount here");

        //  okButton
        JButton ok = new JButton();
        ok.setText("OK");

        //  Setters
        setComboBox(combo);
        setInputField(input);
        setOkButton(ok);

        //  Add Components
        add(comboBox);
        add(instructionLabel);
        add(inputField);
        add(ok);
    }
}