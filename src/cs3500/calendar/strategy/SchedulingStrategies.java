package cs3500.calendar.strategy;


import java.util.List;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;

/**
 * To represent an interface that allows scheduling strategies within the central calendar system.
 * Classes that implement this interface are in charge of determining suitable time slots
 * for events.
 */
public interface SchedulingStrategies {

  /**
   * To find the earliest suitable time slot in the calendar that can accommodate all the included
   * users and schedule details. The method will return an event that fits these accommodations
   * with the given name, time, location, and list of included users.
   * @param system the central system
   * @param name the name of the event
   * @param time the time of the event
   * @param loc the location of the event
   * @param users the list of included users for the event
   * @return an event at a time that works for all the users.
   * @throws IllegalStateException if there are no times that work
   */
  Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc,
                 List<String> users);
}
