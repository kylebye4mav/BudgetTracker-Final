import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author  Kyle Bye
 */
public class AccountBudget {

    ///
    /// Properties, Getters, Setters
    ///

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balanceIn) {
        balance = roundBalance(balanceIn);
    }

    private Date date;

    public Date getDate() {
        if (date == null) {
            System.err.println("getDate() called when date is null in AccountBudget");
        }
        return date;
    }

    public void setDate(Date dateIn) {
        if (dateIn != null) {
            date = dateIn;
        }
        else {
            System.err.println("null dateIn @ setDate(Date) in AccountBudget");
        }
    }

    ///
    /// Functions
    ///
    
    public double roundBalance(double balanceIn) {
        double roundedBalance = balanceIn;
        double roundPoint;
        int wholePart;

        if (balanceIn > 0) roundPoint = 0.5;
        else if (balanceIn < 0.0) roundPoint = -0.5;
        else {
            //  If balanceIn = 0.0. Just return.
            return roundedBalance;
        }

        wholePart = (int) ((balanceIn*100.00) + roundPoint);
        roundedBalance = ((double)wholePart)/100;

        return roundedBalance;
    }

    public static AccountBudget parseBudget(ArrayList<String> content) {
        AccountBudget aBudget;
        double balance;
        Date date;

        ArrayList<String> contentCopy = new ArrayList<String>(content);
        contentCopy.remove(contentCopy.size()-1);
        contentCopy.remove(0);

        //
        //  Parsing balance line
        //
        //  line:\t"balance":"$1.23",
        //
        String balanceStr = contentCopy.get(0).trim().replace("\"", new String());
        try {
            balance = Double.parseDouble(balanceStr.split(":")[1].replace(",", new String()).replace("$", new String()));
        }
        catch (Exception e) { 
            System.err.format("%s failed to parse", balanceStr); 
            return null;
        }

        //
        //  Parsing date line
        //
        //  line:\t"date":"1587925342153"
        //
        String dateStr = contentCopy.get(1).trim().replace("\"", new String());
        try {
            date = new Date(Long.parseLong(dateStr.split(":")[1]));
        }
        catch (Exception e) {
            System.err.format("%s failed to parse", dateStr); 
            return null;
        }

        aBudget = new AccountBudget(balance, date);
        return aBudget;
    }

    public void printBudget(Account accountIn) {
        ArrayList<String> content = budgetToStringList();

        System.out.println("Account Number: " + accountIn.toNameString().hashCode());

        for (String line : content) {
            System.out.println(line);
        }
    }

    public void writeBudget(Account accountIn) {
        PrintWriter fileWriter = null;
        String fileName = accountIn.toNameString().hashCode() + ".json";

        try {
            fileWriter = new PrintWriter(fileName);
        }
        catch (Exception e) {}

        ArrayList<String> content = budgetToStringList();

        for (String line : content) {
            fileWriter.println(line);
        }

        fileWriter.close();
    }

    public static void generateEmptyBalanceFile(String fileName) {
        PrintWriter fileWriter = null;

        try {
            fileWriter = new PrintWriter(fileName);
        }
        catch (Exception e) {}

        ArrayList<String> content = emptyBudgetToStringList();

        for (String line : content) {
            fileWriter.println(line);
        }

        fileWriter.close();
    }

    ///
    /// String Representations
    ///

    @Override
    public String toString() {
        String stringRep = new String();

        stringRep = String.format("$%.2f@%d", toBalanceString(), toDateString());

        return stringRep;
    }

    public String toBalanceString() {
        String stringRep = new String();

        stringRep = String.format("$%.2f", balance);

        return stringRep;
    }

    public String toDateString() {
        String stringRep = new String();

        stringRep = String.format("%d", date.getTime());

        return stringRep;
    }

    public ArrayList<String> budgetToStringList() {
        return budgetToStringList(this);
    }

    public static ArrayList<String> budgetToStringList(AccountBudget budget) {
        ArrayList<String> content = new ArrayList<String>();

        String formatter = "\t\"%s\":\"%s\"%s";
        String balanceStr = String.format(formatter, "balance", budget.toBalanceString(), ",");
        String dateStr = String.format(formatter, "date", budget.toDateString(), new String());

        content.add("{");
        content.add(balanceStr);
        content.add(dateStr);
        content.add("}");

        return content;
    }

    public static ArrayList<String> emptyBudgetToStringList() {
        ArrayList<String> content = new ArrayList<String>();

        String formatter = "\t\"%s\":\"%s\"%s";
        String balanceStr = String.format(formatter, "balance", "$0.00", ",");
        String dateStr = String.format(formatter, "date", new Date().getTime(), new String());

        content.add("{");
        content.add(balanceStr);
        content.add(dateStr);
        content.add("}");

        return content;
    }

    ///
    /// Constructors
    ///

    public AccountBudget() {
        this(0.0, new Date());
    }

    public AccountBudget(double balanceIn) {
        this(balanceIn, new Date());
    }

    public AccountBudget(double balanceIn, long dateTimeIn) {
        this(balanceIn, new Date(dateTimeIn));
    }

    public AccountBudget(double balanceIn, Date dateIn) {
        setDate(dateIn);
        setBalance(balanceIn);
    }

}