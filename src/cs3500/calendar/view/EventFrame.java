package cs3500.calendar.view;
import java.util.List;

import java.awt.*;

import javax.swing.*;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;

/**
 * To represent a EventFrame which implements all the public methods from IEventFrame.
 * This class helps to describe what the event’s frame is capable of.
 */
public class EventFrame extends JFrame implements IEventFrame {

  private JPanel eventNamePanel;
  private JTextField eventNameTextBox;
  private JPanel locationPanel;
  private JCheckBox isOnline;
  private JPanel timePanel;
  private JTextField StartingTimeTextBox;
  private JComboBox<Day> StartingDayDropdown;
  private JTextField EndingTimeTextBox;
  private JComboBox<Day> EndingDayDropdown;
  private JPanel userPanel;
  private JComboBox<String> availableUserDropdown;
  private CentralSystem centralSystem;
  private JPanel buttonPanel;
  private JButton modifyEventButton, removeEventButton;

  public EventFrame(CentralSystem centralSystem) {
    super();
    this.centralSystem = centralSystem;
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
    eventNamePanel = new JPanel();
    eventNamePanel.setBorder(BorderFactory.createTitledBorder("Event name:"));
    eventNameTextBox = new JTextField(20);
    eventNamePanel.add(eventNameTextBox);
    add(eventNamePanel);
  }

  private void setLocationPanel() {
    locationPanel = new JPanel();
    locationPanel.setBorder(BorderFactory.createTitledBorder("Location:"));
    isOnline = new JCheckBox("Is Online");
    locationPanel.add(isOnline);
    add(locationPanel);
  }

  private void setTimePanel() {
    timePanel = new JPanel();
    timePanel.setLayout(new GridLayout(4, 2, 5, 5));
    timePanel.setBorder(BorderFactory.createTitledBorder("Time:"));
    StartingDayDropdown = new JComboBox<>(Day.values());
    StartingTimeTextBox = new JTextField(5);
    EndingDayDropdown = new JComboBox<>(Day.values());
    EndingTimeTextBox = new JTextField(5);
    timePanel.add(new JLabel("Starting Day:"));
    timePanel.add(StartingDayDropdown);
    timePanel.add(new JLabel("Starting time:"));
    timePanel.add(StartingTimeTextBox);
    timePanel.add(new JLabel("Ending Day:"));
    timePanel.add(EndingDayDropdown);
    timePanel.add(new JLabel("Ending time:"));
    timePanel.add(EndingTimeTextBox);
    add(timePanel);
  }

  private void setUserPanel() {
    userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("Available users"));
    List<String> users = centralSystem.getUsers();
    availableUserDropdown = new JComboBox<>(users.toArray(new String[0]));
    userPanel.add(availableUserDropdown);
    add(userPanel);
  }

  private void setEventButtons() {
    buttonPanel = new JPanel();
    modifyEventButton = new JButton("Modify Event");
    removeEventButton = new JButton("Remove Event");
    buttonPanel.add(modifyEventButton);
    buttonPanel.add(removeEventButton);
    add(buttonPanel);
  }


  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void modifyEvent() {

  }

  @Override
  public void removeEvent() {

  }

  @Override
  public void closeWindow() {

  }

  @Override
  public void inputEvent(Event event) {

  }
}
