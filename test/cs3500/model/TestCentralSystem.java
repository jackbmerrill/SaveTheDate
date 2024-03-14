package cs3500.model;

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
import static org.junit.Assert.assertTrue;

/**
 * To test the public methods in the central system.
 */
public class TestCentralSystem {

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
  Event event5 = new Event("Event4", time5, loc4, list4);
  Event event6 = new Event("Event4", time6, loc4, list4);
  Event event7 = new Event("Event4", time7, loc4, list4);
  Event event8 = new Event("Event4", time8, loc4, list4);
  Event newEvent1 = new Event("NewEvent1", time1, loc1, list1);

  Schedule schedule1021 = new Schedule("1021");
  Schedule schedule5046 = new Schedule("5046");
  Schedule schedule1111 = new Schedule("1111");
  Schedule schedule0202 = new Schedule("0202");

  CentralSystem central1 = new CentralSystem();

  // to test the addUser method
  @Test
  public void testAddUser() {
    central1.addUser("9604");
    assertTrue(central1.getSystem().containsKey("9604"));
    central1.getSystem().get("9604").addEvent(event4);
    Schedule schedule9604 = central1.getSystem().get("9604");
    assertEquals(schedule9604.getEvent("Event4"), event4);
  }

  // to test the generateEvent method
  //  @Test
  //  public void testGenerateEvent() {
  //    central1.addUser("4054");
  //    central1.addUser("3000");
  //    List<String> central1Users = Arrays.asList("4054", "3000");
  //
  //    central1.generateEvent("Random Event", time1, loc2, central1Users);
  //  }

  // to test the updateEventName method
  //  @Test
  //  public void testUpdateEventName() {
  //    String name1 = "Name1";
  //    String name2 = "Name2";
  //
  //    central1.addUser("4045");
  //    central1.generateEvent(name1, time1, loc3, List.of("4045"));
  //    central1.updateEventName("4045", name1, name2);
  //  }

  // to test the updateEventTime method
  @Test
  public void testUpdateEventTime() {
    central1.addUser("3043");
    central1.addUser("5460");
    List<String> centralUsers = List.of("3043", "5460");
    central1.generateEvent("Event100", time1, loc1, centralUsers);

    central1.updateEventTime("3043", "Event100", time6);
    assertEquals(time6, central1.getSystem().get("3043").getEvent("Event100").getTime());
    assertEquals(time6, central1.getSystem().get("5460").getEvent("Event100").getTime());
  }

  // to test the updateEventLocation method
  @Test
  public void testUpdateEventLocation() {
    central1.addUser("1000");
    central1.generateEvent("Event4000", time4, loc3, List.of("1000"));

    central1.updateEventLocation("1000", "Event4000", loc1);
    assertEquals(loc1, central1.getSystem().get("1000").getEvent("Event4000"));

  }

  //to test the removeEvent method

  // to test the addEventToUser method

  // to test the getEventsAtTime method

  // to test getSystem method
}
