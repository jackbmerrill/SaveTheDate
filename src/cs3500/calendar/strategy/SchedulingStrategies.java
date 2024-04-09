package cs3500.calendar.strategy;


import java.util.List;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;

public interface SchedulingStrategies {

  /**
   *
   * @param system
   * @param name
   * @param time
   * @param loc
   * @param users
   * @return an event at a time that works for all the users.
   * @throws IllegalStateException if there are no times that work
   */
  Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc, List<String> users);

}
