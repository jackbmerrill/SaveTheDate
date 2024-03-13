package cs3500.calendar.model;

import java.util.List;

/**
 * To represent a schedule for an individual user.
 */
public interface ISchedule {

  /**
   * Adds an event to the schedule.
   * @param event an event to be added to the schedule
   * @throws IllegalStateException if there is already an event at the same time
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
   * To modify the event name
   * @param eventName the event on the schedule name to be changed
   * @throws IllegalStateException if no such event exists
   */
  public void modifyEventName(String eventName, String name);

  /**
   * To modify the event time.
   * @param eventName an event name on the schedule to be changed
   * @throws IllegalStateException if no such event exists or event already exists at given time
   */
  public void modifyEventTime(String eventName, Time time);

  /**
   * To modify the event location.
   * @param eventName an event location to be changed on the schedule
   * @throws IllegalStateException if no such event exists
   */
  public void modifyEventLocation(String eventName, Location location);

  /**
   * To modify the event users.
   * @param eventName an event to modify users from the schedule
   * @throws IllegalStateException if no such event exists
   */
  public void modifyEventUsers(String eventName, List<String> users);

  /**
   * To return all the user of the event.
   * @param eventName an event to get users of
   *@return the event users of the requested event as a list of string
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

}
