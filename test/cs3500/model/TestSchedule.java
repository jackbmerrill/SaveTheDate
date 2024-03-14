package cs3500.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * To test all public methods in the schedule class.
 */
public class TestSchedule {

  Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
  Time time2 = new Time(Day.SUNDAY, 1000, Day.SUNDAY, 1800);
  Time time3 = new Time(Day.TUESDAY, 1259, Day.THURSDAY, 1300);
  Time time4 = new Time(Day.TUESDAY, 1300, Day.FRIDAY, 2359);

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

  Schedule schedule1021 = new Schedule("1021");
  Schedule schedule5046 = new Schedule("5046");
  Schedule schedule1111 = new Schedule("1111");
  Schedule schedule0202 = new Schedule("0202");

  // to test the addEvent method
  @Test
  public void testAddEvent() {
    assertThrows(IllegalStateException.class, () -> schedule1111.getEvent("Event1"));
    schedule1111.addEvent(event1);
    schedule1111.addEvent(event2);
    assertEquals(schedule1111.getEvent("Event1"), event1);
    assertEquals(schedule1111.getEvent("Event2"), event2);
  }

  // to test the removeEvent method
  @Test
  public void testRemoveEvent() {
    schedule0202.addEvent(event3);
    //assertTrue(schedule0202.containsEvent("Event3"));
    schedule0202.removeEvent("Event3");
    //assertFalse(schedule0202.containsEvent("Event3"));
  }

  // to test the containsEvent method
  @Test
  public void testContainsEvent() {
    schedule1021.addEvent(event2);
    schedule1021.addEvent(event3);
    schedule1021.addEvent(event1);
    //assertTrue(schedule1021.containsEvent("Event2"));
    //assertTrue(schedule1021.containsEvent("Event3"));
    //assertTrue(schedule1021.containsEvent("Event1"));
    //assertFalse(schedule1021.containsEvent("Event4"));
  }

  // to test the modifyEventName method
  @Test
  public void testModifyEventName() {
    schedule0202.addEvent(event1);
    schedule0202.modifyEventName("Event1", "NewEvent1");
    //assertFalse(schedule0202.containsEvent("Event1"));
    //assertTrue(schedule0202.containsEvent("NewEvent1"));
  }

  // to test the modifyEventTime method
  @Test
  public void modifyEventTime() {
    schedule5046.addEvent(event2);
    assertEquals(schedule5046.getEvent("Event2").getTime(), time2);
    schedule5046.modifyEventTime("Event2", time4);
    schedule5046.addEvent(event3);
    assertEquals(schedule5046.getEvent("Event2").getTime(), time4);
    //schedule5046.modifyEventTime("Event3", time1);
    //assertEquals(schedule5046.getEvent("Event3").getTime(), time1);

  }

  // to test the modifyEventLocation method
  @Test
  public void modifyEventLocation() {
    schedule1111.addEvent(event2);
    assertEquals(schedule1111.getEvent("Event2").getLocation(), loc2);
    schedule1111.modifyEventLocation("Event2", loc3);
    assertEquals(schedule1111.getEvent("Event2").getLocation(), loc3);
    schedule1111.addEvent(event1);
    //assertEquals(schedule1111.getEvent("Event1").getLocation(), loc1);
    //schedule1111.modifyEventLocation("Event1", loc4);
    //assertEquals(schedule1111.getEvent("Event1").getLocation(), loc4);
  }

  // to test the getAllEventUsers method
  @Test
  public void testGetAllEventUsers() {
    schedule1021.addEvent(event4);
    assertEquals(schedule1021.getAllEventUsers("Event4"), list4);
    schedule1021.addEvent(event2);
    assertEquals(schedule1021.getAllEventUsers("Event2"), list2);
  }

  // to test the getEvent method
  @Test
  public void testGetEvent() {
    schedule5046.addEvent(event1);
    schedule5046.addEvent(event1);
    schedule5046.addEvent(event3);
    schedule5046.addEvent(event4);
  }
}


