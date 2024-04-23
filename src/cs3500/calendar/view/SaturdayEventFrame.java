package cs3500.calendar.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.IEvent;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.STime;

/**
 *
 * Implementation of an Event Frame starting on Saturday, see IEventFrame. This frame is unable to
 * be resized, and will in future assignments connect to the controller with the listeners. The host
 * is always at the top of the list of users and is already selected. When creating an
 * event frame, if no event is provided, all fields will be left blank. Otherwise, all
 * event details will be filled in. Upon closing, the code does not quit.
 */

public class SaturdayEventFrame extends EventFrame {

  public SaturdayEventFrame(ReadOnlyCentralSystem readOnlyCentralSystem, String host) {
    super(readOnlyCentralSystem, host);
  }
  /**
   * Event frame constructor that takes in a readOnlyCentralSystem and an event. Takes the
   * info from the provided event and fills in all the required details to all the different
   * panels.
   * @param readOnlyCentralSystem readOnlyCentralSystem
   * @param event the event to be edited
   */
  public SaturdayEventFrame(ReadOnlyCentralSystem readOnlyCentralSystem, IEvent event, String user) {
    super(readOnlyCentralSystem, event, user);
  }

  @Override
  protected Event makeEvent() {
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
              new STime((Day) startingDayDropdown.getSelectedItem(),
                      startTime, (Day) endingDayDropdown.getSelectedItem(), endTime),
              new Location(isOnline.isSelected(), locationTextBox.getText()),
              users);
      this.dispose();
      return event;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid time.");
    }
  }
}
