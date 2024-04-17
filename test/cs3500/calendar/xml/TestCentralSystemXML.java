package cs3500.calendar.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.IEvent;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * To test central system XML methods.
 */
public class TestCentralSystemXML {

  private CentralSystem centralSystem;

  @Before
  public void setUp() {
    centralSystem = new CentralSystem();
  }

  // to test the load schedule from XML method
  @Test
  public void testLoadScheduleFromXML() throws IOException {
    Time time1 = new Time(Day.TUESDAY, 950, Day.TUESDAY, 1130);
    String filePath = "src/testSchedule.xml";
    centralSystem.loadSchedulesFromXML(filePath);
    List<IEvent> events = centralSystem.getEventsAtTime("Prof. Lucia", null);
    assertEquals(3, events.size());
    IEvent morningLecture = centralSystem.getEventsAtTime("Prof. Lucia", time1).get(0);
    assertEquals("Churchill Hall 101", morningLecture.getLocation().getPlace());
    assertFalse(morningLecture.getLocation().isOnline());
    assertTrue(morningLecture.getUsers().contains("Prof. Lucia"));
    assertTrue(morningLecture.getUsers().contains("Student Anon"));
    assertTrue(morningLecture.getUsers().contains("Chat"));
    String formattedStartTime = Time.formatTime(morningLecture.getTime().getStartTime());
    assertEquals("0950", formattedStartTime);
  }

  @Test
  public void testLoadScheduleFromInvalidXML() {
    String invalidFilePath = "src/doesNotExist.xml";
    assertThrows(FileNotFoundException.class, () -> centralSystem.
            loadSchedulesFromXML(invalidFilePath));
  }


  @Test
  public void testSaveScheduleToInvalidPath() {
    String eventName = "Sample Event";
    Time eventTime = new Time(Day.MONDAY, 1000, Day.MONDAY, 1200);
    Location eventLocation = new Location(false, "Sample Location");
    List<String> eventUsers = Arrays.asList("TestUser1");
    centralSystem.generateEvent(eventName, eventTime, eventLocation, eventUsers);

    String invalidDirectoryPath = "\0invalidPath";
    assertThrows(IOException.class, () -> centralSystem.
            saveSchedulesToXML(invalidDirectoryPath, "john"));
  }

  @Test
  public void testSaveUnregisteredUserScheduleToXML() {
    String unregisteredUser = "UnregisteredUser";
    String knownDirectoryPath = "src";

    assertThrows(FileNotFoundException.class, () -> centralSystem.
            saveSchedulesToXML(knownDirectoryPath,
            "john"));
  }

  // to test the save schedule to XML method
  @Test
  public void testSaveSingleUserScheduleToXML() throws Exception {
    String eventName = "Sample Event";
    Time eventTime = new Time(Day.MONDAY, 1000, Day.MONDAY, 1200);
    Location eventLocation = new Location(false, "Sample Location");
    List<String> eventUsers = Arrays.asList("TestUser1", "TestUser2");
    centralSystem.generateEvent(eventName, eventTime, eventLocation, eventUsers);
    String knownDirectoryPath = "src";
    String userToSave = "TestUser1";
    centralSystem.saveSchedulesToXML(knownDirectoryPath, "TestUser1");
    Path filePath = Paths.get(knownDirectoryPath, userToSave + "-schedule.xml");
    assertTrue(Files.exists(filePath));
  }

}

