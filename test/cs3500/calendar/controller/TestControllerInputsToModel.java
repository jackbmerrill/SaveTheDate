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
import cs3500.calendar.model.MockModel;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.WorkHoursSchedulingStrategy;
import cs3500.calendar.view.MockView;

import static org.junit.Assert.assertEquals;


/**
 * Tests all the controller inputs to the model. All inputs to the model are
 * added to an appendable and then the contents of said appendable are checked
 * in order to confirm the correct inputs.
 */
public class TestControllerInputsToModel {


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
    this.model = new MockModel(output);
    this.view = new MockView(output);
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
    this.controller.createEvent(event1);
    assertEquals("Test1, cs3500.calendar.model.Time@2de8284b, false, somewhere, [Jack, Milo]\n",
            this.output.toString());
  }

  @Test
  public void testModifyEvent() {
    Event event2 = new Event("Renamed", time2, new Location(true, "churchill"),
            new ArrayList<>(List.of("Jack")));
    this.controller.modifyEvent(event1, event2);
    assertEquals("Jack, Test1, Renamed\n" +
                    "Jack, Renamed, cs3500.calendar.model.Time@365185bd\n" +
                    "Jack, Renamed, true, churchill\n" +
                    "Milo, Renamed\n",
            this.output.toString());
  }

  @Test
  public void removeEvent() {
    this.controller.removeEvent(event1, "jack");
    assertEquals("jack, Test1\n", this.output.toString());
  }

  @Test
  public void testLoadXML() {
    this.controller.loadXML("ExamplePath");
    assertEquals("ExamplePath\n", this.output.toString());
  }

  @Test
  public void testSaveXML() {
    this.controller.saveXML("ExamplePath", "Jack");
    assertEquals("ExamplePath, Jack\n", this.output.toString());
  }

  @Test
  public void testScheduleEvent() {
    this.controller.scheduleEvent("Example", 90,
            new Location(true, "somewhere"), new ArrayList<>(Arrays.asList("jack")));
    //because it is not a real model given
    assertEquals("Unable to schedule an event because no suitable time was found.",
            this.output.toString());
  }

}
