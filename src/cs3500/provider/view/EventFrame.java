package cs3500.provider.view;

import cs3500.provider.controller.Features;
import cs3500.provider.model.ReadOnlyPlannerModel;
import cs3500.provider.model.Event;
import cs3500.provider.model.ReadOnlyPlannerModel;
import cs3500.provider.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * EventFrame provides a user interface for creating,
 * modifying, and viewing details of an event.
 * It extends JFrame and implements the PopUpFrame interface,
 * allowing for operations on Event objects
 * within a graphical interface. Users can input event details,
 * invite other users, and indicate whether
 * the event is online or in-person.
 */
public class EventFrame extends JFrame implements view.PopUpFrame {

  private CalendarUI view;
  private Features features;
  private Event event;
  private ReadOnlyPlannerModel model;

  private JTextField eventNameField;
  private JTextField locationField;
  private JCheckBox isOnlineCheckbox;
  private JComboBox<String> startingDayCombo;
  private JTextField startingTimeField;
  private JComboBox<String> endingDayCombo;
  private JTextField endingTimeField;

  /**
   * Constructs an EventFrame window either with
   * a pre-existing Event object for editing
   * or without one for creating a new event.
   *
   * @param event The event to edit, or null if creating a new event.
   */
  public EventFrame(Event event, ReadOnlyPlannerModel model, Features features, CalendarUI view) {
    this.event = event;
    this.model = model;
    this.features = features;
    this.view = view;
    createAndShowGUI();
  }

  private JScrollPane selectedUser() {
    JList<String> usersList = createUserList();
    usersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    return new JScrollPane(usersList);
  }

  private void createAndShowGUI() {
    setTitle("Event Details");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    createFields();

    JScrollPane usersScrollPane = selectedUser();

    JButton createEventButton = new JButton("Create event");
    JButton modifyEventButton = new JButton("Modify event");
    JButton removeEventButton = new JButton("Remove event");
    createEventButton.addActionListener(e -> {
      if (validateInput()) {
        JList<String> usersList = (JList<String>) usersScrollPane.getViewport().getView();
        List<User> selectedUsers = Arrays.stream(usersList
                .getSelectedValuesList().toArray(new String[0]))
            .map(name -> model.getUser(name))
            .collect(Collectors.toList());

        features.createEvent(
            eventNameField.getText(),
            locationField.getText(),
            isOnlineCheckbox.isSelected(),
            (String) startingDayCombo.getSelectedItem(),
            startingTimeField.getText(),
            (String) endingDayCombo.getSelectedItem(),
            endingTimeField.getText(),
            model.getCurrentUser(),
            selectedUsers
        );
        JOptionPane.showMessageDialog(this, "Event created successfully.");
      } else {
        JOptionPane.showMessageDialog(this, "Please fill in all event details before creating.");
      }
      view.updateView();

    });

    modifyEventButton.addActionListener(e -> {
      if (event != null && validateInput()) {
        Event newEvent = new Event(
            eventNameField.getText(),
            locationField.getText(),
            isOnlineCheckbox.isSelected(),
            (String) startingDayCombo.getSelectedItem(),
            startingTimeField.getText(),
            (String) endingDayCombo.getSelectedItem(),
            endingTimeField.getText(),
            model.getCurrentUser(),
            Arrays.asList() // Similar user list retrieval as in createEvent
        );
        features.modifyEvent(event, newEvent);
        JOptionPane.showMessageDialog(this, "Event modified successfully.");
      } else {
        JOptionPane.showMessageDialog(this, "Please fill in all event details before modifying.");
      }
      view.updateView();
    });


    removeEventButton.addActionListener(e -> {
      if (event != null) {
        features.removeEvent(event);
        JOptionPane.showMessageDialog(this, "Event removed successfully.");
      } else {
        JOptionPane.showMessageDialog(this, "No event to remove.");
      }
      view.updateView();

    });

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(createLabeledField("Event name:", eventNameField));
    add(createLabeledField("Location:", locationField));
    add(isOnlineCheckbox);
    add(createLabeledField("Starting Day:", startingDayCombo));
    add(createLabeledField("Starting time:", startingTimeField));
    add(createLabeledField("Ending Day:", endingDayCombo));
    add(createLabeledField("Ending time:", endingTimeField));
    add(new JLabel("Available users"));
    add(usersScrollPane);
    add(createEventButton);
    add(modifyEventButton);
    add(removeEventButton);
    pack();
  }

  private void createFields() {
    eventNameField = new JTextField(event != null ? event.getName() : "");
    locationField = new JTextField(event != null ? event.getLocation() : "");
    isOnlineCheckbox = new JCheckBox("Is online", event != null && event.getOnlineStatus());
    startingDayCombo = new JComboBox<>(new String[]
        {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
    startingDayCombo.setSelectedItem(event != null ? event.getStartDay() : "Sunday");
    startingTimeField = new JTextField(event != null ? event.getStartTime() : "");
    endingDayCombo = new JComboBox<>(new String[]
        {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
    endingDayCombo.setSelectedItem(event != null ? event.getEndDay() : "Sunday");
    endingTimeField = new JTextField(event != null ? event.getEndTime() : "");
  }

  private JList<String> createUserList() {
    String[] userListArray = event != null ? event.getInvitedUsers().stream()
        .map(User::getName).toArray(String[]::new) : new String[]{};
    JList<String> usersList = new JList<>(userListArray);
    return usersList;
  }

  private boolean validateInput() {
    // Validation logic remains the same
    return !eventNameField.getText().trim().isEmpty()
        && !locationField.getText().trim().isEmpty()
        && !startingTimeField.getText().trim().isEmpty()
        && !endingTimeField.getText().trim().isEmpty();
  }

  private JPanel createLabeledField(String labelText, JComponent field) {
    // This method remains the same
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    JLabel label = new JLabel(labelText);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    field.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(label);
    panel.add(field);
    return panel;
  }

  /**
   * Renders the EventFrame GUI, making it visible to the user.
   * This method is intended
   * to ensure that the GUI is displayed on the Event
   * Dispatch Thread (EDT) for thread safety.
   */
  public void render() {
    SwingUtilities.invokeLater(() -> {
      new EventFrame(event, model, features, view).setVisible(true);
    });
  }

}
