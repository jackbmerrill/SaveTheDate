package cs3500.calendar.view;

import java.awt.event.ActionListener;

import cs3500.calendar.model.Schedule;

/**
 * Represents the schedule panel of the central system. Displays a users schedule and all of
 * their respective events on the respective days. The first column is Sunday and the last
 * is Saturday, where each horizontal line is an hour and the thicker lines are every four
 * hour interval. Clicking on an event opens the respective event frame.
 */
public interface ISchedulePanel {

  /**
   * Updates current schedule of the panel. Replaces the existing schedule with the
   * provided one as a parameter.
   * @param schedule the desired schedule to view.
   */
  void updateSchedule(Schedule schedule);

  /**
   * Sets the listener for the controller to have access to the interactions between
   * the user and the view. Will be fully implemented when the view is created.
   * @param listener the listener to be taken in
   */
  void setListener(ActionListener listener);
}
