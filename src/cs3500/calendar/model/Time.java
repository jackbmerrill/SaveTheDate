package cs3500.calendar.model;

import java.util.Objects;

/**
 * To represent time. Has a start time, end time, start day, end day. Each time can last up
 * to exactly a week. Event cannot start and end at the same time.
 */
public class Time implements ITime {
  private final int startTime;
  private final int endTime;
  private final Day startDay;
  private final Day endDay;

  /**
   * Creates a new object of time. Can range up to one week exactly. If invalid, throws
   * an error.
   * @param startDay startDay enum
   * @param startTime integer for the start time
   * @param endDay endDay enum
   * @param endTime integer for the end time
   * @throws IllegalArgumentException if not a valid time
   */
  public Time(Day startDay, int startTime, Day endDay, int endTime) {
    checkValidTime(startTime, endTime);
    if (startDay.equals(endDay)) {
      if (startTime == endTime) {
        throw new IllegalArgumentException("Must enter a valid time");
      }
    }
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  private void checkValidTime(int st, int et) {
    //check start comes before end
    if (st < 0 || et < 0 || st > 2359 || et > 2359 || st % 100 > 59 || et % 100 > 59) {
      throw new IllegalArgumentException("Must enter a valid time");
    }
  }

  /**
   * Checks if the given time overlaps with this time. Returns true if there is an overlap,
   * false if there is not.
   * @param other the other time to check
   * @return true if the times overlap
   */
  public boolean isOverlap(ITime other) {
    //compute minutes in order to calculate overlaps
    int thisStartTotalMins = this.startDay.order() * 1440 + this.startTime;
    int thisEndTotalMins = this.endDay.order() * 1440 + this.endTime
            + (this.endDay.order() < this.startDay.order() ? 7 * 1440 : 0);
    int otherStartTotalMins = other.getStartDay().order() * 1440 + other.getStartTime();
    int otherEndTotalMins = other.getEndDay().order() * 1440 + other.getEndTime()
            + (other.getEndDay().order() < other.getStartDay().order() ? 7 * 1440 : 0);
    //actually check for overlaps
    return thisStartTotalMins < otherEndTotalMins && otherStartTotalMins < thisEndTotalMins
            || this.endDay.equals(other.getEndDay()) && this.startDay.equals(other.getStartDay())
            && ((this.startTime < other.getStartTime() && other.getStartTime() < this.endTime)
            || (this.startTime < other.getEndTime() && other.getEndTime() < this.endTime));
  }


  /**
   * To format the time in a readable XML format.
   * @param time the time.
   * @return formats the time in a readable XML format.
   */
  public static String formatTime(int time) {
    return String.format("%04d", time);
  }

  /**
   * Gets the starting time of the time. Used for the XML and the view.
    * @return an integer representation of the start time
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Gets the ending time of the time. Used for the XML and the view.
   * @return an integer representation of the end time
   */
  public int getEndTime() {
    return this.endTime;
  }

  /**
   * Gets the start day of the time. Used for the XML and the view.
   * @return an enum representing the start day
   */
  public Day getStartDay() {
    return this.startDay;
  }

  /**
   * Gets the end day of the time. Used for the XML and the view.
   * @return an enum representing the end day
   */
  public Day getEndDay() {
    return this.endDay;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)  {
      return true;
    }
    if (! (o instanceof Time)) {
      return false;
    }
    Time other = (Time)o;
    return Objects.equals(this.startTime, other.startTime)
            && Objects.equals(this.endTime, other.endTime)
            && Objects.equals(this.startDay, other.startDay)
            && Objects.equals(this.endDay, other.endDay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, endTime, startDay, endDay);
  }

}
