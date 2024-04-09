package cs3500.calendar.controller;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.ICentralSystem;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.MockModelIOFail;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.WorkHoursSchedulingStrategy;
import cs3500.calendar.view.MockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class designed to test that the inputs from the controller actually modify the view.
 * It also tests for the exceptions thrown creating error boxes in the view.
 */
public class TestControllerModifiesModel {

  IFeatures controller;
  Appendable output;
  ICentralSystem model;
  MockView view;
  Time time1;
  Time time2;
  Event event1;
  IFeatures failureController;

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
    failureController = new Controller(new MockView(this.output), new MockModelIOFail(),
            null);
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
    Time time1 = new Time(Day.TUESDAY, 950, Day.TUESDAY, 1130);
    String filePath = "src/testSchedule.xml";
    this.view.loadXML(filePath);
    List<Event> events = model.getEventsAtTime("Prof. Lucia", null);
    assertEquals(3, events.size());
    Event morningLecture = model.getEventsAtTime("Prof. Lucia", time1).get(0);
    assertEquals("Churchill Hall 101", morningLecture.getLocation().getPlace());
    assertFalse(morningLecture.getLocation().isOnline());
    assertTrue(morningLecture.getUsers().contains("Prof. Lucia"));
    assertTrue(morningLecture.getUsers().contains("Student Anon"));
    assertTrue(morningLecture.getUsers().contains("Chat"));
    String formattedStartTime = Time.formatTime(morningLecture.getTime().getStartTime());
    assertEquals("0950", formattedStartTime);
  }

  @Test
  public void testSaveXML() {
    String eventName = "Sample Event";
    Time eventTime = new Time(Day.MONDAY, 1000, Day.MONDAY, 1200);
    Location eventLocation = new Location(false, "Sample Location");
    List<String> eventUsers = Arrays.asList("TestUser1", "TestUser2");
    model.generateEvent(eventName, eventTime, eventLocation, eventUsers);
    String knownDirectoryPath = "src";
    String userToSave = "TestUser1";
    view.saveXML(knownDirectoryPath, "TestUser1");
    Path filePath = Paths.get(knownDirectoryPath, userToSave + "-schedule.xml");
    assertTrue(Files.exists(filePath));
  }

  @Test
  public void testSaveXMLFail() {
    this.failureController.saveXML("input", "someone");
    assertEquals("IO failed", this.output.toString());
  }
  @Test
  public void testLoadXMLFail() {
    this.failureController.loadXML("input");
    assertEquals("IO failed", this.output.toString());
  }

  @Test
  public void testNoStrategy() {
    this.failureController.scheduleEvent("Test", 90,
            new Location(false, "test"), new ArrayList<>(Arrays.asList("Jack")));
    assertEquals("Scheduling Strategy is not set", this.output.toString());
  }


}
