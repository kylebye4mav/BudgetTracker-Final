import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * This class is reponsible for handling all the events, data,
 * and all the gui components of the BudgetTracker application.
 * 
 * @author  Kyle Bye
 */
public class BudgetTrackerModel {

    ///
    /// Properties, Getters, Setters
    ///

    private static final String indexURL = "https://kylebye-web.azurewebsites.net/AccountIndex.json";

    private ArrayList<Updatable> updatables;

    public ArrayList<Updatable> getUpdatables() {
        if (updatables == null) {
            System.err.println("getUpdatables() called when updatables is null in BudgetTrackerModel");
        }
        return updatables;
    }

    public void setUpdatables(ArrayList<Updatable> updatablesIn) {
        if (updatablesIn != null) {
            updatables = updatablesIn;
        }
        else {
            System.out.println("null updatablesIn @ setUpdatables(ArrayList<Updatable>) in BudgetTrackerModel");
        }
    }


    private Account selectedAccount;

    public Account getSelectedAccount() {
        if (selectedAccount == null) {
            System.err.println("getSelectedAccount() called when selectedAccount is null in BudgetTrackerModel");
            System.out.println("@Warning There's no account selected in BudgetTrackerModel.");
        }
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccountIn) {
        if (selectedAccountIn != null) {
            selectedAccount = selectedAccountIn;
            loadAccountBudget();
            update();
        }
        else {
            System.out.println("null selectedAccountIn @ setSelectedAccount(Account) in BudgetTrackerModel");
            System.out.println("@Warning No selected account in BudgetTrackerModel");
        }
    }

    private ArrayList<Account> accountList;

    public ArrayList<Account> getAccountList() {
        if (accountList == null) {
            System.err.println("getAccountList() called when accountList is null in BudgetTrackerModel");
        }
        return accountList;
    }

    public void setAccountList(ArrayList<Account> accountListIn) {
        if (accountListIn != null) {
            accountList = accountListIn;
        }
        else {
            System.err.println("null accountListIn @ setAccountList(ArrayList<Account>) in BudgetTrackerModel");
            setAccountBudget(null);
        }
    }

    private AccountBudget accountBudget;

    public AccountBudget getAccountBudget() {
        if (accountBudget == null) {
            System.err.println("getAccountBudget() called when account is null in BudgetTrackerModel");
        }
        return accountBudget;
    }

    public void setAccountBudget(AccountBudget accountBudgetIn) {
        if (accountBudgetIn != null) {
            accountBudget = accountBudgetIn;
        }
        else {
            System.err.println("null accountBudgetIn @ setAccountBudget(AccountBudget) in BudgetTrackerModel");
        }
    }

    ///
    /// Functions
    ///

    /**
     * Calls <code>update()</code> of every updateable object from
     * the updatableList of this instance.
     * 
     * @see Updatable
     */
    public void update() {
        if (updatables == null) {
            System.err.println("update() called when updatables is null in BudgetTrackerModel");
            return;
        }
        if (updatables.isEmpty()) {
            System.out.println("@Notification: update() called when updatables is empty.");
        }

        ArrayList<Thread> threadList = new ArrayList<Thread>();
        for (Updatable updatable : updatables) {
            if (updatable instanceof Thread) {
                threadList.add((Thread)updatable);
            } 
            else updatable.update();
        }
        try {
            for (Thread t : threadList) t.start();
            for (Thread t : threadList) t.join();
        }
        catch (Exception e) {}
    }

    /**
     * Takes a username String and checks every Account of accountList
     * if an account exists with the provided username.
     * 
     * @param   userNameIn  username to look for in accountList.
     * @return  Account instance if account with provided username exists.
     *          null otherwise.
     * @see Account
     */
    public Account findAccount(String userNameIn) {

        if (accountList == null) {
            System.err.println("null accountList @ retrieveAccountUserNames() in BudgetTrackerModel");
            return null;
        }

        for (Account acc : getAccountList()) {
            if (acc.getUserName().equals(userNameIn)) {
                return acc;
            }
        }

        System.err.format("Account not found with username \"%s\"", userNameIn);
        return null;
    }

