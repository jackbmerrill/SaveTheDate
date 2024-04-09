package cs3500.calendar.strategy;

import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.SchedulingStrategies;

public class AnyTimeSchedulingStrategies implements SchedulingStrategies {
  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int duration, Location loc,
                        List<String> users) {
    for (Day day : Day.values()) {
      for (int hour = 0; hour < 24; hour++) {
        for (int min = 0; min < 60; min++) {
          int endHour = hour + duration / 60;
          int endMin = min + duration % 60;
          if (endMin >= 60) {
            endHour++;
            endMin -= 60;
          }

          Day endDay = day;
          if (endHour >= 24) {
            endHour -= 24;
            int nextDayOrdinal = (day.ordinal() + 1) % Day.values().length;
            endDay = Day.values()[nextDayOrdinal];
          }

          if (endHour < 24 && !system.eventConflict(new Time(day, hour * 100 + min,
                  endDay, endHour * 100 + endMin), users)) {
            return new Event(name, new Time(day, hour * 100 + min, endDay,
                    endHour * 100 + endMin), loc, users);
          }
        }
      }
    }
    throw new IllegalStateException("Unable to schedule an event because " +
            "no suitable time was found.");
  }
}



