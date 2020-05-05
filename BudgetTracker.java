/**
 * This program is responsible for helping a user manage a budget.
 * @author  Kyle Bye
 */
public class BudgetTracker {
    /**
     * Program Execution begins here.
     * @param   args    program args.
     */
    public static void main(String[] args) {
        try {
            System.out.println("BudgetTracker starting...");
            BudgetTrackerFrame frame = new BudgetTrackerFrame();
            System.out.println("BudgetTracker ending...");
        }
        catch (Exception e) {
            System.err.format(
                "Something went wrong\nException: %s", 
                e.getMessage()
            );
        }
        return;
    }
}