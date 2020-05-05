/**
 * This class is responsible for parsing <code>Account</code>
 * objects.
 * 
 * @author  Kyle Bye
 * @see Account
 */
public class AccountParser implements Runnable {

    ///
    /// Properties, Getters, Setters
    ///

    private String accountLine;

    public String getAccountLine() {
        return accountLine;
    }

    public void setAccountLine(String accountLineIn) {
        accountLine = accountLineIn;
    }

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accountIn) {
        account = accountIn;
    }

    ///
    /// Functions
    ///

    /**
     * Calls <code>parseAccountLine()</code>
     * 
     * @see AccountParser#parseAccountLine()
     */
    public void run() {
        parseAccountLine();
    }

    /**
     * Takes a line from the "AccountIndex.json" and parsed the line
     * into an Account instance to be placed into account. A warning 
     * message is printed if a line fails to parse.
     * 
     * @param   accountLine String to parse into an Account instance.
     */
    public void parseAccountLine() {

        Account account = null;

        try {
            String firstName = null;
            String lastName = null;
            String userName = null;
            long key = 0;

            accountLine = accountLine.trim();
            accountLine = accountLine.replaceAll("\"", new String());
            accountLine = accountLine.replace("{", new String());
            accountLine = accountLine.replace("}", new String());

            String[] accountParts = accountLine.split(",");
            for (String accountPart : accountParts) {
                String[] accountPartParts = accountPart.trim().split(":");
                if (accountPartParts[0].equals("Name")) {
                    String[] nameParts = accountPartParts[1].split(" ");
                    firstName = nameParts[0].trim();
                    lastName = nameParts[1].trim();
                }
                else if (accountPartParts[0].equals("UserName")) {
                    userName = accountPartParts[1].trim();
                }
                else if (accountPartParts[0].equals("Key")) {
                    key = Long.parseLong(accountPartParts[1].trim());
                }
            }

            if (firstName != null && lastName != null && userName != null && key != 0) {
                account = new Account(firstName, lastName, userName, key);
            }
            else {
                System.err.format(
                    "@Warning: %s failed to parse in parseAccount(String) of BudgetTrackerModel\n", accountLine
                );
            }
        }
        catch (Exception e) {
            System.err.format(
                "@Warning: %s failed to parse in parseAccount(String) of BudgetTrackerModel\n", accountLine
                );
            return;
        }

        setAccount(account);
    }

    ///
    /// Constructors
    ///

    public AccountParser(String accountLineIn) {
        setAccountLine(accountLineIn);
        setAccount(null);
    }
    
}