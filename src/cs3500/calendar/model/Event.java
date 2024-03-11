package cs3500.calendar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to represent an event in an individual's schedule. The first user in the list of users for
 * an event is the host of that event. An event includes a name for the event, a location and
 * whether the event is online or not. It includes the starting/ending day and time of the event. It
 * includes a list of users who are invited to the event and events can only span at most a week.
 * (Note: An event can have both a physical location and still be online)
 */
public class Event implements IEvent {

  private String name;
  private Time time;
  private Location location;
  private List<String> users;

  /**
   * To represent a constructor for event.
   * Contains all the information needed to create an event and cannot take in null fields. 
   * @param name name of the event
   * @param time time of the even
   * @param location location of the event
   * @param users list of users
   */
  public Event(String name, Time time, Location location, List<String> users) {

    this.name = Objects.requireNonNull(name);
    this.time = Objects.requireNonNull(time);
    this.location = Objects.requireNonNull(location);
    this.users = Objects.requireNonNull(users);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Location getLocation() {
    return this.location;
  }

  @Override
  public void updateName(String name) {
    this.name = Objects.requireNonNull(name);
  }

  @Override
  public void updateTime(Time time) {
    this.time = Objects.requireNonNull(time);
  }

  @Override
  public void updateLocation(Location loc) {
    this.location = Objects.requireNonNull(loc);
  }

  @Override
  public void updateUsers(List<String> users) {
    this.users = Objects.requireNonNull(users);
  }

  @Override
  public List<String> getUsers() {
    return new ArrayList<>(users);
  }

  @Override
  public String getHost() {
    if (!users.isEmpty()) {
      return users.get(0);
    }
    return "";
  }

  @Override
  public Time getTime() {
    return time;
  }
}
