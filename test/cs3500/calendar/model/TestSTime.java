package cs3500.calendar.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * To test all public methods in the STime class.
 */
public class TestSTime {

  Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
  Time time2 = new Time(Day.SUNDAY, 1259, Day.TUESDAY, 1300);
  Time time3 = new Time(Day.TUESDAY, 1259, Day.THURSDAY, 1300);
  Time time4 = new Time(Day.TUESDAY, 1300, Day.FRIDAY, 2359);
  Time time5 = new Time(Day.SUNDAY, 1300, Day.SATURDAY, 800);

  // to test the constructor
  @Test
  public void testConstructor() {
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new STime(Day.MONDAY, -1, Day.TUESDAY, 0));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new STime(Day.MONDAY, 2400, Day.TUESDAY, 0));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new STime(Day.MONDAY, 2160, Day.TUESDAY, 0));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new STime(Day.MONDAY, 0, Day.TUESDAY, -10));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new STime(Day.MONDAY, 0, Day.TUESDAY, 2500));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new STime(Day.MONDAY, 0, Day.TUESDAY, 1390));
  }

  // to test the isOverlap method
  @Test
  public void testIsOverlap() {
    assertTrue(time2.isOverlap(time1));
    assertTrue(time1.isOverlap(time2));
    assertTrue(time3.isOverlap(time2));
    assertTrue(time2.isOverlap(time3));
    assertFalse(time4.isOverlap(time2));
    assertFalse(time2.isOverlap(time4));
    //check with an event that spans all week
    assertTrue(time5.isOverlap(time1));
    assertTrue(time1.isOverlap(time5));
    assertTrue(time2.isOverlap(time5));
    assertTrue(time5.isOverlap(time3));
  }
}
