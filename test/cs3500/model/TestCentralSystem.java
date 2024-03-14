package cs3500.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;

public class TestCentralSystem {

  private CentralSystem centralSystem;

  @Before
  public void setUp() {
    centralSystem = new CentralSystem();
  }

//  @Test
//  public void testLoadScheduleFromXML() {
//    String filePath = "src/testSchedule.xml";
//    centralSystem.loadSchedulesFromXML(filePath);
//    assertTrue(centralSystem.getSystem().containsKey("Prof. Lucia"));
//    Schedule schedule = centralSystem.getSystem().get("Prof. Lucia");
//    assertEquals(3, schedule.getEvents().size());
//    assertTrue(schedule.getEvents().containsKey("CS3500 Morning Lecture"));
//    Event morningLecture = schedule.getEvents().get("CS3500 Morning Lecture");
//    assertEquals("Churchill Hall 101", morningLecture.getLocation().getPlace());
//    assertFalse(morningLecture.getLocation().isOnline());
//    assertTrue(morningLecture.getUsers().contains("Prof. Lucia"));
//    assertTrue(morningLecture.getUsers().contains("Student Anon"));
//    assertTrue(morningLecture.getUsers().contains("Chat"));
//    String formattedStartTime = Time.formatTime(morningLecture.getTime().getStartTime());
//    assertEquals("0950", formattedStartTime);
//  }

  @Test
  public void testSaveSchedulesToXML() throws Exception {
    String eventName = "Test Event";
    Time eventTime = new Time(Day.MONDAY, 1000, Day.MONDAY, 1200);
    Location eventLocation = new Location(false, "Test Location");
    List<String> eventUsers = List.of("TestUser");
    centralSystem.generateEvent(eventName, eventTime, eventLocation, eventUsers);
    String knownDirectoryPath = "src";
    String fullPath = knownDirectoryPath + File.separator + "TestUser-schedule.xml";
    centralSystem.saveSchedulesToXML(knownDirectoryPath);
    Path filePath = Paths.get(fullPath);
    assertTrue(Files.exists(filePath));
  }
}

