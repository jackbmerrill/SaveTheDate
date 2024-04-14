package cs3500.calendar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to represent an event in an individual's schedule. Has four fields, Name of the event,
 * Time of the event, location of the event, and a list of users. The first user is the host
 * and the rest of the users are the attendees.
 * INVARIANT:
 *         Within our code, Location and Time are invariants of the event class as they must
 *         always be present. There is no possible way to have an object of the event class without
 *         a time and a location.
 */
public class Event implements IEvent {

  private String name;
  private ITime time;
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
  public Event(String name, ITime time, Location location, List<String> users) {
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
  public void updateTime(ITime time) {
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
  public ITime getTime() {
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
    return Objects.hash(name, time, location);
  }
}
