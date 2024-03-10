package cs3500.calendar.model;

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

  Day(String day, int order) {
    this.day = day;
    this.order = order;
  }
  @Override
  public String toString() {
    return day;
  }

  public int order() {
    return order;
  }

}
