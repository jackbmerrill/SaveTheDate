package cs3500.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class TestCentralSystem {

  private CentralSystem centralSystem;

  @BeforeEach
  void setUp() {
    centralSystem = new CentralSystem();
  }

  @Test
  void testLoadScheduleFromXML() {
    String filePath = "src/testSchedule.xml";
    centralSystem.loadSchedulesFromXML(filePath);
    assertTrue(centralSystem.getSystem().containsKey("Prof. Lucia"), "Prof. Lucia's schedule is loaded");
    Schedule schedule = centralSystem.getSystem().get("Prof. Lucia");
    assertEquals(3, schedule.getEvents().size(), "3 events need to be loaded");
    assertTrue(schedule.getEvents().containsKey("CS3500 Morning Lecture"), "Event needs to match");
    Event morningLecture = schedule.getEvents().get("CS3500 Morning Lecture");
    assertEquals("Churchill Hall 101", morningLecture.getLocation().getPlace());
    assertFalse(morningLecture.getLocation().isOnline());
    assertTrue(morningLecture.getUsers().contains("Prof. Lucia"), "Prof. Lucia is a participant");
    assertTrue(morningLecture.getUsers().contains("Student Anon"), "Student Anon is a participant");
    assertTrue(morningLecture.getUsers().contains("Chat"), "Chat is a participant");
    assertEquals("0950", morningLecture.getTime().getFormattedStartTime());
  }

  @Test
  void testSaveSchedulesToXML() throws Exception {
    String eventName = "Test Event";
    Time eventTime = new Time(Day.MONDAY, 1000, Day.MONDAY, 1200);
    Location eventLocation = new Location(false, "Test Location");
    List<String> eventUsers = List.of("TestUser");
    centralSystem.generateEvent(eventName, eventTime, eventLocation, eventUsers);
    //directory path
    String knownDirectoryPath = "src";
    String fullPath = knownDirectoryPath + File.separator + "TestUser-schedule.xml";
    //save schedules to directory path
    centralSystem.saveSchedulesToXML(knownDirectoryPath);
    //checker for directory path existing file
    Path filePath = Paths.get(fullPath);
    assertTrue(Files.exists(filePath), "XML File is there");
  }
}
