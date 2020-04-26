import java.io.IOException;
import java.io.PrintWriter;
/**
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

    public static long generateKey(String passWordIn) {
        return passWordIn.hashCode();
    }

    public boolean compareKey(String passWordIn) {
        long correctKey = getKey();
        long providedKey = generateKey(passWordIn);

        return correctKey == providedKey;
    }

    public static boolean generateAccountJSON(String firstNameIn, String lastNameIn, String userNameIn, long keyIn) {
        Account account = new Account(firstNameIn, lastNameIn, userNameIn, keyIn);
        return generateAccountJSON(account);
    }

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