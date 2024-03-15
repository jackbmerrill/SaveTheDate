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
   * To represent a constructor for event. There must be at least one user in an event
   * Contains all the information needed to create an event and cannot take in null fields. 
   * @param name name of the event
   * @param time time of the even
   * @param location location of the event
   * @param users list of users
   * @throws IllegalArgumentException if there are no users or any argument is null
   */
  public Event(String name, Time time, Location location, List<String> users) {
    nullCheck(name);
    nullCheck(time);
    nullCheck(location);
    nullCheck(users);
      this.name = name;
      this.time = time;
      this.location = location;
      if (users.isEmpty()) {
        throw new IllegalArgumentException("There cannot be no users");
      }
    this.users = new ArrayList<>(users);
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
    nullCheck(name);
    this.name = name;
  }

  private static void nullCheck(Object field) {
    if (field == null) {
      throw new IllegalArgumentException("Argument cannot be null");
    }
  }

  @Override
  public void updateTime(Time time) {
    nullCheck(time);
    this.time = time;
  }

  @Override
  public void updateLocation(Location loc) {
    nullCheck(loc);
    this.location = loc;
  }

  @Override
  public void updateUsers(List<String> users) {
    nullCheck(users);
    if (users.isEmpty()) {
      throw new IllegalArgumentException("There cannot be no users");
    }
    if (!users.get(0).equals(getHost())) {
      throw new IllegalArgumentException("Host cannot be changed.");
    }
    this.users = new ArrayList<>(users);
  }

  @Override
  public List<String> getUsers() {
    return new ArrayList<>(users);
  }

  @Override
  public String getHost() {
    return users.get(0);
  }

  @Override
  public Time getTime() {
    return time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)  {
      return true;
    }
    if (! (o instanceof Event)) {
      return false;
    }
    Event other = (Event)o;
    return Objects.equals(this.name, other.name)
            && Objects.equals(this.time, other.time)
            && Objects.equals(this.location, other.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, time, location, users);
  }
}
