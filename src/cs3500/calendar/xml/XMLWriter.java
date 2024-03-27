package cs3500.calendar.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.Time;
import cs3500.calendar.model.Schedule;

/**
 * This class represents an XMLWriter which essentially converts a given Schedule
 * to a newly generated XML file. It functions by formatting the given Schedules
 * events along with all of their details such as name, time, location, invitees, and
 * online status into the format that a structured XML file holds.
 */
public class XMLWriter {

  /**
   * This method writes the Schedule that its given to an XML file at the given file path. It
   * then proceeds by iterating over each event in the given schedule, along with all of its
   * details such as the name, time, location, invitees, and online status of the event
   * and converts that data to an XML format. The XML file that is created is then
   * stored in the requested file path which either creates an XML file or overwrites
   * it if an old version is present.
   *
   * @param filePath filePath where the XML document is saved
   * @param schedule schedule which contains the events and data we want to store in our
   *                XML document
   * @throws IOException throws exception if any errors occur during the process
   */

  public void writeScheduleToFile(String filePath, Schedule schedule, String scheduleId)
          throws IOException {
    try {
      Document doc = createDocument();
      Element rootElement = createRootElement(doc, scheduleId);
      for (Event event : schedule.getEventsAtTime(null)) {
        Element eventElement = createEventElement(doc, event);
        rootElement.appendChild(eventElement);
      }
      writeDocumentToFile(doc, filePath);
    } catch (Exception e) {
      throw new IOException("Error writing XML file: " + filePath, e);
    }
  }

  private Document createDocument() throws Exception {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    return dBuilder.newDocument();
  }

  private Element createRootElement(Document doc, String scheduleId) {
    Element rootElement = doc.createElement("schedule");
    rootElement.setAttribute("id", scheduleId);
    doc.appendChild(rootElement);
    return rootElement;
  }

  private Element createEventElement(Document doc, Event event) {
    Element eventElement = doc.createElement("event");
    addElementWithText(doc, eventElement, "name", "\"" + event.getName() + "\"");
    addTimeElements(doc, eventElement, event);
    addLocationElements(doc, eventElement, event);
    addUserElements(doc, eventElement, event);
    return eventElement;
  }

  private void addElementWithText(Document doc, Element parent, String tagName, String text) {
    Element element = doc.createElement(tagName);
    element.appendChild(doc.createTextNode(text));
    parent.appendChild(element);
  }

  private void addTimeElements(Document doc, Element parent, Event event) {
    Element timeElement = doc.createElement("time");
    parent.appendChild(timeElement);
    addElementWithText(doc, timeElement, "start-day", event.getTime().getStartDay().toString());
    addElementWithText(doc, timeElement, "start", Time.formatTime(event.getTime().getStartTime()));
    addElementWithText(doc, timeElement, "end-day", event.getTime().getEndDay().toString());
    addElementWithText(doc, timeElement, "end", Time.formatTime(event.getTime().getEndTime()));
  }

  private void addLocationElements(Document doc, Element parent, Event event) {
    Element locationElement = doc.createElement("location");
    parent.appendChild(locationElement);
    addElementWithText(doc, locationElement, "online", Boolean.toString(event.getLocation().isOnline()));
    addElementWithText(doc, locationElement, "place", "\"" + event.getLocation().getPlace() + "\"");
  }

  private void addUserElements(Document doc, Element parent, Event event) {
    Element usersElement = doc.createElement("users");
    parent.appendChild(usersElement);
    for (String user : event.getUsers()) {
      addElementWithText(doc, usersElement, "uid", "\"" + user + "\"");
    }
  }

  private void writeDocumentToFile(Document doc, String filePath) throws Exception {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(filePath));
    transformer.transform(source, result);
  }
}