package cs3500.calendar.strategy;

import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

public class WorkHoursSchedulingStrategy implements SchedulingStrategies {
  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int duration,
                        Location loc, List<String> users) {
    for (Day day : Day.values()) {
      if (day == Day.SATURDAY || day == Day.SUNDAY) continue;

      for (int hour = 9; hour < 17; hour++) {
        for (int min = 0; min < 60; min += 15) {
          int startMin = hour * 100 + min;
          int endHour = hour + duration / 60;
          int endMin = min + duration % 60;

          if (endMin >= 60) {
            endHour++;
            endMin -= 60;
          }

          // Ensure the event ends within work hours (before 5:00 PM)
          if (endHour < 17 || (endHour == 17 && endMin == 0)) {
            Time potentialTime = new Time(day, startMin, day, endHour * 100 + endMin);
            if (!system.eventConflict(potentialTime, users)) {
              return new Event(name, potentialTime, loc, users);
            }
          }
        }
      }
    }
    throw new IllegalStateException("Unable to schedule an event because no suitable time was found.");
  }
}

