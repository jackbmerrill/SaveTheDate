package cs3500.calendar.view;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.view.TextualView;
import cs3500.calendar.xml.XMLReader;

/**
 * To test the public methods for textual view.
 */
public class TestTextualView {

  @Test
  public void testGenerateTextualView() {
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
}
