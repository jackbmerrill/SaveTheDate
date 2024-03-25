package cs3500.calendar.view;

/**
 * To describe what the event’s frame is capable of.
 */
public interface IEventFrame {

  /**
   * Once all the information is given to the frame for an event, clicking on the "Create"
   * button should print a message (using System.out) with the words "Create event:" and containing
   * all the details the user entered in the frame. Note that the user of the currently selected schedule
   * would be the host of the event.
   * Clicking "Remove event" should print a message with the words "Remove event:" and containing the
   * details of the event to remove, including the user you are removing it from. Note that would be the user of the currently selected schedule.
   * Clicking on the red button to close the window should DISPOSE the window. In other words, the
   * program does not exit, but the window still closes and nothing else happens.
   * Clicking on either the "Create" button or "Remove" button without all the information should print
   * a message stating some kind of error. What that error message contains is up to you.
   */


  /**
   * Makes the view visible to the user. Shows itself, the current user's event frame.
   */
  void makeVisible();

  /**
   * Signal the view to draw itself and updates the view.
   */
  void refresh();


  /**
   * To modify the given event.
   */
  void modifyEvent();

  /**
   * To remove the event from the corresponding user schedule.
   */
  void removeEvent();

  /**
   * To close the window, but does not stop the program. All info that is not saved
   * is discarded and forgotten.
   */
  void closeWindow();


  // printing create event details when clicking on the create event button

  // printing the remove event details when clicking on the remove event button

  // dispose the window when clicking on the red button (program does not exit, but the window closes

  // not a method, feature when clocking on event/remove event without all the information prints an error message

}
