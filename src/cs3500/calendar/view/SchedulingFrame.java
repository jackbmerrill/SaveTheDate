package cs3500.calendar.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;

/**
 * Implementation of a Scheduling Frame that is almost the same as the event frame.
 * The differences visually are as follows: Instead of places to take in the starting day/time and
 * ending day/time, you will have one place to take in the number of minutes representing the
 * duration of the event. Instead of having multiple buttons to create/modify/remove the event,
 * you will have one to simply schedule it.
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
