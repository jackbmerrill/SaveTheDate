package cs3500.calendar.model;


import java.util.List;

public interface SchedulingStrategies {

  Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc, List<String> users);

}