    /**
     * Takes all the account instances and returns an arraylist of
     * Strings of the username of all the instances.
     * 
     * @return  ArrayList of usernames.
     * @see Account
     */
    public ArrayList<String> retrieveAccountUserNames() {
        ArrayList<String> userNameList = new ArrayList<String>();

        if (accountList != null) {
            String userName;

            for (Account acc : getAccountList()) {
                userName = acc.getUserName();
                userNameList.add(userName);
            }
        }
        else {
            System.err.println("null accountList @ retrieveAccountUserNames() in BudgetTrackerModel");
        }

        return userNameList;
    }

    /**
     * Looks for a file called "AccountIndex.json".
     * If it exists, read and parse the data into account objects
     * to be stored in accountList.
     * Otherwise, download the json file from my website and recall
     * the method.
     * 
     * This method is multi-threaded.
     * 
     * @return  true if succeeded. false if failed.
     * @see Account
     */
    public boolean loadAccountIndex() {
        //  Check if AccountIndex.json exists.
        //  If so, load it. Otherwise, download it.
        boolean successful = true;

        File accountIndex = new File("AccountIndex.json");

        if (accountIndex.exists()) {
            // Load
            BufferedReader fileReader = null;
            try {
                fileReader = new BufferedReader(new FileReader(accountIndex));
            }
            catch (FileNotFoundException fnfe){}

            //  Read File
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            try {
                while ((line = fileReader.readLine()) != null) lines.add(line);
                fileReader.close();
            }
            catch (IOException ioe) {}

            //  Chop off first and last line
            lines.remove(lines.size()-1);
            lines.remove(0);

            setAccountList(new ArrayList<Account>());

            //  Parse each Account line
            //  Multi-Threading is used here.
            try {
                ArrayList<Thread> threadList = new ArrayList<Thread>();
                ArrayList<AccountParser> accountParseList = new ArrayList<AccountParser>();
                for (String accountLine : lines) {
                    AccountParser ap = new AccountParser(accountLine);
                    threadList.add(new Thread(ap));
                    accountParseList.add(ap);
                }
                for (Thread t : threadList) t.start();
                for (Thread t : threadList) t.join();
                for (AccountParser ap : accountParseList) {
                    if (ap.getAccount() != null) accountList.add(ap.getAccount());
                }
            }
            catch(Exception e) { System.err.println("MT went wrong!"); }
            if (accountList.size() == 0) successful = false;
        }
        else {
            System.err.println("\"AccountIndex.json\" was not found in current directory");
            System.out.format("Downloading \"AccountIndex.json\" from %s", "https://kylebye-web.azurewebsites.net/AccountIndex.json\n");
            downloadAccountIndex();
        }

        return successful;
    }

