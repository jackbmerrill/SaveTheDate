package cs3500.calendar.model;

public class Time {
  private final int startTime;
  private final int endTime;
  private final Day startDay;
  private final Day endDay;


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
   * Checks if the given time overlaps with this time
   *
   * @param other the other time to check
   * @return true if the times overlap
   */
  //also check if works when next week
  //if this start > this end +7 to end
  public boolean isOverlap(Time other) {
    //same day
    if (this.startDay == other.startDay && this.endDay == other.endDay) {
      //double check logic
      return this.startTime <= other.startTime && this.endTime > other.startTime
              || this.startTime < other.endTime && this.endTime >= other.endTime
              || this.startTime >= other.startTime && this.endTime <= other.endTime;
      //if this starts when other ends, check this starts after other ends
    } else if (this.startDay == other.endDay) {
      return this.startTime < other.endTime;
      //if this ends when other starts, check this ends before other starts
    } else if (this.endDay == other.startDay) {
      return this.endTime > other.startTime;
    } else {
      /*uses order defined in the day class and sets them all to variables.
      if a start day comes before a end day, it means its over another week
      so add 7.
      then use that to compare.
      */
      int thisStartOrder = this.startDay.order();
      int otherStartOrder = other.startDay.order();
      int thisEndOrder = this.endDay.order();
      int otherEndOrder = other.endDay.order();
      if (thisStartOrder >= thisEndOrder) {
        thisEndOrder += 7;
      }
      if (otherStartOrder >= otherEndOrder) {
        otherEndOrder += 7;
      }
      return thisStartOrder < otherStartOrder
              && thisEndOrder > otherStartOrder
              || thisStartOrder < otherEndOrder
              && thisEndOrder > otherEndOrder
              || thisStartOrder > otherStartOrder
              && thisEndOrder < otherEndOrder;
    }
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

  public String getFormattedStartTime() {
    return String.format("%04d", this.startTime);
  }

}
