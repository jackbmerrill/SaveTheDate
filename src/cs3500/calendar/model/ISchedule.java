package cs3500.calendar.model;

import java.util.List;
import java.util.Map;

/**
 * To represent a schedule for an individual user.
 */
public interface ISchedule {

  /**
   * Adds an event to the schedule. If there is a time conflict for the host, throws exception.
   * If there is a time conflict and the owner of the event is not the host, nothing is thrown
   * but the event is not added
   * @param event an event to be added to the schedule
   * @throws IllegalStateException if there is a time conflict
   */
  public void addEvent(Event event) throws IllegalStateException;

  /**
   * To remove an event from the schedule. Removes the current user from the event
   * and removes the event from this schedule.
   * @param eventName an event to be removed from the schedule
   * @throws IllegalStateException if no such event exists in this schedule
   */
  public void removeEvent(String eventName);

  /**
   * To modify the event name.
   * @param eventName the event on the schedule name to be changed
   * @throws IllegalStateException if no such event exists
   */
  public void modifyEventName(String eventName, String name);

  /**
   * To modify the event time. If the event has a time conflict, it does not get added to the
   * schedule
   * @param eventName an event name on the schedule to be changed
   * @throws IllegalStateException if no such event exists
   */
  public void modifyEventTime(String eventName, Time time);

  /**
   * To modify the event location.
   * @param eventName an event location to be changed on the schedule
   * @throws IllegalStateException if no such event exists
   */
  public void modifyEventLocation(String eventName, Location location);

  /**
   * To return all the user of the event.
   * @param eventName an event to get users of
   * @return the event users of the requested event as a list of string
   * @throws IllegalStateException if no such event exists
   */
  public List<String> getAllEventUsers(String eventName);

  /**
   * Returns the requested event as a copy.
   * @param eventName the name of the desired event.
   * @return the requested event as a new object
   * @throws IllegalStateException if no such event exists
   */
  public Event getEvent(String eventName);

  /**
   * To get a list of all the events at a given time.
   * Returns an empty list is no such events exist and returns all the events
   * if given null as a time
   * @param time the time
   * @return a list of all the events at the specified time
   */
  List<Event> getEventsAtTime(Time time);

  Map<Day, List<Event>> getEventsByDay();
}
