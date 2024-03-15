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
   * then proceeds by iterating over each event in the given schedule, along with all of its details
   * such as the name, time, location, invitees, and online status of the event and converts that data
   * to an XML format. The XML file that is created is then stored in the requested file path which either
   * creates an XML file or overwrites it if an old version is present.
   *
   * @param filePath filePath where the XML document is saved
   * @param schedule schedule which contains the events and data we want to store in our XML document
   * @throws Exception throws exception if any errors occur during the process
   */
  public void writeScheduleToFile(String filePath, Schedule schedule, String scheduleId) throws Exception {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.newDocument();

    //root schedule creation
    Element rootElement = doc.createElement("schedule");
    rootElement.setAttribute("id", scheduleId);
    doc.appendChild(rootElement);

    for (Event event : schedule.getEventsAtTime(null)) {
      Element eventElement = doc.createElement("event");
      rootElement.appendChild(eventElement);

      //quotes for name
      Element name = doc.createElement("name");
      name.appendChild(doc.createTextNode("\"" + event.getName() + "\""));
      eventElement.appendChild(name);

      //time
      Element timeElement = doc.createElement("time");
      eventElement.appendChild(timeElement);

      Element startDayElement = doc.createElement("start-day");
      startDayElement.appendChild(doc.createTextNode(event.getTime().getStartDay().toString()));
      timeElement.appendChild(startDayElement);

      Element startTimeElement = doc.createElement("start");
      startTimeElement.appendChild(doc.createTextNode(Time.formatTime(event.getTime().getStartTime())));
      timeElement.appendChild(startTimeElement);

      Element endDayElement = doc.createElement("end-day");
      endDayElement.appendChild(doc.createTextNode(event.getTime().getEndDay().toString()));
      timeElement.appendChild(endDayElement);

      Element endTimeElement = doc.createElement("end");
      endTimeElement.appendChild(doc.createTextNode(Time.formatTime(event.getTime().getEndTime())));
      timeElement.appendChild(endTimeElement);

      //location
      Element locationElement = doc.createElement("location");
      eventElement.appendChild(locationElement);

      Element onlineElement = doc.createElement("online");
      onlineElement.appendChild(doc.createTextNode(String.valueOf(event.getLocation().isOnline())));
      locationElement.appendChild(onlineElement);

      //quotes for place
      Element placeElement = doc.createElement("place");
      placeElement.appendChild(doc.createTextNode("\"" + event.getLocation().getPlace() + "\""));
      locationElement.appendChild(placeElement);

      //users
      Element usersElement = doc.createElement("users");
      eventElement.appendChild(usersElement);
      for (String user : event.getUsers()) {
        Element userElement = doc.createElement("uid");
        userElement.appendChild(doc.createTextNode("\"" + user + "\""));
        usersElement.appendChild(userElement);
      }
    }

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(filePath));

    transformer.transform(source, result);
  }
}
