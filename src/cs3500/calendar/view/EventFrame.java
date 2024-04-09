package cs3500.calendar.view;

import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

/**
 * Implementation of an Event Frame, see IEventFrame. This frame is unable to be resized,
 * and will in future assignments connect to the controller with the listeners. The host
 * is always at the top of the list of users and is already selected. When creating an
 * event frame, if no event is provided, all fields will be left blank. Otherwise, all
 * event details will be filled in. Upon closing, the code does not quit.
 */
public class EventFrame extends JFrame implements IEventFrame {

  private JTextField eventNameTextBox;
  private JCheckBox isOnline;
  private JTextField locationTextBox;
  private JTextField startingTimeTextBox;
  private JComboBox<Day> startingDayDropdown;
  private JTextField endingTimeTextBox;
  private JComboBox<Day> endingDayDropdown;
  private JList<String> availableUserDropdown;
  private ReadOnlyCentralSystem readOnlyCentralSystem;
  private Event event;
  private String host;
  private String user;
  private IFeatures controller;

  /**
   * Event frame constructor that takes in only a readOnlyCentralSystem and the host of
   * the event. Initializes the event frame and creates all the required text boxes. Sets
   * the host of the event.
   * @param readOnlyCentralSystem readOnlyCentralSystem
   * @param host the host of the event
   */
  public EventFrame(ReadOnlyCentralSystem readOnlyCentralSystem, String host) {
    super();
    this.host = host;
    this.user = host;
    initialize(readOnlyCentralSystem);
  }

  /**
   * Event frame constructor that takes in a readOnlyCentralSystem and an event. Takes the
   * info from the provided event and fills in all the required details to all the different
   * panels.
   * @param readOnlyCentralSystem readOnlyCentralSystem
   * @param event the event to be edited
   */
  public EventFrame(ReadOnlyCentralSystem readOnlyCentralSystem, Event event, String user) {
    super();
    this.event = event;
    this.user = user;
    this.host = this.event.getHost();
    initialize(readOnlyCentralSystem);
    eventNameTextBox.setText(event.getName());
    Location loc = event.getLocation();
    isOnline.setSelected(loc.isOnline());
    locationTextBox.setText(loc.getPlace());
    Time time = event.getTime();
    startingDayDropdown.setSelectedItem(time.getStartDay());
    endingDayDropdown.setSelectedItem(time.getEndDay());
    startingTimeTextBox.setText(Integer.toString(time.getStartTime()));
    endingTimeTextBox.setText(Integer.toString(time.getEndTime()));
  }

  private void initialize(ReadOnlyCentralSystem readOnlyCentralSystem) {
    this.readOnlyCentralSystem = readOnlyCentralSystem;
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    setEventNamePanel();
    setLocationPanel();
    setTimePanel();
    setUserPanel();
    setEventButtons();
    pack();
    setLocationRelativeTo(null);
  }

  private void setEventNamePanel() {
    JPanel eventNamePanel = new JPanel();
    eventNamePanel.setBorder(BorderFactory.createTitledBorder("Event name:"));
    eventNameTextBox = new JTextField(20);
    eventNamePanel.add(eventNameTextBox);
    add(eventNamePanel);
  }

  private void setLocationPanel() {
    JPanel locationPanel = new JPanel();
    locationPanel.setBorder(BorderFactory.createTitledBorder("Location:"));
    isOnline = new JCheckBox("Is Online");
    locationTextBox = new JTextField(10);
    locationPanel.add(locationTextBox);
    locationPanel.add(isOnline);
    add(locationPanel);
  }

  private void setTimePanel() {
    JPanel timePanel = new JPanel();
    timePanel.setLayout(new GridLayout(4, 2, 5, 5));
    timePanel.setBorder(BorderFactory.createTitledBorder("Time:"));
    startingDayDropdown = new JComboBox<>(Day.values());
    startingTimeTextBox = new JTextField(5);
    endingDayDropdown = new JComboBox<>(Day.values());
    endingTimeTextBox = new JTextField(5);
    timePanel.add(new JLabel("Starting Day:"));
    timePanel.add(startingDayDropdown);
    timePanel.add(new JLabel("Starting time:"));
    timePanel.add(startingTimeTextBox);
    timePanel.add(new JLabel("Ending Day:"));
    timePanel.add(endingDayDropdown);
    timePanel.add(new JLabel("Ending time:"));
    timePanel.add(endingTimeTextBox);
    add(timePanel);
  }

  private void setUserPanel() {
    JPanel userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("Available users"));
    List<String> users = new ArrayList<>(readOnlyCentralSystem.getUsers());

    users.remove(this.host);
    users.add(0, this.host);

    availableUserDropdown = new JList<>(users.toArray(new String[0]));

    if (this.event != null) {
      List<String> eventUsers = event.getUsers();
      List<Integer> selectedIndices = new ArrayList<>();

      for (String user : eventUsers) {
        int index = users.indexOf(user);
        if (index != -1) {
          selectedIndices.add(index);
        }
      }

      int[] indices = selectedIndices.stream().mapToInt(i -> i).toArray();
      availableUserDropdown.setSelectedIndices(indices);
    } else {
      availableUserDropdown.setSelectedIndex(0);
    }

    JScrollPane scrollPane = new JScrollPane(availableUserDropdown);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    userPanel.add(scrollPane, BorderLayout.CENTER);
    add(userPanel);
  }

  private void setEventButtons() {
    JPanel buttonPanel = new JPanel();
    JButton createEventButton = new JButton("Create Event");
    createEventButton.addActionListener(e -> {
      try {
        controller.createEvent(makeEvent());
      } catch (IllegalArgumentException x) {
        new ErrorBox(x.getMessage());
      }
    });
    JButton modifyEventButton = new JButton("Modify Event");
    modifyEventButton.addActionListener(e -> {
      try {
        controller.modifyEvent(event, makeEvent());
      } catch (IllegalArgumentException x) {
        new ErrorBox(x.getMessage());
      }
    });
    JButton removeEventButton = new JButton("Remove Event");
    removeEventButton.addActionListener(e -> {
      controller.removeEvent(event, user);
      this.dispose();
    });
    buttonPanel.add(createEventButton);
    buttonPanel.add(modifyEventButton);
    buttonPanel.add(removeEventButton);
    add(buttonPanel);
  }

  private Event makeEvent() {
    if (eventNameTextBox.getText().isEmpty() || locationTextBox.getText().isEmpty()
            || startingTimeTextBox.getText().isEmpty() || endingTimeTextBox.getText().isEmpty()) {
      throw new IllegalArgumentException("Not all required information is provided.");
    }
    try {
      int startTime = Integer.parseInt(startingTimeTextBox.getText());
      int endTime = Integer.parseInt(endingTimeTextBox.getText());
      List<String> users = new ArrayList<>();
      users.addAll(availableUserDropdown.getSelectedValuesList());
      Event event = new Event(eventNameTextBox.getText(),
              new Time((Day) startingDayDropdown.getSelectedItem(),
              startTime, (Day) endingDayDropdown.getSelectedItem(), endTime),
              new Location(isOnline.isSelected(), locationTextBox.getText()),
              users);
      this.dispose();
      return event;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid time.");
    }
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
