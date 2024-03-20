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
  List<Event> getEventsAtTime(String userId, Time time);

  /**
   * To get a copy of the central system.
   * @return a copy of the current state of the central system
   */
  Map<String, Schedule> getSystem();


  /**
   * To get a copy of the given user's schedule
   * @param userID the unique user id of the user
   * @return the schedule associated with the given userID
   */
  Schedule getUserSchedule(String userID);

  /**
   * To get a copy of all the users in the central system
   * @return a list of all the users in the central system
   */
  List<String> getAllUsers();


  /**
   * Does the given event have a conflict with any events exisitng in any of the users
   * schedules who are invited? If so, return true, if not return false.
   * @param event the event in question
   * @return True iff no conflict
   */
  boolean EventConflict(Event event);
}
