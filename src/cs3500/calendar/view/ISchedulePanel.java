package cs3500.calendar.view;


import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.ISchedule;

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
  void updateSchedule(ISchedule schedule);

  /**
   * Sets the feature for the controller to have access to the interactions between
   * the user and the view. Connects the controller to the panel for selecting existing
   * events from the panel.
   * @param feature the feature to be taken in
   */
  void setFeature(IFeatures feature);
}
