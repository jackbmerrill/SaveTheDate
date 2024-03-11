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
   * @param schedule the attached schedule of the user
   */
  void addUser(String userId, Schedule schedule);

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
   * To update an event with the given details and modify it in the schedules of all
   * the specified users.
   * @param userID userID of the event
   * @param name the name of the event.
   * @param time the time of the event
   * @param location the location of the event
   * @param users the list of users connected to the event
   */
  void updateEvent(String userID, String name, Time time, Location location, List<String> users);

  /**
   * To remove an event with the given details and modify it in the schedules of all
   * the specified users. If user is the host, removes the event from
   * all of the schedules. If user is not the host, only removes from user schedule.
   * @param userID userID of the event
   * @param eventName name of the event to be removed
   */
  void removeEvent(String userID, String eventName);

  /**
   * To get the schedule that corresponds to the name of the given userID.
   * @param userId the unique id of the user
   * @return the given schedule for a user.
   */
  Schedule userSchedule(String userId);
}
