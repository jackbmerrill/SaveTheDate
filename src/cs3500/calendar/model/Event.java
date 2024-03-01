package cs3500.calendar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * To represent an event in an individual's schedule.
 */
public class Event implements IEvent {

  private String name;
  private Time time;
  private Location location;
  private List<String> users;

  /**
   * To represent a constructor for event.
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
    return null;
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
  public Object getTime() {
    return time;
  }
}
