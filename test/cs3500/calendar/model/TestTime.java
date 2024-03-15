package cs3500.calendar.model;

import org.junit.Test;


import cs3500.calendar.model.Day;
import cs3500.calendar.model.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


/**
 * To test the public methods in the time class. Most important is overlap.
 */
public class TestTime {

  Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
  Time time2 = new Time(Day.SUNDAY, 1259, Day.TUESDAY, 1300);
  Time time3 = new Time(Day.TUESDAY, 1259, Day.THURSDAY, 1300);
  Time time4 = new Time(Day.TUESDAY, 1300, Day.FRIDAY, 2359);
  Time time5 = new Time(Day.SUNDAY, 1300, Day.SATURDAY, 800);


  @Test
  public void testConstructor() {
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new Time(Day.MONDAY, -1, Day.TUESDAY, 0));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new Time(Day.MONDAY, 2400, Day.TUESDAY, 0));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new Time(Day.MONDAY, 2160, Day.TUESDAY, 0));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new Time(Day.MONDAY, 0, Day.TUESDAY, -10));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new Time(Day.MONDAY, 0, Day.TUESDAY, 2500));
    assertThrows("Must enter a valid time", IllegalArgumentException.class, () ->
            new Time(Day.MONDAY, 0, Day.TUESDAY, 1390));
  }


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

  //tests for formatTime method
  @Test
  public void testFormatTime() {
    assertEquals("Should return time in HHMM format", "0900", Time.formatTime(900));
    assertEquals("Should return time in HHMM format with 00 at the start for a double digit input",
            "0015", Time.formatTime(15));
    assertEquals("Should return time in HHMM format for midnight", "0000", Time.formatTime(0));
    assertEquals("Should return time in HHMM format for max time", "2359", Time.formatTime(2359));
  }

  //tests for getStartTime and getEndTime methods
  @Test
  public void testGetStartTimeAndEndTime() {
    Time time = new Time(Day.MONDAY, 900, Day.MONDAY, 1700);
    assertEquals("Should return the start time", 900, time.getStartTime());
    assertEquals("Should return the end time", 1700, time.getEndTime());
  }

  //tests for getStartDay and getEndDay methods
  @Test
  public void testGetStartAndEndDay() {
    Time time = new Time(Day.TUESDAY, 1000, Day.WEDNESDAY, 1100);
    assertEquals("Should return the start day", Day.TUESDAY, time.getStartDay());
    assertEquals("Should return the end day", Day.WEDNESDAY, time.getEndDay());
  }
}
