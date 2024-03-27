package cs3500.calendar.view;

/**
 * A visual representation of an Event. Should display and enable the editing and removal
 * of an event and its details. Details include the title, location, time, and the
 * attending users. The host of the event should always appear at the top of the event
 * users.
 */
public interface IEventFrame {

  /**
   * Makes the view visible to the user. Shows itself, the current user's event frame.
   */
  void makeVisible();

}
