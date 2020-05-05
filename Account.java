import java.io.IOException;
import java.io.PrintWriter;
/**
 * This class holds the first name, last name, username,
 * and key of a user.
 * 
 * @author  Kyle Bye
 */
public class Account {

    ///
    /// Properties, Getters, Setters
    ///

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNameIn) {
        firstName = firstNameIn;
    }

    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNameIn) {
        lastName = lastNameIn;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userNameIn) {
        userName = userNameIn;
    }

    private long key;

    public long getKey() {
        return key;
    }

    public void setKey(long keyIn) {
        key = keyIn;
    }

    ///
    /// Functions
    ///

    /**
     * Returns true if all the fields of this instance
     * are non null or default.
     * 
     * @return true if valid. false if not.
     */
    public boolean validate() {
        boolean isValid = true;

        if (firstName == null || firstName.equals(new String())) {
            isValid = false;
        }
        else if (lastName == null || lastName.equals(new String())) {
            isValid = false;
        }
        else if (userName == null || userName.equals(new String())) {
            isValid = false;
        }
        else if (key == -1 || key == 0) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Returns the hashcode of the parameter.
     * 
     * @param   passWordIn to be translated to hashcode.
     * @return  hashcode version of parameter.
     */
    public static long generateKey(String passWordIn) {
        return passWordIn.hashCode();
    }

    /**
     * Compares the hashcode values of this instance and parameter
     * passwords.
     * 
     * @param   passWordIn  password to compare to the key of this instance.
     * @return  returns true if keys match. Otherwise, false is returned.
     */
    public boolean compareKey(String passWordIn) {
        long correctKey = getKey();
        long providedKey = generateKey(passWordIn);

        return correctKey == providedKey;
    }

    /**
     * Instantiates an Account instance and calls
     * <code>generateAccountJSON(Account accountIn)</code>
     * 
     * @param firstNameIn
     * @param lastNameIn
     * @param userNameIn
     * @param keyIn
     * @return  true if succeeded. false if not.
     * @see Account#generateAccountJSON(Account)
     */
    public static boolean generateAccountJSON(String firstNameIn, String lastNameIn, String userNameIn, long keyIn) {
        Account account = new Account(firstNameIn, lastNameIn, userNameIn, keyIn);
        return generateAccountJSON(account);
    }

    /**
     * Writes contents of this instance into a json file where the initials are the file name.
     * 
     * @param   accountIn   account to write to file.
     * @return  true if succeeded. false if not.
     */
    public static boolean generateAccountJSON(Account accountIn) {
        System.out.println("Generating New Account JSON File...");
        boolean isSuccessful = true;

        if (!accountIn.validate()) {
            System.err.println(
                "Invalid/null Account provided when calling generateAccountJSON(Account)"
            );
            isSuccessful = false;
            return isSuccessful;
        }

        String initials = new String();
        initials += accountIn.getFirstName().substring(0,1);
        initials += accountIn.getLastName().substring(0,1);

        PrintWriter fileWriter = null;

        try {
            fileWriter = new PrintWriter(initials + ".json");
            fileWriter.println("{");
            fileWriter.format("\t\"firstName\": \"%s\",\n", accountIn.getFirstName());
            fileWriter.format("\t\"lastName\": \"%s\",\n", accountIn.getLastName());
            fileWriter.format("\t\"userName\": \"%s\",\n", accountIn.getUserName());
            fileWriter.format("\t\"key\": %d\n", accountIn.getKey());
            fileWriter.println("}");
        }
        catch (IOException ioe) {
            System.err.format(
                "Something went wrong\nException: %s", 
                ioe.getMessage()
            );
            isSuccessful = false;
        }

        fileWriter.close();
        System.out.format("%s.json created\n", initials);
        return isSuccessful;
    }

    ///
    /// String Representation
    ///

    @Override
    public String toString() {
        String stringRep = new String();

        stringRep = String.format(
            "\t{ \"Name\":\"%s\", \"UserName\":\"%s\", \"Key\":\"%d\" }",
            firstName + " " + lastName, userName, key
            );

        return stringRep;
    }

    /**
     * Returns the first name and last name of this instance
     * into a String with a space in between.
     * 
     * @return  first name and last name with space in between.
     */
    public String toNameString() {
        String stringRep = new String();

        stringRep = String.format(
            "%s %s", firstName, lastName 
            );
        
        return stringRep;
    }

    ///
    /// Constructors
    ///

    public Account() {
        this("Unknown", "Name", "", -1);
    }

    public Account(String firstNameIn, String lastNameIn, String userNameIn, String passWordIn) {
        this(firstNameIn, lastNameIn, userNameIn, generateKey(passWordIn));
    }

    public Account(String firstNameIn, String lastNameIn, String userNameIn, long keyIn) {
        setFirstName(firstNameIn);
        setLastName(lastNameIn);
        setUserName(userNameIn);
        setKey(keyIn);
    }

}