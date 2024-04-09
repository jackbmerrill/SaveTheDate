package cs3500.calendar.strategy;

import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

/**
 * Represents a scheduling strategy that finds available time slots for events
 * that fall within the standard work hours. This means between 9:00am to 5:00pm Monday through
 * Friday.
 */
public class WorkHoursSchedulingStrategy implements SchedulingStrategies {

  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int time,
                        Location loc, List<String> users) {
    for (Day day : Day.values()) {
      if (day == Day.SUNDAY || day == Day.SATURDAY) continue;

      for (int hr = 9; hr < 17; hr++) {
        for (int min = 0; min < 60; min += 15) {
          int startMin = hr * 100 + min;
          int endHr = hr + time / 60;
          int endMin = min + time % 60;

          if (endMin >= 60) {
            endHr++;
            endMin -= 60;
          }

          if (endHr < 17 || (endHr == 17 && endMin == 0)) {
            Time currTime = new Time(day, startMin, day, endHr * 100 + endMin);
            if (!system.eventConflict(currTime, users)) {
              return new Event(name, currTime, loc, users);
            }
          }
        }
      }
    }
    throw new IllegalStateException("No suitable time was found.");
  }
}


