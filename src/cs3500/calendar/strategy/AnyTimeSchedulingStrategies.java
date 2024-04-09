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
  public Event findTime(ReadOnlyCentralSystem system, String name, int time, Location loc, List<String> users) {
    for (Day day : Day.values()) {
      for (int hour = 0; hour < 24; hour ++) {
        for (int min = 0; min < 60; min ++) {
          int startTime = hour * 100 + min;
          int endTime = startTime + time;
          int endHour = endTime / 100;
          int endMinute = endTime % 100;
          if (endMinute >= 60) {
            endHour += endMinute / 60;
            endMinute %= 60;
          }
          endTime = endHour * 100 + endMinute;

          Day endDay = day;
          if (endHour < 24) {
            endHour -= 24;
            endTime = endHour * 100 + endMinute;
            int nextDayIndex = (day.ordinal() + 1) % Day.values().length;
            endDay = Day.values()[nextDayIndex];
          }
          Time currTime = new Time(day, startTime, endDay, endTime);
          if (system.isTimeAvailableForUsers(currTime, users)) {
            return new Event(name, currTime, loc, users);
          }
        }
      }
    }
    return null;
  }
}