    /**
     * Calls an <code>HttpRequest</code> to my website and writes the file.
     * Calls <code>loadAccountIndex()</code>.
     */
    public void downloadAccountIndex() {
        HttpRequest request = new HttpRequest(indexURL);
        request.readUrl();
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter("AccountIndex.json");
        }
        catch (Exception e) {}
        for (String contentLn : request.getContent()) {
            fileWriter.println(contentLn);
        }
        fileWriter.close();
        loadAccountIndex();
    }

    /**
     * Takes a line from the "AccountIndex.json" and parsed the line
     * into an Account instance to be placed into accountList. A warning 
     * message is printed if a line fails to parse.
     * 
     * @param   accountLine String to parse into an Account instance.
     */
    public void parseAccount(String accountLine) {
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
                Account account = new Account(firstName, lastName, userName, key);
                accountList.add(account);
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
    }

    /**
     * Looks for an AccountBudget file that corresponds with selectedAccount,
     * parse the file and sets the accountBudget instance.
     * If the file does not exist, an empty AccountBudget file is created,
     * a warning message is printed, and this method is recalled.
     * <br><br>
     * If selectedAccount is null. This method ends without processing
     * and a null message is printed.
     * 
     * @see AccountBudget
     */
    public void loadAccountBudget() {
        if (selectedAccount == null) {
            System.err.println("null selectedAccount @ loadAccountBudget(Account) in BudgetTrackerFrame");
            return;
        }

        String fileName = selectedAccount.toNameString().hashCode() + ".json";
        File budgetFile = new File(fileName);

        if (!budgetFile.exists()) {
            System.out.format("@Warning: %s was not found for account \"%s\".\n", fileName, selectedAccount.toNameString());
            System.out.format("@Warning: generating new file named \"%s\"\n", fileName);
            AccountBudget.generateEmptyBalanceFile(fileName);
            loadAccountBudget();
            return;
        }

        ArrayList<String> content = new ArrayList<String>();
        BufferedReader fileReader = null;
        try { fileReader = new BufferedReader(new FileReader(budgetFile)); }
        catch (FileNotFoundException fnfe) {}
        String line;
        try { while ((line = fileReader.readLine()) != null) content.add(line); }
        catch (IOException ioe) {}

        AccountBudget aBudget = AccountBudget.parseBudget(content);

        if (aBudget == null) {
            System.out.format("@Warning: for account \"%s\", %s failed to parse.\n", selectedAccount.toNameString(), fileName);
            System.out.format("@Warning: generating new file named \"%s\"\n", fileName);
            loadAccountBudget();
            return;
        }

        setAccountBudget(aBudget);
    }

    /**
     * Instantiates a <code>SignInFrame</code> instance and
     * prints a notification message.
     */
    public void openSignIn() {
        System.out.println("@Notification: Opening SignInFrame...");
        SignInFrame signIn = new SignInFrame(this);
    }

    /**
     * Instantiates a <code>InputFrame</code> instance and
     * prints a notification message only if selectedAccount
     * is not null.
     * <br><br>
     * Otherwise, a warning message is printed and a popup window
     * appears to inform the user that the user must sign in.
     * 
     * @see InputFrame
     */
    public void openInput() {
        if (getSelectedAccount() != null) {
            System.out.println("@Notification: Opening InputFrame...");
            InputFrame input = new InputFrame(this);
        }    
        else {
            System.out.println("@Warning: Attempt to open InputFrame without a selected account");
            JOptionPane.showMessageDialog(null, "Please sign in first!", "Sign In!", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Writes "AccountIndex.json" to the console including every
     * account in accountList.
     * 
     * @see Account
     */
    public void printIndex() {
        ArrayList<String> content = indexToStringList();

        for (String line : content) {
            System.out.println(line);
        }
    }

    /**
     * Writes "AccountIndex.json" including every
     * account in accountList to the console.
     * 
     * @see Account
     */
    public void writeIndex() {
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter("AccountIndex.json");
        }
        catch (Exception e) {}

        ArrayList<String> content = indexToStringList();

        for (String line : content) {
            fileWriter.println(line);
        }
        fileWriter.close();
    }

    ///
    /// Events
    ///

    /**
     * Calls <code>loadAccountBudget()</code>.
     * 
     * @see BudgetTrackerModel#loadAccountBudget()
     */
    public void accountSelected() {
        loadAccountBudget();
    }

    ///
    /// String Representations
    ///

    /**
     * Calls and returns the return list of 
     * <code>indexToStringList(ArrayList<Account> accountListIn)
     * </code>
     * 
     * @return  returns a list of strings that represents the AccountIndex.
     * @see Account
     * @see BudgetTrackerModel#indexToStringList(ArrayList)
     */
    public ArrayList<String> indexToStringList() {
        return indexToStringList(accountList);
    }

    /**
     * Returns a list of strings that represents the AccountIndex.
     * 
     * @param   accountListIn   list of Accounts to write to a string list.
     * @return  returns a list of strings that represents the AccountIndex.
     */
    public static ArrayList<String> indexToStringList(ArrayList<Account> accountListIn) {
        //  In Format:
        //  [
        //      { "Name":"First Last1", "UserName":"userName1", "Key":"31231" },
        //      { "Name":"First Last2", "UserName":"userName2", "Key":"3123331" }
        //  ]
        ArrayList<Account> accounts = accountListIn;
        ArrayList<String> content = new ArrayList<String>();
        content.add("[");
        for (int i = 0; i<accounts.size(); ++i) {
            String accountLine = accounts.get(i).toString();
            if (i != content.size()-1) accountLine += ",";
            content.add(accountLine);
        }
        content.add("]");

        return content;
    }

    ///
    /// Constructors
    ///

    public BudgetTrackerModel() {
        setUpdatables(new ArrayList<Updatable>());
        if (!loadAccountIndex()) {
            setAccountList(new ArrayList<Account>());
        }
        setSelectedAccount(null);
    }
}