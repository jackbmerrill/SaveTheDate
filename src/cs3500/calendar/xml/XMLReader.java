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
      File xmlFile = new File(filePath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("event");
      for (int i = 0; i < nList.getLength(); i++) {
        Node nNode = nList.item(i);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) nNode;
          //extract event details and clean them up
          String name = element.getElementsByTagName("name").item(0).getTextContent();
          name = name.startsWith("\"") && name.endsWith("\"")
                  ? name.substring(1, name.length() - 1) : name;

          String startDay = element.getElementsByTagName("start-day").item(0).
                  getTextContent().toUpperCase();

          String startTime = element.getElementsByTagName("start").item(0).getTextContent();

          String endDay = element.getElementsByTagName("end-day").item(0).getTextContent().
                  toUpperCase();

          String endTime = element.getElementsByTagName("end").item(0).getTextContent();

          String locationPlace = element.getElementsByTagName("place").item(0).
                  getTextContent();
          locationPlace = locationPlace.startsWith("\"") && locationPlace.endsWith("\"") ?
                  locationPlace.substring(1, locationPlace.length() - 1) : locationPlace;
          boolean isOnline = Boolean.parseBoolean(element.getElementsByTagName("online").
                  item(0).getTextContent());

          //extract users and cleanup their UIDs
          List<String> users = new ArrayList<>();
          NodeList usersList = element.getElementsByTagName("uid");
          for (int j = 0; j < usersList.getLength(); j++) {
            String user = usersList.item(j).getTextContent();
            user = user.startsWith("\"") && user.endsWith("\"")
                    ? user.substring(1, user.length() - 1) : user;
            users.add(user);
          }
          //convert start and end times to integers
          int startTimeInt = Integer.parseInt(startTime);
          int endTimeInt = Integer.parseInt(endTime);
          Day startDayEnum = Day.valueOf(startDay);
          Day endDayEnum = Day.valueOf(endDay);
          Time eventTime = new Time(startDayEnum, startTimeInt, endDayEnum, endTimeInt);
          Location eventLocation = new Location(isOnline, locationPlace);
          centralSystem.generateEvent(name, eventTime, eventLocation, users);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


