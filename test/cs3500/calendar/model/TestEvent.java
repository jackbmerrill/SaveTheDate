package cs3500.calendar.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

/**
 * To test the event class.
 */
public class TestEvent {
  Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
  Time time2 = new Time(Day.SUNDAY, 1259, Day.TUESDAY, 1300);
  Time time3 = new Time(Day.TUESDAY, 1259, Day.THURSDAY, 1300);
  Time time4 = new Time(Day.TUESDAY, 1300, Day.FRIDAY, 2359);

  Location loc1 = new Location(true, "Churchill 101");
  Location loc2 = new Location(false, "Zoom");
  Location loc3 = new Location(false, "Jack's House");
  Location loc4 = new Location(false, "Milo's House");

  List<String> list1 = new ArrayList<>(List.of("1201"));
  List<String> list2 = new ArrayList<>(List.of("1023"));
  List<String> list3 = new ArrayList<>(Arrays.asList("3024", "1023", "4035"));
  List<String> list4 = new ArrayList<>(Arrays.asList("4035", "4035", "9604", "5046", "6755"));

  Event event1 = new Event("Event1", time1, loc1, list1);
  Event event2 = new Event("Event2", time2, loc2, list2);
  Event event3 = new Event("Event3", time3, loc3, list3);
  Event event4 = new Event("Event4", time4, loc4, list4);


  @Test
  public void testConstructor() {
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            new Event(null, time1, loc1, list1));
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            new Event("Event1", null, loc1, list1));
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            new Event("Event1", time1, null, list1));
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            new Event("event1", time1, loc1, null));
    assertThrows("There cannot be no users", IllegalArgumentException.class, () ->
            new Event("event1", time1, loc1, new ArrayList<>()));
  }

  // to test that the update name method works properly
  @Test
  public void testUpdateName() {
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            event1.updateName(null));
    event1.updateName("new name!!");
    assertEquals(event1.getName(), "new name!!");
    event1.updateName("extra new name");
    assertEquals(event1.getName(), "extra new name");
    event2.updateName("");
    assertEquals(event2.getName(), "");
  }

  // to test that the updateName method works properly
  @Test
  public void testUpdateTime() {
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            event1.updateTime(null));
    assertEquals(event3.getTime(), time3);
    event3.updateTime(time4);
    assertEquals(event3.getTime(), time4);
    assertEquals(event4.getTime(), time4);
    event4.updateTime(time1);
    assertEquals(event4.getTime(), time1);
  }

  // to test that the updateLocation method works properly
  @Test
  public void testUpdateLocation() {
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            event1.updateLocation(null));
    assertEquals(event3.getLocation(), loc3);
    event3.updateLocation(loc4);
    assertEquals(event3.getLocation(), loc4);
    assertEquals(event4.getLocation(), loc4);
    event4.updateLocation(loc1);
    assertEquals(event4.getLocation(), loc1);
  }

  // to test that the updateUsers method works properly
  @Test
  public void testUpdateUsers() {
    assertThrows("Argument cannot be null", IllegalArgumentException.class, () ->
            event1.updateUsers(null));
    assertEquals(event1.getUsers(), list1);
    assertThrows("Cannot change host", IllegalArgumentException.class, () ->
            event1.updateUsers(list4));
    assertEquals(event4.getUsers(), list4);
    event4.updateUsers(list4);
    assertEquals(event4.getUsers(), list4);
    assertEquals(event2.getUsers(), list2);
    List<String> list = new ArrayList<>(Arrays.asList("1023", "3024"));
    event2.updateUsers(list);
    assertEquals(event2.getUsers(), list);
  }

  // to test that the getUsers method works properly
  @Test
  public void testGetUsers() {
    assertEquals(event1.getUsers(), list1);
    assertEquals(event2.getUsers(), list2);
    assertEquals(event3.getUsers(), list3);
    assertEquals(event4.getUsers(), list4);
  }

  // to test that the getHost method works properly
  @Test
  public void testGetHost() {
    assertEquals(event1.getHost(), "1201");
    assertEquals(event2.getHost(), "1023");
    assertEquals(event3.getHost(), "3024");
    assertEquals(event4.getHost(), "4035");
  }

  // to test that the getTime method works properly
  @Test
  public void testGetTime() {
    assertEquals(event1.getTime(), time1);
    assertEquals(event2.getTime(), time2);
    assertEquals(event3.getTime(), time3);
    assertEquals(event4.getTime(), time4);
  }

  // to test that the getName method works properly
  @Test
  public void testGetName() {
    assertEquals(event1.getName(), "Event1");
    assertEquals(event2.getName(), "Event2");
    assertEquals(event3.getName(), "Event3");
    assertEquals(event4.getName(), "Event4");
  }

  // to test that the getLocation method works properly
  @Test
  public void testGetLocation() {
    assertEquals(event1.getLocation(), loc1);
    assertEquals(event2.getLocation(), loc2);
    assertEquals(event3.getLocation(), loc3);
    assertEquals(event4.getLocation(), loc4);
  }

  // to test overriding hashcode for event
  @Test
  public void testHashCode() {
    assertEquals(event1.hashCode(), event1.hashCode());
    assertNotEquals(event1.hashCode(), event4.hashCode());
  }
}
