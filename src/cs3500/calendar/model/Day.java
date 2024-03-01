package cs3500.calendar.model;

public enum Day {
  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday"),
  SUNDAY("Sunday");

  private final String day;

  Day(String day) {
    this.day = day;
  }
  @Override
  public String toString() {
    return day;
  }

}
