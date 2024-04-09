package cs3500.calendar.strategy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;

import static org.junit.Assert.assertThrows;

/**
 * To test all public methods for the strategy. ie: workhours and anyTime.
 */
public class TestStrategy {

  Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
  Time time2 = new Time(Day.SUNDAY, 1000, Day.SUNDAY, 1800);
  Time time3 = new Time(Day.TUESDAY, 1259, Day.THURSDAY, 1300);
  Time time4 = new Time(Day.TUESDAY, 1300, Day.FRIDAY, 2359);
  Time time5 = new Time(Day.WEDNESDAY, 1000, Day.WEDNESDAY, 1100);
  Time time6 = new Time(Day.THURSDAY, 800, Day.THURSDAY, 830);
  Time time7 = new Time(Day.THURSDAY, 1801, Day.THURSDAY, 1802);
  Time time8 = new Time(Day.THURSDAY, 2300, Day.THURSDAY, 2359);
  Time time9 = new Time(Day.SUNDAY, 0000, Day.SATURDAY, 2359);


  Location loc1 = new Location(true, "Churchill 101");
  Location loc2 = new Location(false, "Zoom");
  Location loc3 = new Location(false, "Jack's House");
  Location loc4 = new Location(false, "Milo's House");

  List<String> list1 = new ArrayList<>(List.of("4003"));
  List<String> list2 = new ArrayList<>(List.of("1023"));
  List<String> list3 = new ArrayList<>(Arrays.asList("3024", "1023", "4035"));
  List<String> list4 = new ArrayList<>(Arrays.asList("4035", "4035", "9604", "5046", "6755"));

  Event event1 = new Event("Event1", time1, loc1, list1);
  Event event2 = new Event("Event2", time2, loc2, list2);
  Event event3 = new Event("Event3", time3, loc3, list3);
  Event event4 = new Event("Event4", time4, loc4, list4);
  Event event5 = new Event("Event5", time5, loc4, list4);
  Event event6 = new Event("Even64", time6, loc4, list4);
  Event event7 = new Event("Event7", time7, loc4, list4);
  Event event8 = new Event("Event8", time8, loc4, list4);
  Event newEvent1 = new Event("NewEvent1", time1, loc1, list1);

  Schedule schedule1021 = new Schedule("1021");
  Schedule schedule5046 = new Schedule("5046");
  Schedule schedule1111 = new Schedule("1111");
  Schedule schedule0202 = new Schedule("0202");

  CentralSystem central1 = new CentralSystem();

  SchedulingStrategies workhours = new WorkHoursSchedulingStrategy();

  @Test
  public void testFullWorkHoursSystem() {
    central1.generateEvent("event1", time9, loc2, list1);
    // to ensure that the work hours
    assertThrows(IllegalArgumentException.class, () -> workhours.findTime(central1, "event10",
            60, loc1, list4));
  }


}