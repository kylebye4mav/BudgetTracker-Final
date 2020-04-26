import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author  Kyle Bye
 */
@SuppressWarnings("serial")
public class SignInFrame extends JFrame {

    private SignInPanel panel;

    public SignInPanel getPanel() {
        return panel;
    }

    public void setPanel(SignInPanel panelIn) {
        panel = panelIn;
    }

    public SignInFrame() {
        setPanel(new SignInPanel());
    }

}