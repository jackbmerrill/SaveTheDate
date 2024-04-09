package cs3500.calendar.model;

import java.util.List;

public class WorkHoursSchedulingStrategy implements  SchedulingStrategies {
  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc, List<String> users) {
    for (Day day : Day.values()) {
      if (day == Day.SATURDAY || day == Day.SUNDAY) continue;

      for (int hour = 9; hour <= 16; hour++) {
        for (int min = 0; min <= (60 - time); min += time) {
          Time currTime = new Time(day, hour * 100 + min, day, (hour * 100 + min + time) % 2400);
          if (system.isTimeAvailableForUsers(currTime, users)) {
            return new Event(name, currTime, loc, users);
          }
        }
      }
    }
    return null;
  }
}
