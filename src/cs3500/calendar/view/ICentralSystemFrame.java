package cs3500.calendar.view;

import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * An interface to describe the frame of the central system. The frame of the central system
 * consists of a central panel to represent the calendar, the ability to create and schedule
 * events, view different user schedules, and import and export xml files.
 */
public interface ICentralSystemFrame {

  /**
   * Makes the view visible to the user. Shows itself, the current user's event frame.
   */
  void makeVisible(boolean visible);

  /**
   * Sets the listener for the controller to have access to the interactions between
   * the user and the view. Will be fully implemented when the view is created.
   * @param listener the listener to be taken in
   */
  void setListener(ActionListener listener);

}
