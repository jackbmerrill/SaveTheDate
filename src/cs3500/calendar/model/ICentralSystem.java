package cs3500.calendar.model;

import java.util.List;

/**
 * Defines the core functionalities for managing users, their schedules and events for the
 * NU planner.
 */
public interface ICentralSystem {

  /**
   * To add a user to the central system with their schedule.
   * @param userId the unique id of the user
   */
  void addUser(String userId);

  /**
   * Generates a new event with the given details and adds it to the schedule of the
   * specified users.
   * @param name the name of the event
   * @param time the time of the event
   * @param location the location of the event
   * @param users the list of users connected to the event
   */
  void generateEvent(String name, Time time, Location location, List<String> users);

  /**
   * To update an event name and modify it in the schedules of all
   * the specified users. Updates the name of the event for every user with the event.
   * @param userID userID of the event
   * @param oldName the old name of the event
   * @param newName the new name of the event
   */
  void updateEventName(String userID, String oldName, String newName);

  /**
   * To update an event time and modify it in the schedules of all
   * the specified users. If the event time is moved to create a conflict for a user,
   * that user is then removed from the event. If the host has a time conflict, an error is thrown.
   * @param userID userID of the event
   * @param name the name of the event
   * @param newTime the new time of the event
   */
  void updateEventTime(String userID, String name, Time newTime);

  /**
   *  To update an event location and modify it in the schedules of all
   *  the specified users.
   * @param userID userID of the event
   * @param name the name of the event
   * @param newLocation the new location of the event
   */
  void updateEventLocation(String userID, String name, Location newLocation);

  /**
   * To remove an event with the given details and modify it in the schedules of all
   * the specified users. If user is the host, removes the event from
   * all the schedules. If user is not the host, only removes from user schedule.
   * @param userID userID of the event
   * @param eventName name of the event to be removed
   */
  void removeEvent(String userID, String eventName);

  /**
   * To add a user to an event with the given details and modify it in the schedules of all
   * the specified users. Adds the user to the events users.
   * @param userID userID of the event
   * @param eventName name of the event to be removed
   */
  void addEventToUser(String userID, String eventName);


  /**
   *
   * @param filePath
   */
  void loadSchedulesFromXML(String filePath);

  /**
   *
   * @param directoryPath
   */
  void saveSchedulesToXML(String directoryPath);

  /**
   * Returns all events at the given time. All events that are within this time fram are
   * returned.
   * @param time the desired time
   * @param userId the unique id of the user
   * @return list of events at this time
   */
  List<Event> getEventsAtTime(String userId, Time time);
}
