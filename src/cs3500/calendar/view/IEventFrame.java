package cs3500.calendar.view;


import cs3500.calendar.controller.IFeatures;

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

  /**
   * Sets the feature for the controller to have access to the interactions between
   * the user and the view. Connects the controller to the panel for selecting existing
   * events from the panel.
   *
   * @param feature the feature to be taken in
   */
  void setFeature(IFeatures feature);
}
