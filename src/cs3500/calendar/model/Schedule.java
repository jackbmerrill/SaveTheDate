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

  /**
   * To represent a constructor for a schedule.
   */
  public Schedule() {
    this.eventMap = new HashMap<>();
  }

  @Override
  public void addEvent(Event event) throws IllegalStateException {
    Objects.requireNonNull(event);
    //we need to check no overlap in dates

    eventMap.put(event.getName(), event);
  }

  @Override
  public void removeEvent(String eventName) {
    containsEvent(eventName);
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

  @Override
  public void modifyEventTime(String eventName, Time time) {
    containsEvent(eventName);
    for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
      time.isOverlap(entry.getValue().getTime());
    }
    this.eventMap.get(eventName).updateTime(time);
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
}
