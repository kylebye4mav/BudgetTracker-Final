import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
/**
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class BudgetTrackerFrame extends JFrame {

    ///
    /// Properties, Getters, Setters
    ///

    private BudgetTrackerPanel panel;

    public BudgetTrackerPanel getPanel() {
        if (panel == null) {
            System.err.println("getPanel() called when panel is null in BudgetTrackerFrame");
        }
        return panel;
    }

    public void setPanel(BudgetTrackerPanel panelIn) {
        if (panelIn != null) panel = panelIn;
        else {
            System.err.println("null panelIn @ setPanel(BudgetTrackerPanel) in BudgetTrackerFrame");
        }
    }

    ///
    /// Constructors
    ///

    public BudgetTrackerFrame() {
        this(0, 0, 600, 400);
    }

    public BudgetTrackerFrame(int xIn, int yIn, int widthIn, int heightIn) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(xIn, yIn, widthIn, heightIn);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        BudgetTrackerPanel panel = new BudgetTrackerPanel();
        setPanel(panel);
        contentPane.add(panel);


        setVisible(true);
    }

}