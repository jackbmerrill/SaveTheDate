package cs3500.calendar.model;

import java.util.Objects;

public class Time {
  private final int startTime;
  private final int endTime;
  private final Day startDay;
  private final Day endDay;


  public Time(Day startDay, int startTime, Day endDay, int endTime) {
    checkValidTime(startTime, endTime);
    if (startDay == endDay) {
      if (startTime > endTime) {
        throw new IllegalArgumentException("Event cannot begin after it ends");
      }
    }
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
  }
  private void checkValidTime(int st, int et) {
    if (st < 0 || et < 0 || st > 2359 || et > 2359 || st % 100 > 59 || et % 100 > 59) {
      throw new IllegalArgumentException("Must enter a valid time");
    }
  }

  /**
   * Checks if the given time overlaps with this time
   * @param time
   */
  public void isOverlap(Time time) {

  }

}
