package cs3500.calendar.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Defines the core functionalities for managing users, their schedules and events for the
 * NU planner. The central system holds access to all the schedules and is able to manipulate
 * them as desired by each user. Can add, remove, and modify events, as well as take in
 * an XML file and produce one of the schedule.
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
   * @throws IllegalArgumentException if there is a time conflict with an existing event
   *    for any users
   */
  void generateEvent(String name, Time time, Location location, List<String> users);

  /**
   * To update an event name and modify it in the schedules of all
   * the specified users. Updates the name of the event for every user with the event.
   * @param userID userID of the event
   * @param oldName the old name of the event
   * @param newName the new name of the event
   * @throws IllegalStateException if user or event does not exist in system
   */
  void updateEventName(String userID, String oldName, String newName);

  /**
   * To update an event time and modify it in the schedules of all
   * the specified users. If the event time is moved to create a conflict for a user,
   * that user is then removed from the event. If the host has a time conflict, an error is thrown.
   * @param userID userID of the event
   * @param name the name of the event
   * @param newTime the new time of the event
   * @throws IllegalStateException if user or event does not exist in system
   */
  void updateEventTime(String userID, String name, Time newTime);

  /**
   *  To update an event location and modify it in the schedules of all
   *  the specified users.
   * @param userID userID of the event
   * @param name the name of the event
   * @param newLocation the new location of the event
   * @throws IllegalStateException if user or event does not exist in system
   */
  void updateEventLocation(String userID, String name, Location newLocation);


  /**
   * To remove an event with the given details and modify it in the schedules of all
   * the specified users. If user is the host, removes the event from
   * all the schedules. If user is not the host, only removes from user schedule.
   * @param userID userID of the event
   * @param eventName name of the event to be removed
   * @throws IllegalStateException if user or event does not exist in system
   */
  void removeEvent(String userID, String eventName);

  /**
   * To add a user to an event with the given details and modify it in the schedules of all
   * the specified users. Adds the user to the events users.
   * @param userID userID of the event
   * @param eventName name of the event to be removed
   * @throws IllegalStateException if user or event does not exist in system
   */
  void addEventToUser(String userID, String eventName);


  /**
   * Load schedules from an XML file and update the CentralSystem with its data.
   * Read schedules from the specific XML file and use them to update or add schedules
   * for users based on what the XML files contain. If the file does not exist then throw an
   * IOException.
   * @param filePath filePath
   * @throws IOException for non-existent file.
   */
  void loadSchedulesFromXML(String filePath) throws IOException;

  /**
   * Saves the schedules of said users to XML files in the given directory of a specified
   * requested user. Iterate through the list in order to pick the requested userID. An IOException
   * is thrown in the case that there is an error in the process such as the given directory
   * not existing.
   * @param directoryPath directoryPath where the XML files are saved
   * @param userIDs The list of userIDs of users to save their schedules
   * @throws IOException If an error occurs during the process of saving the files or user does
   * not exist in the system.
   */
  void saveSchedulesToXML(String directoryPath, List<String> userIDs) throws IOException;


  /**
   * Returns all events at the given time. All events that are within this time frame are
   * returned, empty is returned if no events exist.
   * @param time the desired time
   * @param userId the unique id of the user
   * @return list of events at this time, returns empty if no events
   */
  List<Event> getEventsAtTime(String userId, Time time);

  /**
   * To get a copy of the central system.
   * @return a copy of the current state of the central system
   */
  Map<String, Schedule> getSystem();
}
