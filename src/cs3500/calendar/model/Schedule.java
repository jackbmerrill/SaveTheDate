package cs3500.calendar.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * To represent a user's schedule. Has a map of string to events, and the user id of the
 * schedule. Implements the ISchedule interface and all of its methods.
 */
public class Schedule implements ISchedule {

  private final Map<String, IEvent> eventMap;
  private final String userID;

  /**
   * To represent a constructor for a schedule.
   * @param userID The userID for this schedule as a string
   */
  public Schedule(String userID) {
    this.userID = userID;
    this.eventMap = new HashMap<>();
  }

  /**
   * To represent a constructor for a schedule that takes in a schedule.
   * Creates a copy of all of the events.
   * @param other other schedule
   */
  public Schedule(ISchedule other) {
    this.userID = other.getUserID();
    this.eventMap = new HashMap<>();
    for (IEvent event : other.getEventsAtTime(null)) {
      this.addEvent(event);
    }
  }

  @Override
  public void addEvent(IEvent event) throws IllegalStateException {
    Objects.requireNonNull(event);
    eventOverlap(null, event.getTime());
    eventMap.put(event.getName(), event);
  }

  @Override
  public void removeEvent(String eventName) {
    containsEvent(eventName);
    if (!this.userID.equals(getEvent(eventName).getHost())) {
      List<String> users = this.getAllEventUsers(eventName);
      users.remove(this.userID);
      this.eventMap.get(eventName).updateUsers(users);
    }
    this.eventMap.remove(eventName);
  }

  private void containsEvent(String eventName) throws IllegalStateException {
    if (!eventMap.containsKey(eventName)) {
      throw new IllegalStateException("No such event exists");
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
  public void modifyEventTime(String eventName, ITime time) {
    containsEvent(eventName);
    try {
      eventOverlap(eventName, time);
    } catch (IllegalStateException e) {
      if (this.userID.equals(eventMap.get(eventName).getHost())) {
        throw new IllegalStateException("The host has a time conflict");
      }
      //if the event has a time conflict, does not add it to schedule
      this.removeEvent(eventName);
      return;
    }
    this.eventMap.get(eventName).updateTime(time);
  }

  private void eventOverlap(String eventName, ITime time) throws IllegalStateException {
    for (IEvent event : eventMap.values()) {
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
    IEvent temp = this.eventMap.get(eventName);
    //we could do this diff and create a new constructor
    return new Event(temp.getName(), temp.getTime(), temp.getLocation(), temp.getUsers());
  }

  @Override
  public List<IEvent> getEventsAtTime(ITime time) {
    ArrayList<IEvent> events = new ArrayList<>();

    if (time == null) {
      events.addAll(eventMap.values());
      return events;
    }

    for (IEvent event : eventMap.values()) {
      if (event.getTime().isOverlap(time)) {
        events.add(event);
      }
    }

    return events;
  }

  @Override
  public Map<Day, List<IEvent>> getEventsByDay() {
    Map<Day, List<IEvent>> eventsByDay = new EnumMap<>(Day.class);

    //empty list for each day
    for (Day day : Day.values()) {
      eventsByDay.put(day, new ArrayList<>());
    }

    //check events for each day
    for (IEvent event : eventMap.values()) {
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
        eventsByDay.get(currentDay).add(getEvent(event.getName()));
      }
    }

    return eventsByDay;
  }

  @Override
  public String getUserID() {
    return userID;
  }

}


