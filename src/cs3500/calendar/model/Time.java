package cs3500.calendar.model;

/**
 * To represent time.
 */
public class Time {
  private final int startTime;
  private final int endTime;
  private final Day startDay;
  private final Day endDay;

  /**
   * JACK ADD HERE.
   * @param startDay startDay
   * @param startTime startTime
   * @param endDay endDay
   * @param endTime endTime
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
   * Checks if the given time overlaps with this time.
   * @param other the other time to check
   * @return true if the times overlap
   */
  //also check if works when next week
  //if this start > this end +7 to end
  public boolean isOverlap(Time other) {
    //start and end order for week comparison
    int thisStartOrder = this.startDay.order();
    int otherStartOrder = other.startDay.order();
    int thisEndOrder = this.endDay.order() + (this.endDay.compareTo(this.startDay) < 0 ? 7 : 0);
    int otherEndOrder = other.endDay.order() + (other.endDay.compareTo(other.startDay) < 0 ? 7 : 0);

    //convert times to minutes from start of week
    int thisStartTime = thisStartOrder * 24 * 60 + this.startTime / 100 * 60 + this.startTime % 100;
    int thisEndTime = thisEndOrder * 24 * 60 + this.endTime / 100 * 60 + this.endTime % 100;
    int otherStartTime = otherStartOrder * 24 * 60 + other.startTime /
            100 * 60 + other.startTime % 100;
    int otherEndTime = otherEndOrder * 24 * 60 + other.endTime / 100 * 60 + other.endTime % 100;

    //check for overlap
    return !(thisEndTime <= otherStartTime || thisStartTime >= otherEndTime);
  }

  //format integers to string
  public static String formatTime(int time) {
    return String.format("%04d", time);
  }

  public int getStartTime() {
    return this.startTime;
  }

  public int getEndTime() {
    return this.endTime;
  }

  public Day getStartDay() {
    return this.startDay;
  }

  public Day getEndDay() {
    return this.endDay;
  }

}
