package cs3500.calendar.controller;

import java.util.List;

import cs3500.calendar.model.Event;

/**
 * The IFeatures interface delegates all interactions between the model and the view
 * to prevent loose coupling. Acts as the intermediary of the system. Defines each action
 * available to the user in the view and the model to ensure the correct action is performed
 * without the user knowing how.
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


  void scheduleEvent();

  /**
   * Load the desired file into the model.
   * @param filePath the file path to the desired schedule XML.
   */
  void loadXML(String filePath);

  /**
   * Saves the desired users schedule to the given filepath.
   * @param filePath the file path to save the XML.
   * @param userID the desired users schedule to save.
   */
  void saveXML(String filePath, String userID);

  /**
   * Removes the given event from the system. If the user removing the event is the hsot,
   * then the event is removed from all users. Otherwise, it is only removed from the current
   * users schedule.
   * @param event the event to be removed.
   * @param user the user schedule to remove the event
   */
  //TODD: do we also need to take in a user id to differentiate between who is removing the event.
  void removeEvent(Event event, String user);

}
