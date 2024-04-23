package cs3500.calendar.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

/**
 * Strategy for finding the first available time in a calendar, where Saturday is the first
 * day of the week. No default constructor, all work for the strategy is done via the findTime
 * method. If no such time can be found, an error is thrown.
 */
public class AnyTimeSchedulingStrategySaturday implements SchedulingStrategies {

  @Override
  public Event findTime(ReadOnlyCentralSystem system, String name, int time,
                        Location loc, List<String> users) {
    List<Day> saturdayFirst = new ArrayList<>(Arrays.asList(Day.SATURDAY, Day.SUNDAY, Day.MONDAY,
            Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY));
    System.out.println(saturdayFirst);
    for (Day day : saturdayFirst) {
      for (int hr = 0; hr < 24; hr++) {
        for (int min = 0; min < 60; min++) {
          if (modifiedOrder(day) * 1440 + hr * 60 + min + (time % 60) + (time / 60) * 60 > 16800) {
            break;
          }
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
            endDay = saturdayFirst.get(nextDay);
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

  private int modifiedOrder(Day day) {
    switch (day) {
      case SATURDAY:
        return 1;
      case SUNDAY:
        return 2;
      case MONDAY:
        return 3;
      case TUESDAY:
        return 4;
      case WEDNESDAY:
        return 5;
      case THURSDAY:
        return 6;
      case FRIDAY:
        return 7;
      default:
        throw new IllegalArgumentException("No valid day");
    }
  }
}
