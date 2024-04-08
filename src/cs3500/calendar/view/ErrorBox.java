package cs3500.calendar.view;

import java.awt.*;

import javax.swing.*;

/**
 * Creates and displays an error box with the desired message. Used to display issues
 * when they arrise either within the view itself or within the model.
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
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(200, 150);
    setTitle("Message");
    setLayout(new BorderLayout());
    JLabel text = new JLabel(message);
    text.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(text, BorderLayout.CENTER);
    JButton close = new JButton("Close");
    close.addActionListener(e -> this.dispose());
    this.add(close, BorderLayout.SOUTH);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

}
