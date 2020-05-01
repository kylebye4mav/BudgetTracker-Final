import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BudgetTrackerModel {

    ///
    /// Properties, Getters, Setters
    ///

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
            for (String accountLine : lines) parseAccount(accountLine);
            if (accountList.size() == 0) successful = false;
        }
        else {
            System.err.println("\"AccountIndex.json\" was not found in current directory");
            System.out.format("Downloading \"AccountIndex.json\" from %s", "https://kylebye-web.azurewebsites.net/AccountIndex.json\n");
            downloadAccountIndex();
        }

        return successful;
    }

    public void downloadAccountIndex() {
        HttpRequest request = new HttpRequest("https://kylebye-web.azurewebsites.net/AccountIndex.json");
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
                    firstName = nameParts[0];
                    lastName = nameParts[1];
                }
                else if (accountPartParts[0].equals("UserName")) {
                    userName = accountPartParts[1];
                }
                else if (accountPartParts[0].equals("Key")) {
                    key = Long.parseLong(accountPartParts[1]);
                }
            }

            if (firstName != null && lastName != null && userName != null && key != 0) {
                Account account = new Account(firstName, lastName, userName, key);
                accountList.add(account);
            }
            else {
                System.err.format(
                    "%s failed to parse in parseAccount(String) of BudgetTrackerModel\n", accountLine
                );
            }
        }
        catch (Exception e) {
            System.err.format(
                "%s failed to parse in parseAccount(String) of BudgetTrackerModel\n", accountLine
                );
            return;
        }
    }

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

    public void openSignIn() {
        SignInFrame signIn = new SignInFrame(this);
    }

    public void printIndex() {
        ArrayList<String> content = indexToStringList();

        for (String line : content) {
            System.out.println(line);
        }
    }

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

    public void accountSelected() {
        loadAccountBudget();
    }

    ///
    /// String Representations
    ///

    public ArrayList<String> indexToStringList() {
        return indexToStringList(accountList);
    }

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