package cs3500.calendar.model;

import java.util.List;
import java.util.Map;

/**
 * To represent a schedule for an individual user. Each schedule may consist of many events.
 * No events may overlap, but they may start and end at the same time. Each schedule tracks
 * the events for the given userID.
 */
public interface ISchedule {

  /**
   * Adds an event to the schedule. If there is a time conflict for the host, throws exception.
   * If there is a time conflict and the owner of the event is not the host, nothing is thrown
   * but the event is not added
   * @param event an event to be added to the schedule
   * @throws IllegalStateException if there is a time conflict
   */
  void addEvent(IEvent event) throws IllegalStateException;

  /**
   * To remove an event from the schedule. Removes the current user from the event
   * and removes the event from this schedule. If the host is the person removing,
   * it removes the event from the hosts schedule, but not from the event itself.
   * @param eventName an event to be removed from the schedule
   * @throws IllegalStateException if no such event exists in this schedule
   */
  void removeEvent(String eventName);

  /**
   * To modify the event name.
   * @param eventName the event on the schedule name to be changed
   * @throws IllegalStateException if no such event exists
   */
  void modifyEventName(String eventName, String name);

  /**
   * To modify the event time. If the event has a time conflict, it does not get added to the
   * schedule
   * @param eventName an event name on the schedule to be changed
   * @throws IllegalStateException if no such event exists
   */
  void modifyEventTime(String eventName, ITime time);

  /**
   * To modify the event location.
   * @param eventName an event location to be changed on the schedule
   * @throws IllegalStateException if no such event exists
   */
  void modifyEventLocation(String eventName, Location location);

  /**
   * To return all the user of the event.
   * @param eventName an event to get users of
   * @return the event users of the requested event as a list of string
   * @throws IllegalStateException if no such event exists
   */
  List<String> getAllEventUsers(String eventName);

  /**
   * Returns the requested event as a copy.
   * @param eventName the name of the desired event.
   * @return the requested event as a new object
   * @throws IllegalStateException if no such event exists
   */
  IEvent getEvent(String eventName);

  /**
   * To get a list of all the events at a given time.
   * Returns an empty list is no such events exist and returns all the events
   * if given null as a time
   * @param time the time
   * @return a list of all the events at the specified time
   */
  List<IEvent> getEventsAtTime(ITime time);

  /**
   * Gets a map of all the events ordered by day. Creates a list of events assigned to
   * a key of each day. Utilized for XML and view.
   * @return A map of a Day to a list of events
   */
  Map<Day, List<IEvent>> getEventsByDay();

  /**
   * Returns the user ID of the current schedule.
   * @return the user ID
   */
  String getUserID();
}
