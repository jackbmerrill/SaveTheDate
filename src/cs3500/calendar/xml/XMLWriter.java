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
import java.util.Map;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.Time;
import cs3500.calendar.model.Schedule;

public class XMLWriter {
  public void writeScheduleToFile(String filePath, Schedule schedule) throws Exception {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.newDocument();

    Element rootElement = doc.createElement("schedule");
    doc.appendChild(rootElement);

    Map<String, Event> events = schedule.getEvents();
    for (Map.Entry<String, Event> entry : events.entrySet()) {
      Event event = entry.getValue();
      Element eventElement = doc.createElement("event");
      rootElement.appendChild(eventElement);

      //name
      Element name = doc.createElement("name");
      name.appendChild(doc.createTextNode(event.getName()));
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
      onlineElement.appendChild(doc.createTextNode(Boolean.toString(event.getLocation().isOnline())));
      locationElement.appendChild(onlineElement);

      Element placeElement = doc.createElement("place");
      placeElement.appendChild(doc.createTextNode(event.getLocation().getPlace()));
      locationElement.appendChild(placeElement);

      //users
      Element usersElement = doc.createElement("users");
      eventElement.appendChild(usersElement);
      for (String user : event.getUsers()) {
        Element userElement = doc.createElement("user");
        userElement.appendChild(doc.createTextNode(user));
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
