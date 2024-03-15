package cs3500.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;

import static org.junit.Assert.assertEquals;
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
  Time time5 = new Time(Day.WEDNESDAY, 1000, Day.WEDNESDAY, 1100);
  Time time6 = new Time(Day.THURSDAY, 800, Day.THURSDAY, 830);
  Time time7 = new Time(Day.THURSDAY, 1801, Day.THURSDAY, 1802);
  Time time8 = new Time(Day.THURSDAY, 2300, Day.THURSDAY, 2359);

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
  Event event6 = new Event("Event6", time6, loc4, list4);
  Event event7 = new Event("Event7", time7, loc4, list4);
  Event event8 = new Event("Event8", time8, loc4, list4);
  Event newEvent1 = new Event("NewEvent1", time1, loc1, list1);

  Schedule schedule1021;
  Schedule schedule5046;
  Schedule schedule1111;
  Schedule schedule0202;
  @Before
  public void initData() {
    schedule1021 = new Schedule("1021");
    schedule5046 = new Schedule("5046");
    schedule1111 = new Schedule("1111");
    schedule0202 = new Schedule("0202");
  }

  // to test the addEvent method
  @Test
  public void testAddEvent() {
    assertThrows(IllegalStateException.class, () -> schedule1111.getEvent("Event1"));
    schedule1111.addEvent(event1);
    schedule1111.addEvent(event2);
    schedule1111.addEvent(event3);
    assertEquals(schedule1111.getEvent("Event1"), event1);
    assertEquals(schedule1111.getEvent("Event2"), event2);
    assertEquals(schedule1111.getEvent("Event3"), event3);
    assertThrows(IllegalStateException.class, () -> schedule1111.addEvent(event4));
    assertThrows(IllegalStateException.class, () -> schedule1111.addEvent(event1));
  }

  // to test the removeEvent method
  @Test
  public void testRemoveEvent() {
    assertThrows(IllegalStateException.class, () -> schedule0202.removeEvent("Event10"));
    schedule0202.addEvent(event1);
    schedule0202.addEvent(event2);
    schedule0202.addEvent(event3);
    assertEquals(schedule0202.getEvent("Event1"), event1);
    assertEquals(schedule0202.getEvent("Event2"), event2);
    assertEquals(schedule0202.getEvent("Event3"), event3);
    schedule0202.removeEvent("Event2");
    schedule0202.removeEvent("Event3");
    assertEquals(schedule0202.getEvent("Event1"), event1);
    assertThrows(IllegalStateException.class, () -> schedule0202.getEvent("Event2"));
    assertThrows(IllegalStateException.class, () -> schedule0202.getEvent("Event3"));
  }

  // to test the modifyEventName method
  @Test
  public void testModifyEventName() {
    assertThrows(IllegalStateException.class, () -> schedule0202.modifyEventName(
            "No name", "other name"));
    schedule0202.addEvent(event1);
    schedule0202.modifyEventName("Event1", "NewEvent1");
    assertThrows(IllegalStateException.class, () -> schedule0202.getEvent("Event1"));
    assertEquals(schedule0202.getEvent("NewEvent1"), newEvent1);
  }

  // to test the modifyEventTime method
  @Test
  public void modifyEventTime() {
    assertThrows(IllegalStateException.class, () -> schedule5046.modifyEventTime(
            "No name", time2));
    schedule5046.addEvent(event2);
    schedule5046.addEvent(event4);
    assertEquals(schedule5046.getEvent("Event4").getTime(), time4);
    schedule5046.modifyEventTime("Event4", time1);
    assertEquals(schedule5046.getEvent("Event4").getTime(), time1);
  }

  // to test the modifyEventLocation method
  @Test
  public void modifyEventLocation() {
    assertThrows(IllegalStateException.class, () -> schedule1021.modifyEventLocation(
            "No name", loc2));
    schedule1021.addEvent(event1);
    assertEquals(schedule1021.getEvent("Event1").getLocation(), loc1);
    schedule1021.addEvent(event2);
    assertEquals(schedule1021.getEvent("Event2").getLocation(), loc2);
    schedule1021.modifyEventLocation("Event1", loc3);
    schedule1021.modifyEventLocation("Event2", loc3);
    assertEquals(schedule1021.getEvent("Event1").getLocation(), loc3);
    assertEquals(schedule1021.getEvent("Event2").getLocation(), loc3);
  }

  // to test the getAllEventUsers method
  @Test
  public void testGetAllEventUsers() {
    assertThrows(IllegalStateException.class, () -> schedule1111.getAllEventUsers(
            "Event1"));
    schedule1111.addEvent(event1);
    schedule1111.addEvent(event4);
    assertEquals(schedule1111.getAllEventUsers("Event1"), list1);
    assertEquals(schedule1111.getAllEventUsers("Event4"), list4);
  }

  // to test the getEvent method
  @Test
  public void testGetEvent() {
    assertThrows(IllegalStateException.class, () -> schedule1111.getEvent(
            "Event1"));
    schedule5046.addEvent(event1);
    schedule5046.addEvent(event2);
    schedule5046.addEvent(event3);
    assertEquals(schedule5046.getEvent("Event1"), event1);
    assertEquals(schedule5046.getEvent("Event2"), event2);
    assertEquals(schedule5046.getEvent("Event3"), event3);
  }

  // to test the getEventsAtTime method
  @Test
  public void testGetEventsAtTime() {
    assertTrue(schedule1021.getEventsAtTime(time1).isEmpty());
    schedule1021.addEvent(event1);
    assertEquals(event1, schedule1021.getEvent("Event1"));
    schedule1021.addEvent(event2);
    assertEquals(event2, schedule1021.getEvent("Event2"));
    schedule1021.addEvent(event5);
    assertEquals(event5, schedule1021.getEvent("Event5"));
    schedule1021.addEvent(event6);
    schedule1021.addEvent(event7);
    schedule1021.addEvent(event8);
    List<Event> eventsAtTime1 = Collections.singletonList(event1);
    assertEquals(schedule1021.getEventsAtTime(time1), eventsAtTime1);
    assertEquals(new ArrayList<>(Arrays.asList(event5, event6, event7, event8)),
            schedule1021.getEventsAtTime(time4));
    assertEquals(new ArrayList<>(Arrays.asList(event1, event2, event5, event6, event7, event8)),
            schedule1021.getEventsAtTime(null));
  }
}


