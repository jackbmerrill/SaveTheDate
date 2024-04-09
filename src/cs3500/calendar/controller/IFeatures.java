package cs3500.calendar.controller;

import java.util.List;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;

/**
 * The IFeatures interface delegates all interactions between the model and the view
 * to prevent loose coupling. Acts as the intermediary of the system. Defines each action
 * available to the user in the view and the model to ensure the correct action is performed
 * without the user knowing how. All interactions are delegated to the model.
 */
public interface IFeatures {

  /**
   * Creates a new event in the model based on the inputs provided in the view. Passes in the
   * given event created in the view to the model. If there is a conflict or an issue with
   * creating the event in the model, the view displays an error box to the user.
   * @param event the event to be created in the model.
   */
  void createEvent(Event event);

  /**
   * Modifies an existing event in the model via the user input in the view. Takes in
   * the original event which will be modified and the desired new version of the event.
   * If there is a conflict or an issue with creating the event in the model,
   * the view displays an error box to the user.
   * @param originalEvent the original event to be modified
   * @param modifiedEvent the updated version of the event
   */
  void modifyEvent(Event originalEvent, Event modifiedEvent);


  /**
   * Schedules an event in the model at an available time for all users. This is done through
   * delegating the inputs to the provided strategy to the controller. If there is not a
   * given strategy for, an error box is created in the view.
   * @param name name of the event
   * @param time duration of the event as an integer in minutes
   * @param loc the location of the event
   * @param users the desired users of the event
   */
  void scheduleEvent(String name, int time, Location loc, List<String> users);

  /**
   * Load the desired file into the model. If for any reason it cannot be loaded,
   * an error box is created in the view. Delgates to the model
   * @param filePath the file path to the desired schedule XML.
   */
  void loadXML(String filePath);

  /**
   * Saves the desired users schedule to the given filepath. Delegates the info to the model
   * and if the xml cannot be saved or an error occurs, an error box is displayed in the view.
   * @param filePath the file path to save the XML.
   * @param userID the desired users schedule to save.
   */
  void saveXML(String filePath, String userID);

  /**
   * Removes the given event from the system. If the user removing the event is the host,
   * then the event is removed from all users. Otherwise, it is only removed from the current
   * users schedule.
   * @param event the event to be removed.
   * @param user the user schedule to remove the event
   */
  void removeEvent(Event event, String user);

}
