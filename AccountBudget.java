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