package cs3500.calendar.strategy;


import java.util.List;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;

public interface SchedulingStrategies {

  Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc, List<String> users);

}
