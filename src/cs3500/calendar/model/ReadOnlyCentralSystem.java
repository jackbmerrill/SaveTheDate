package cs3500.calendar.model;


import java.util.List;
import java.util.Map;

/**
 * To represent a read only central system that defines the core functionalities for managing users,
 * their schedules and events for the NU planner.
 */
public interface ReadOnlyCentralSystem {

  /**
   * Returns all events at the given time. All events that are within this time frame are
   * returned, empty is returned if no events exist.
   * @param time the desired time
   * @param userId the unique id of the user
   * @return list of events at this time, returns empty if no events
   * @throws IllegalStateException if user does not exist in system
   */
  List<IEvent> getEventsAtTime(String userId, Time time);

  /**
   * To get a copy of the central system.
   * @return a copy of the current state of the central system
   */
  Map<String, ISchedule> getSystem();


  /**
   * To get a copy of the given user's schedule.
   * @param userID the unique user id of the user
   * @return the schedule associated with the given userID
   */
  ISchedule getUserSchedule(String userID);

  /**
   * Returns a list of all userIds within the system to the user. Each user ID is a unique
   * string. Can be used to view all users.
   * @return A list of all users in the system
   */
  List<String> getUsers();


  /**
   * Does the given time have a conflict with any events exisitng in any of the users
   * schedules who are invited? Checks all users schedules for conflict. If so, return true,
   * if not return false.
   * @param time the time of the event in question
   * @param users the users who are invited to the event
   * @return True iff there is a conflict, otherwise returns false
   */
  boolean eventConflict(Time time, List<String> users);
}
