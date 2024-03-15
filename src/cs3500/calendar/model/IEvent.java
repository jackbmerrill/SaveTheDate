package cs3500.calendar.model;

import java.util.List;

/**
 * To represent an Event interface.
 */
public interface IEvent {

  /**
   * Updates the name of the event.
   * @param name the name to be replaced
   * @throws IllegalArgumentException if argument is null
   */
  public void updateName(String name);

  /**
   * Updates the time of the event.
   * @param time the time to be replaced
   * @throws IllegalArgumentException if argument is null
   */
  public void updateTime(Time time);

  /**
   * Updates the location of the event.
   * @param loc the location to be replaced
   * @throws IllegalArgumentException if argument is null
   */
  public void updateLocation(Location loc);

  /**
   * Updates the users of the event. The first user is the event host.
   * The host cannot change
   * @param users the users to be updated
   * @throws IllegalArgumentException if the host has changed or list is empty or null
   */
  public void updateUsers(List<String> users);

  /**
   * Gives the users of the given event.
   * @return the list of users of event
   */
  public List<String> getUsers();


  /**
   * Returns the first user (the host) of the given event.
   * @return the name of the host of the event
   */
  public String getHost();

  /**
   * Gives the time object of the event.
   * @return the time object of event
   */
  public Time getTime();

  /**
   * Gets the name of the event.
   * @return the name of the event
   */
  public String getName();

  /**
   * Gives the location of the event.
   * @return the location object of the event
   */
  public Location getLocation();


}
