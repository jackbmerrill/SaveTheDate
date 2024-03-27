package cs3500.calendar.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * To test all the public method in the day class.
 */
public class TestDay {

  // to test the get day method 
  @Test
  public void testGetDay() {
    assertEquals(Day.getDay(1), Day.SUNDAY);
    assertEquals(Day.getDay(2), Day.MONDAY);
    assertEquals(Day.getDay(3), Day.TUESDAY);
    assertEquals(Day.getDay(4), Day.WEDNESDAY);
    assertEquals(Day.getDay(5), Day.THURSDAY);
    assertEquals(Day.getDay(6), Day.FRIDAY);
    assertEquals(Day.getDay(7), Day.SATURDAY);
    assertThrows(IllegalArgumentException.class, () -> Day.getDay(10));
  }
}
