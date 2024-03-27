package cs3500.calendar.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;

/**
 * It provides the neccessary functions in order to read and parse XML files
 * which contain event data, and it then loads said data into a CentralSystem. It opens
 * an XML file, navigates through its structure, extracts all the neccessary information,
 * and after identifying it, it registers those events to a CentralSystem.
 */
public class XMLReader {

  /**
   * Reads and parses the given XML file and pushes that data into the given
   * CentralSystem. It extracts details such the name, time, location, online status, and
   * invitees of an event within a schedule in order to actually create it in the CentralSystem.
   * All of the given information through the XML file are formatted in a way to allow them
   * to be read by the CentralSystem.
   *
   * @param filePath      filePath of the XML file that needs to be read and parsed
   * @param centralSystem centralSystem where all the data will be registered
   */
  public void loadScheduleFromFile(String filePath, CentralSystem centralSystem) {
    try {
      Document doc = parseXMLFile(filePath);
      NodeList events = doc.getElementsByTagName("event");
      for (int i = 0; i < events.getLength(); i++) {
        Node node = events.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          processEventElement((Element) node, centralSystem);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Document parseXMLFile(String filePath) throws Exception {
    File xmlFile = new File(filePath);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(xmlFile);
    doc.getDocumentElement().normalize();
    return doc;
  }

  private void processEventElement(Element element, CentralSystem centralSystem) {
    String name = cleanString(element.getElementsByTagName("name").item(0).getTextContent());
    Day startDay = Day.valueOf(cleanString(element.getElementsByTagName("start-day").item(0).
            getTextContent()).toUpperCase());
    Day endDay = Day.valueOf(cleanString(element.getElementsByTagName("end-day").item(0).
            getTextContent()).toUpperCase());
    int startTime = Integer.parseInt(element.getElementsByTagName("start").item(0).
            getTextContent());
    int endTime = Integer.parseInt(element.getElementsByTagName("end").item(0).
            getTextContent());
    String location = cleanString(element.getElementsByTagName("place").item(0).
            getTextContent());
    boolean isOnline = Boolean.parseBoolean(element.getElementsByTagName("online").item(0).
            getTextContent());
    List<String> users = extractUsers(element.getElementsByTagName("uid"));

    Time eventTime = new Time(startDay, startTime, endDay, endTime);
    Location eventLocation = new Location(isOnline, location);
    centralSystem.generateEvent(name, eventTime, eventLocation, users);
  }

  private String cleanString(String s) {
    return s.startsWith("\"") && s.endsWith("\"") ? s.substring(1, s.length() - 1) : s;
  }

  private List<String> extractUsers(NodeList nodeList) {
    List<String> users = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      String user = cleanString(nodeList.item(i).getTextContent());
      users.add(user);
    }
    return users;
  }
}


