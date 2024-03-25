package cs3500.calendar.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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

  // to test the addUser method
  @Test
  public void testAddUser() {
    central1.addUser("9604");
    assertTrue(central1.getSystem().containsKey("9604"));
    Schedule schedule9604 = central1.getSystem().get("9604");
    assertTrue(schedule9604.getEventsAtTime(null).isEmpty());
    central1.generateEvent("Event4", time4, loc4, List.of("9604"));
    Schedule schedule = central1.getSystem().get("9604");
    assertEquals(event4, schedule.getEvent("Event4"));
  }

  // to test the generateEvent method
  @Test
  public void testGenerateEvent() {
    central1.addUser("4054");
    central1.addUser("3000");
    List<String> central1Users = Arrays.asList("4054", "3000");
    central1.generateEvent("Random Event", time1, loc2, central1Users);
    assertEquals(central1.getSystem().get("4054").getEvent("Random Event"),
            new Event("Random Event", time1, loc2, central1Users));
    assertEquals(central1.getSystem().get("3000").getEvent("Random Event"),
            new Event("Random Event", time1, loc2, central1Users));
    assertThrows(IllegalStateException.class, () -> central1.generateEvent("Milo",
            time1, loc1, central1Users));
  }

  // to test the updateEventName method
  @Test
  public void testUpdateEventName() {
    central1.addUser("user10");
    central1.generateEvent("Event1000", time2, loc2, List.of("user10"));
    central1.updateEventName("user10", "Event1000", "Event1001");
    assertEquals(central1.getSystem().get("user10").getEvent("Event1001"),
            new Event("Event1001", time2, loc2, List.of("user10")));
    assertThrows(IllegalStateException.class, () -> central1.getSystem().
            get("user10").getEvent("Event1000"));
    assertThrows(IllegalStateException.class, () -> central1.updateEventName("user10",
            "Not included event", "new name here"));
  }


  // to test the updateEventTime method
  @Test
  public void testUpdateEventTime() {
    central1.addUser("3043");
    central1.addUser("5460");
    List<String> centralUsers = List.of("3043", "5460");
    central1.generateEvent("Event100", time1, loc1, centralUsers);
    assertEquals(time1, central1.getSystem().get("3043").getEvent("Event100").getTime());
    central1.updateEventTime("3043", "Event100", time6);
    assertEquals(time6, central1.getSystem().get("3043").getEvent("Event100").getTime());
    assertEquals(time6, central1.getSystem().get("5460").getEvent("Event100").getTime());
    assertThrows(IllegalStateException.class, () -> central1.updateEventTime("Milo",
            "jedd", time1));
    assertThrows(IllegalStateException.class, () -> central1.updateEventTime("John",
            "Event4000", time1));
  }
  
  // to test the updateEventLocation method
  @Test
  public void testUpdateEventLocation() {
    central1.addUser("1000");
    central1.generateEvent("Event4000", time4, loc3, List.of("1000", "2000"));
    assertEquals(loc3, central1.getEventsAtTime("1000", time4).get(0).getLocation());
    assertEquals(loc3, central1.getEventsAtTime("2000", time4).get(0).getLocation());
    central1.updateEventLocation("1000", "Event4000", loc1);
    assertEquals(loc1, central1.getEventsAtTime("1000", time4).get(0).getLocation());
    assertEquals(loc1, central1.getEventsAtTime("2000", time4).get(0).getLocation());
    assertThrows(IllegalStateException.class, () -> central1.updateEventLocation("Milo",
            "jedd", loc1));
    assertThrows(IllegalStateException.class, () -> central1.updateEventLocation("John",
            "Event4000", loc1));
  }

  //to test the removeEvent method
  @Test
  public void testRemoveEvent() {
    central1.addUser("Milo");
    central1.addUser("Jack");
    central1.generateEvent("GoingToRemove", time3, loc3, List.of("Milo", "Jack"));
    central1.removeEvent("Jack", "GoingToRemove");
    assertTrue(central1.getSystem().get("Jack").getEventsAtTime(time3).isEmpty());
    assertEquals(new Event("GoingToRemove", time3, loc3, List.of("Milo", "Jack")),
            central1.getEventsAtTime("Milo",time3).get(0));
    central1.generateEvent("GoingToRemoveHost", time1, loc1, List.of("Milo", "Jack"));
    central1.removeEvent("Milo", "GoingToRemoveHost");
    assertTrue(central1.getEventsAtTime("Jack", time1).isEmpty());
    assertTrue(central1.getEventsAtTime("Milo", time1).isEmpty());
    assertThrows(IllegalStateException.class, () -> central1.removeEvent("Milo",
            "NON_EXISTENT"));
    assertThrows(IllegalStateException.class, () -> central1.removeEvent("John",
            "GoingToRemove"));
  }

  // to test the addEventToUser method
  @Test
  public void testAddEventToUser() {
    central1.addUser("Milo");
    central1.generateEvent("New Event", time6, loc4, List.of("Milo"));

    central1.addUser("Jack");
    central1.addEventToUser("Jack", "New Event");
    assertEquals(central1.getSystem().get("Jack").getEvent("New Event"),
            new Event("New Event", time6, loc4, List.of("Milo", "Jack")));
    assertThrows(IllegalStateException.class, () -> central1.addEventToUser("Milo",
            "NON_EXISTENT"));

  }

  // to test the getEventsAtTime method
  @Test
  public void testGetEventsAtTime() {
    central1.addUser("Dio");
    central1.generateEvent("Meet101", time5, loc1, List.of("Dio"));
    central1.generateEvent("Meet202", time8, loc3, List.of("Dio"));
    central1.generateEvent("Meet303", time7, loc3, List.of("Dio"));

    List<Event> meet101 = central1.getEventsAtTime("Dio", time5);
    List<Event> meet202And303 = central1.getEventsAtTime("Dio", time4);
    assertEquals(1, meet101.size());
    assertEquals(new Event("Meet101", time5, loc1, List.of("Dio")), meet101.get(0));
    assertEquals(3, meet202And303.size());
    assertEquals(new Event("Meet101", time5, loc1, List.of("Dio")), meet202And303.get(2));
    assertEquals(new Event("Meet202", time8, loc3, List.of("Dio")), meet202And303.get(1));
    assertEquals(new Event("Meet303", time7, loc3, List.of("Dio")), meet202And303.get(0));
    assertThrows(IllegalStateException.class, () -> central1.getEventsAtTime("John",
            time2));
  }

  // to test getSystem method
  @Test
  public void testGetSystem() {
    central1.addUser("Milo");
    central1.generateEvent("OOD Exam", time4, loc4, List.of("Milo"));
    Map<String, Schedule> systemTest = central1.getSystem();
    assertTrue(systemTest.containsKey("Milo"));
    assertEquals(systemTest.get("Milo").getEvent("OOD Exam"), new Event(
            "OOD Exam", time4, loc4, List.of("Milo")));
    assertThrows(IllegalStateException.class, () -> central1.removeEvent("John",
            "GoingToRemove"));
  }

  @Test
  public void testGetUsers() {
    assertEquals(new ArrayList<String>(), central1.getUsers());
    central1.addUser("Jack");
    central1.addUser("Milo");
    central1.addUser("Dio");
    assertEquals(new ArrayList<String>(Arrays.asList("Milo", "Dio", "Jack")),
            central1.getUsers());
  }

  @Test
  public void testEventConflict() {

  }
}
