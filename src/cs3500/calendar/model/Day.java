package cs3500.calendar.model;

/**
 * To represent a day of the week. Each day has an associated order starting with
 * Sunday at 1 and Saturday at 7, in the order of the week.
 */
public enum Day {
  MONDAY("Monday", 2),
  TUESDAY("Tuesday", 3),
  WEDNESDAY("Wednesday", 4),
  THURSDAY("Thursday", 5),
  FRIDAY("Friday", 6),
  SATURDAY("Saturday", 7),
  SUNDAY("Sunday", 1);

  private final String day;
  private final int order;

  /**
   * The constructor for the day enum. Takes in day and order.
   * @param day day of the week as a string
   * @param order order of the day as an int
   */
  Day(String day, int order) {
    this.day = day;
    this.order = order;
  }

  @Override
  public String toString() {
    return day;
  }

  /**
   * Returns the order of the day of the week. IE the order associated with each day.
   * @return int from 1 to 7
   */
  public int order() {
    return order;
  }

}
