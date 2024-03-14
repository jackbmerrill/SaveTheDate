package cs3500.calendar.model;

import java.util.Objects;

/**
 * A public class to represent a location. Could be online or in person,
 * and has an associated place.
 *
 * NOTE: possibly not final so host can change location
 */
public class Location {
  private final boolean online;
  private final String place;

  /**
   * Creates a new location.
   * @param online Boolean if the event is online or not
   * @param place A string for the desired location
   */
  public Location(boolean online, String place) {
    this.online = online;
    this.place = Objects.requireNonNull(place);
  }

  /**
   * Gives if the given location is online or not.
   * @return the private field online
   */
  public boolean isOnline() {
    return online;
  }

  /**
   * Gives the place of the location.
   * @return the private field place
   */
  public String getPlace() {
    return place;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (! (o instanceof Location)) {
      return false;
    }
    Location other = (Location)o;
    return Objects.equals(this.online, other.online)
            && Objects.equals(this.place, other.place);
  }
  @Override
  public int hashCode() {
    return Objects.hash(online, place);
  }
}
