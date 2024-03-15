package cs3500.calendar.model;

import org.junit.Test;


import cs3500.calendar.model.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the class for a location.
 */
public class TestLocation {

  Location loc1 = new Location(true, "Churchill 101");
  Location loc2 = new Location(false, "Zoom");

  @Test
  public void testIsOnline() {
    assertTrue(loc1.isOnline());
    assertFalse(loc2.isOnline());
  }

  @Test
  public void testGetPlace() {
    assertEquals("Churchill 101", loc1.getPlace());
    assertEquals("Zoom", loc2.getPlace());
  }
}
