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

import static org.junit.Assert.assertEquals;
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
  Time time10 = new Time(Day.SUNDAY, 0000, Day.SATURDAY, 2300);
  Time time11 = new Time(Day.TUESDAY, 900, Day.TUESDAY, 1400);


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
  CentralSystem central2 = new CentralSystem();
  CentralSystem central3 = new CentralSystem();

  SchedulingStrategies workhours = new WorkHoursSchedulingStrategy();

  @Test
  public void testWorkHoursSystem() {
    central1.generateEvent("event1", time9, loc2, list1);
    // to ensure that the work hours strategy throws when the calendar is completely full
    //assertThrows(IllegalArgumentException.class, () -> workhours.findTime(central1, "event10",
            //60, loc1, list4));

    central2.generateEvent("event1", time10, loc2, list1);
    // to ensure that the work hours strategy throws when the calendar is not full, but does not
    // have enough space to store the total time of the new event
    //assertThrows(IllegalStateException.class, () -> workhours.findTime(central2, "event10",
            //61, loc1, list4));

    // to ensure that the work hours strategy adds a new event at the earliest time given that
    // other event exist in the schedule
    central3.generateEvent("event1", time1, loc1, list1);
    central3.generateEvent("event2", time5, loc2, list1);
    central3.generateEvent("event3", time6, loc3, list1);
    assertEquals(new Event("event1", time11, loc1, list1),
            workhours.findTime(central3, "event11", 300, loc1, list1));
  }




}
