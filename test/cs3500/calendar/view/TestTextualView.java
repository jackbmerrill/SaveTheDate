package cs3500.calendar.view;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;
import cs3500.calendar.xml.XMLReader;

/**
 * To test the public methods for textual view.
 */
public class TestTextualView {

  @Test
  public void testTextualViewMultipleUser() {
    CentralSystem centralSystem = new CentralSystem();
    XMLReader xmlReader = new XMLReader();
    String filePath = "src/testSchedule.xml";

    //load schedule from XML file
    xmlReader.loadScheduleFromFile(filePath, centralSystem);
    TextualView textualView = new TextualView(centralSystem);

    //generate textual view
    String view = textualView.generateTextualView();

    //print it
    System.out.println(view);

    assertTrue("Textual view should contain Prof. Lucia's schedule", view.contains(
            "User: Prof. Lucia"));
    assertTrue("Textual view should contain CS3500 Morning Lecture event", view.contains(
            "CS3500 Morning Lecture"));
    assertTrue("Textual view should contain CS3500 Afternoon Lecture event", view.contains(
            "CS3500 Afternoon Lecture"));
    assertTrue("Textual view should contain Sleep event", view.contains("Sleep"));
    assertTrue("Textual view should mention Churchill Hall 101", view.contains(
            "Churchill Hall 101"));
    assertTrue("Textual view should mention Home", view.contains("Home"));
    assertTrue("Textual view should mention online status", view.contains(
            "online: true") || view.contains("online: false"));
    assertTrue(
            "Textual view should list invitees including Prof. Lucia, Student Anon, " +
                    "and Chat",
            view.contains("Prof. Lucia") && view.contains("Student Anon") && view.contains(
                    "Chat"));
  }


  @Test
  public void testTextualViewOneUser() {
    CentralSystem centralSystem = new CentralSystem();
    String userId = "Dio";

    centralSystem.generateEvent("Morning Jog", new Time(Day.MONDAY, 600, Day.MONDAY, 700),
            new Location(true, "Park"), Arrays.asList(userId));
    centralSystem.generateEvent("Evening Yoga", new Time(Day.MONDAY, 1800, Day.MONDAY, 1900),
            new Location(false, "Yoga Studio"), Arrays.asList(userId));

    TextualView textualView = new TextualView(centralSystem);
    String view = textualView.generateTextualView();

    System.out.println(view);

    assertTrue("Textual view should contain Morning Jog event",
            view.contains("Morning Jog"));
    assertTrue("Textual view should contain Evening Yoga event",
            view.contains("Evening Yoga"));
    assertTrue("Textual view should mention Park location for Morning Jog",
            view.contains("Park"));
    assertTrue("Textual view should mention Yoga Studio location for Evening Yoga",
            view.contains("Yoga Studio"));
  }
}
