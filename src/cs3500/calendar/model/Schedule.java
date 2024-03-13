package cs3500.calendar.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * To represent a user's schedule.
 */
public class Schedule implements ISchedule {

  private final Map<String, Event> eventMap;
  private final String userID;

  /**
   * To represent a constructor for a schedule.
   */
  public Schedule(String userID) {
    this.userID = userID;
    this.eventMap = new HashMap<>();
  }

  @Override
  public void addEvent(Event event) throws IllegalStateException {
    Objects.requireNonNull(event);
    eventOverlap("", event.getTime());
    eventMap.put(event.getName(), event);
  }

  @Override
  public void removeEvent(String eventName) {
    containsEvent(eventName);
    List<String> users = this.getAllEventUsers(eventName);
    users.remove(this.userID);
    this.modifyEventUsers(eventName, users);
    this.eventMap.remove(eventName);
  }

  private void containsEvent(String eventName) throws IllegalStateException {
    if (!eventMap.containsKey(eventName)) {
      throw new IllegalStateException("No such element exists");
    }
  }

  @Override
  public void modifyEventName(String oldEventName, String newName) {
    containsEvent(oldEventName);
    this.eventMap.get(oldEventName).updateName(newName);
    this.eventMap.put(newName, this.eventMap.get(oldEventName));
    this.eventMap.remove(oldEventName);
  }

  //need to ignore the current event
  @Override
  public void modifyEventTime(String eventName, Time time) {
    containsEvent(eventName);
    eventOverlap(eventName, time);
    this.eventMap.get(eventName).updateTime(time);
  }

  private void eventOverlap(String eventName, Time time) {
    for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
      if (entry.getValue().getName().equals(eventName)) {
        continue;
      }
      //if overlap, throw error
      if (time.isOverlap(entry.getValue().getTime())) {
        throw new IllegalStateException("An event already exists at this time");
      }
    }
  }

  @Override
  public void modifyEventLocation(String eventName, Location location) {
    containsEvent(eventName);
    this.eventMap.get(eventName).updateLocation(location);
  }

  @Override
  public void modifyEventUsers(String eventName, List<String> users) {
    containsEvent(eventName);
    this.eventMap.get(eventName).updateUsers(users);
  }

  @Override
  public List<String> getAllEventUsers(String eventName) {
    containsEvent(eventName);
    return this.eventMap.get(eventName).getUsers();
  }

  @Override
  public Event getEvent(String eventName) {
    containsEvent(eventName);
    Event temp = this.eventMap.get(eventName);
    //we could do this diff and create a new constructor
    return new Event(temp.getName(), temp.getTime(), temp.getLocation(), temp.getUsers());
  }

}
