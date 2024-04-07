package cs3500.calendar.controller;

import java.util.List;

import cs3500.calendar.model.Event;

/**
 * The IFeatures
 */
public interface IFeatures {

  /**
   * Creates a new event in the model based on the inputs provided i
   * @param event
   */
  void createEvent(Event event);

  /**
   *
   * @param originalEvent
   * @param modifiedEvent
   */
  void modifyEvent(Event originalEvent, Event modifiedEvent);

  //this needs to be worked out how we are doing this.
  //ie strategy, users, time, etc

  void scheduleEvent();

  /**
   *
   * @param filePath
   */
  void loadXML(String filePath);

  /**
   *
   * @param filePath
   * @param userID
   */
  void saveXML(String filePath, String userID);

  /**
   *
   * @param event
   */
  void removeEvent(Event event);

}
