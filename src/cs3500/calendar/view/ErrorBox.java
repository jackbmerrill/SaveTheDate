package cs3500.calendar.view;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates and displays an error box with the desired message. Used to display issues
 * when they arise either within the view itself or within the model.
 */
public class ErrorBox extends JFrame {

  /**
   * Creates a new error box in the view and displays it to the user. Displays the provided
   * message, whether that is an error message or a simple reminder that there was an
   * invalid input handed to the view.
   * @param message the message to be displayed
   */
  public ErrorBox(String message) {
    super();
    JOptionPane.showMessageDialog(this, message,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

}
