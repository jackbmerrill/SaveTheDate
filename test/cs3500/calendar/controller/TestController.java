package cs3500.calendar.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.ICentralSystem;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.WorkHoursSchedulingStrategy;
import cs3500.calendar.view.ICentralSystemFrame;
import cs3500.calendar.view.MockView;

import static org.junit.Assert.assertEquals;

public class TestController {

  IFeatures controller;
  Appendable output;
  ICentralSystem model;
  MockView view;
  Time time1;
  Time time2;
  Event event1;

  @Before
  public void init() {
    this.output = new StringBuilder();
    this.model = new CentralSystem();
    this.view = new MockView(this.output);
    this.controller = new Controller(this.view, this.model, new WorkHoursSchedulingStrategy());
    this.time1 = new Time(Day.MONDAY, 0, Day.MONDAY, 400);
    this.time2 = new Time(Day.MONDAY, 600, Day.MONDAY, 800);
    event1 = new Event("Test1",
            time1,
            new Location(false, "somewhere"),
            new ArrayList<>(Arrays.asList("Jack", "Milo")));
  }

  @Test
  public void testCreateEvent() {
    this.view.createEvent(event1);
    assertEquals(this.model.getEventsAtTime("Jack", time1).get(0), event1);
    this.view.createEvent(event1);
    assertEquals("An event already exists at this time", this.output.toString());
  }

  @Test
  public void testModifyEvent() {
    this.view.createEvent(event1);
    Event event2 = new Event("Renamed", time2, new Location(true, "churchill"),
            new ArrayList<>(List.of("Jack")));
    assertEquals(this.model.getEventsAtTime("Jack", time1).get(0), event1);
    assertEquals(this.model.getEventsAtTime("Milo", time1).get(0), event1);
    this.view.modifyEvent(event1, event2);
    assertEquals(this.model.getEventsAtTime("Jack", time1), new ArrayList<>());
    assertEquals(this.model.getEventsAtTime("Jack", time2).get(0), event2);
    assertEquals(this.model.getEventsAtTime("Milo", time1), new ArrayList<>());
    assertEquals(this.model.getEventsAtTime("Milo", time2), new ArrayList<>());
    this.view.createEvent(event1);
    this.view.modifyEvent(event2, event1);
    assertEquals("An event already exists at this time", this.output.toString());
  }

  @Test
  public void removeEvent() {
    this.view.createEvent(event1);
    assertEquals(this.model.getEventsAtTime("Milo", time1).get(0), event1);
    assertEquals(this.model.getEventsAtTime("Jack", time1).get(0), event1);
    this.view.removeEvent(event1, "Milo");
    assertEquals(this.model.getEventsAtTime("Milo", time1), new ArrayList<>());
    assertEquals(this.model.getEventsAtTime("Jack", time1).get(0), event1);
    this.view.removeEvent(event1, "Milo");
    assertEquals("No such event exists", this.output.toString());
  }

  @Test
  public void testLoadXML() {

  }

  @Test
  public void testSaveXML() {

  }

  @Test
  public void testScheduleEvent() {

  }

}
