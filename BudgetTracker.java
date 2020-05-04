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
        InputFrame frameB = new InputFrame(frame.getPanel().getModel());


        //  Todo:
        //      1) Build GUI 
        //          ==> 50% Done.                    
        //      2) Build Distribution with JSON   
        //          ==> Done.
        //      3) Integrate Account System
        //          ==> 50% Done.
        //      4) Incorporate Multi-Threading if possible.
        //          ==> 0% Done.
        
        System.out.println("BudgetTracker ending...");
        return;
    }
}