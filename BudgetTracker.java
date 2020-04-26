/**
 * @author  Kyle Bye
 */
public class BudgetTracker {
    public static void main(String[] args) {
        try {
            System.out.println("BudgetTracker starting...");
        }
        catch (Exception e) {
            System.err.format(
                "Something went wrong\nException: %s", 
                e.getMessage()
            );
        }

        BudgetTrackerFrame frame = new BudgetTrackerFrame();

        //  Todo:
        //      1) Build GUI
        //      2) Build Distribution with JSON
        //      3) Integrate Account System
        //      4) Incorporate Multi-Threading if possible.
        
        System.out.println("BudgetTracker ending...");
        return;
    }
}