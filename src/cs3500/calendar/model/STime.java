package cs3500.calendar.model;

/**
 * New class implementation where the week starts on Saturday and ends on Friday. Enables the
 * creation of a Saturday based schedule.
 */
public class STime extends Time {

  /**
   * Creates a new object of time. Can range up to one week exactly. If invalid, throws
   * an error.
   *
   * @param startDay  startDay enum
   * @param startTime integer for the start time
   * @param endDay    endDay enum
   * @param endTime   integer for the end time
   * @throws IllegalArgumentException if not a valid time
   */
  public STime(Day startDay, int startTime, Day endDay, int endTime) {
    super(startDay, startTime, endDay, endTime);
  }


  @Override
  public boolean isOverlap(ITime other) {
    //compute minutes in order to calculate overlaps
    int thisStartTotalMins = modifiedOrder(this.getStartDay()) * 1440 + this.getStartTime();
    int thisEndTotalMins = modifiedOrder(this.getEndDay()) * 1440 + this.getEndTime()
            + (modifiedOrder(this.getEndDay()) < modifiedOrder(this.getStartDay()) ? 7 * 1440 : 0);
    int otherStartTotalMins = modifiedOrder(other.getStartDay()) * 1440 + other.getStartTime();
    int otherEndTotalMins = modifiedOrder(other.getEndDay()) * 1440 + other.getEndTime()
            + (modifiedOrder(other.getEndDay()) < modifiedOrder(other.getStartDay()) ? 7 * 1440 : 0);
    //actually check for overlaps
    return thisStartTotalMins < otherEndTotalMins && otherStartTotalMins < thisEndTotalMins
            || this.getEndDay().equals(other.getEndDay()) && this.getStartDay().equals(other.getStartDay())
            && ((this.getStartTime() < other.getStartTime() && other.getStartTime() < this.getEndTime())
            || (this.getStartTime() < other.getEndTime() && other.getEndTime() < this.getEndTime()));
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
