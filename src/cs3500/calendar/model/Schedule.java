package cs3500.calendar.model;

import java.util.ArrayList;
import java.util.EnumMap;
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
    try {
      eventOverlap(null, event.getTime());
    } catch (IllegalStateException e) {
      if (this.userID.equals(event.getHost())) {
        throw new IllegalStateException("Host has a time conflict");
      }
      List<String> users = event.getUsers();
      users.remove(this.userID); //check if causes issue
      event.updateUsers(users);
      return;
    }
    eventMap.put(event.getName(), event);
  }

  @Override
  public void removeEvent(String eventName) {
    containsEvent(eventName);
    List<String> users = this.getAllEventUsers(eventName);
    users.remove(this.userID);
    this.eventMap.get(eventName).updateUsers(users);
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
    try {
      eventOverlap(eventName, time);
    } catch (IllegalStateException e) {
      if (this.userID.equals(eventMap.get(eventName).getHost())) {
        throw new IllegalStateException("The host has a time conflict");
      }
      //if the event has a time conflict, dont add it to schedule and remove from list of users
      this.eventMap.remove(eventName);
      return;
    }
    this.eventMap.get(eventName).updateTime(time);
  }

  private void eventOverlap(String eventName, Time time) throws IllegalStateException {
    for (Event event : eventMap.values()) {
      //ignores current event
      if (event.getName().equals(eventName)) {
        continue;
      }
      //if overlap, throw error
      if (time.isOverlap(event.getTime())) {
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

  //return all events
  public Map<String, Event> getEvents() {
    return new HashMap<>(eventMap);
  }

  public Map<Day, List<Event>> getEventsByDay() {
    Map<Day, List<Event>> eventsByDay = new EnumMap<>(Day.class);

    //empty list for each day
    for (Day day : Day.values()) {
      eventsByDay.put(day, new ArrayList<>());
    }

    //check events for each day
    for (Event event : eventMap.values()) {
      Day startDay = event.getTime().getStartDay();
      Day endDay = event.getTime().getEndDay();

      //handle events spanning multiple days
      int startDayOrdinal = startDay.ordinal();
      int endDayOrdinal = endDay.ordinal();
      if (endDayOrdinal < startDayOrdinal) {
        endDayOrdinal += Day.values().length;
      }

      //add it to the respective list
      for (int ordinal = startDayOrdinal; ordinal <= endDayOrdinal; ordinal++) {
        Day currentDay = Day.values()[ordinal % Day.values().length];
        eventsByDay.get(currentDay).add(event);
      }
    }

    return eventsByDay;
  }
}


