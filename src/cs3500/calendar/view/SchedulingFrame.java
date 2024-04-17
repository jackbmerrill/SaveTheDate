package cs3500.calendar.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JComponent;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;

/**
 * Implementation of the scheduling frame. Implements the IEventFrame interface but
 * only has one button to schedule an event given the name, desired users, length of time, and
 * location. All fields must have information or an error box will be created. Sends the desired
 * information the controller via a callback.
 */
public class SchedulingFrame extends JFrame implements IEventFrame {
  private JTextField eventNameTextBox;
  private JCheckBox isOnline;
  private JTextField locationTextBox;
  private JTextField durationTextBox;
  private JList<String> listOfAvailableUsers;
  private ReadOnlyCentralSystem readOnlyCentralSystem;
  private JButton scheduleEventButton;
  private IFeatures controller;

  /**
   * SchedulingEvent Frame constructor that takes in a readOnlyCentralSystem. Takes the
   * info from the provided event and fills in all the required details to all the different
   * panels.
   *
   * @param readOnlyCentralSystem readOnlyCentralSystem
   */
  public SchedulingFrame(ReadOnlyCentralSystem readOnlyCentralSystem) {
    super("Schedule Event");
    this.readOnlyCentralSystem = readOnlyCentralSystem;
    setInitialComponents();
    setInitialLayout();
    pack();
    setLocationRelativeTo(null);
  }

  private void setInitialComponents() {
    eventNameTextBox = new JTextField(20);
    isOnline = new JCheckBox("Is Online");
    locationTextBox = new JTextField(10);
    durationTextBox = new JTextField(5);
    listOfAvailableUsers = new JList<>(new ArrayList<>(readOnlyCentralSystem.getUsers()).
            toArray(new String[0]));
    scheduleEventButton = new JButton("Schedule Event");
    scheduleEventButton.addActionListener(e -> {
      try {
        List<String> users = new ArrayList<>(listOfAvailableUsers.getSelectedValuesList());
        if (eventNameTextBox.getText().isEmpty() || locationTextBox.getText().isEmpty()
                || durationTextBox.getText().isEmpty() || users.isEmpty()) {
          new ErrorBox("Not all required information is provided.");
          return;
        }
        int duration = Integer.parseInt(durationTextBox.getText());
        Location location = new Location(isOnline.isSelected(), locationTextBox.getText());
        controller.scheduleEvent(eventNameTextBox.getText(), duration, location, users);
        this.dispose();
      } catch (NumberFormatException x) {
        new ErrorBox("Invalid time, enter in minutes.");
      }
    });
  }

  private void setInitialLayout() {
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(createSchedulingPanels("Event name: ", eventNameTextBox));
    add(createSchedulingPanels("Location: ", locationTextBox, isOnline));
    add(createSchedulingPanels("Duration in minutes: ", durationTextBox));
    add(createSchedulingPanels("Available users: ", new JScrollPane(listOfAvailableUsers)));
    add(scheduleEventButton);
  }

  private JPanel createSchedulingPanels(String title, JComponent... components) {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(title));
    for (JComponent component : components) {
      panel.add(component);
    }
    return panel;
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setFeature(IFeatures feature) {
    this.controller = feature;
  }
}
