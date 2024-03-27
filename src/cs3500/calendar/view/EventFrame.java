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
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

/**
 * To represent a EventFrame which implements all the public methods from IEventFrame.
 * This class helps to describe what the event’s frame is capable of.
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

  /**
   * Event frame constructor that takes in only a readOnlyCentralSystem.
   * @param readOnlyCentralSystem readOnlyCentralSystem
   */
  public EventFrame(ReadOnlyCentralSystem readOnlyCentralSystem) {
    super();
    initialize(readOnlyCentralSystem);
  }

  /**
   * Event frame constructor that takes in a readOnlyCentralSystem and an event.
   * @param readOnlyCentralSystem readOnlyCentralSystem
   */
  public EventFrame(ReadOnlyCentralSystem readOnlyCentralSystem, Event event) {
    super();
    this.event = event;
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
    if (!users.isEmpty()) {
      users.remove(0);
    }
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
    createEventButton.addActionListener(e -> printEvent("Create Event"));
    JButton modifyEventButton = new JButton("Modify Event");
    modifyEventButton.addActionListener(e -> printEvent("Modify Event"));
    JButton removeEventButton = new JButton("Remove Event");
    removeEventButton.addActionListener(e -> printEvent("Remove Event"));
    buttonPanel.add(createEventButton);
    buttonPanel.add(modifyEventButton);
    buttonPanel.add(removeEventButton);
    add(buttonPanel);
  }

  private void printEvent(String title) {
    if (eventNameTextBox.getText().isEmpty() || locationTextBox.getText().isEmpty()
            || startingTimeTextBox.getText().isEmpty() || endingTimeTextBox.getText().isEmpty()) {
      System.out.println("\nNot all required information is provided");
      return;
    }
    System.out.println("\n" + title + ": \nEvent Name: " + eventNameTextBox.getText()
            + "\nLocation: \n\tName: " + locationTextBox.getText() + "\n\tOnline: "
        + isOnline.isSelected() + "\nTime \n\tStart Day: "
            + startingDayDropdown.getSelectedItem().toString() + "\n\tStarting Time: "
        + startingTimeTextBox.getText() +  "\n\tEnd Day: "
            + endingDayDropdown.getSelectedItem().toString() + "\n\tEnding Time: "
            + endingTimeTextBox.getText() + "\n Users: \t");
    for (String user : availableUserDropdown.getSelectedValuesList()) {
      System.out.print(user + " ");
    }
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }
}
