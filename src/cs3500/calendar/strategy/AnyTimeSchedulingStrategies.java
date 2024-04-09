package cs3500.calendar.strategy;

import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

/**
 * Represents a scheduling strategy that finds available time slots for events
 *  that fall within any time on the calendar. This means that this strategy finds the earliest time
 *  possible to schedule an event on the calendar.
 */
public class AnyTimeSchedulingStrategies implements SchedulingStrategies {

  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc,
                        List<String> users) {
    for (Day day : Day.values()) {
      for (int hr = 0; hr < 24; hr++) {
        for (int min = 0; min < 60; min++) {
          int endHr = hr + time / 60;
          int endMin = min + time % 60;

          if (endMin >= 60) {
            endHr++;
            endMin -= 60;
          }

          Day endDay = day;
          if (endHr >= 24) {
            endHr -= 24;
            int nextDay = (day.ordinal() + 1) % Day.values().length;
            endDay = Day.values()[nextDay];
          }

          if (endHr < 24 && !system.eventConflict(new Time(day, hr * 100 + min, endDay,
                  endHr * 100 + endMin), users)) {
            return new Event(name, new Time(day, hr * 100 + min, endDay,
                    endHr * 100 + endMin), loc, users);
          }
        }
      }
    }
    throw new IllegalStateException("No Suitable time was found");
  }
}



