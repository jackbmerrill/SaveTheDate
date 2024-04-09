package cs3500.calendar.strategy;

import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

public class WorkHoursSchedulingStrategy implements SchedulingStrategies {
  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int time,
                        Location loc, List<String> users) {
    for (Day day : Day.values()) {
      if (day == Day.SATURDAY || day == Day.SUNDAY) continue;
      for (int hour = 9; hour <= 16; hour++) {
        for (int min = 0; min <= (60 - time); min += time) {
          Time currTime = new Time(day, hour * 100 + min, day,
                  (hour * 100 + min + time) % 2400);
          if (! system.eventConflict(currTime, users)) {
            return new Event(name, currTime, loc, users);
          }
        }
      }
    }
    throw new IllegalStateException(
            "Unable to schedule an event because no suitable time was found.");
  }
}
