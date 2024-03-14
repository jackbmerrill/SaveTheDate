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

public class XMLReader {

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
          String name = cleanString(element.getElementsByTagName("name").item(0).getTextContent());
          String startDay = element.getElementsByTagName("start-day").item(0).getTextContent().toUpperCase();
          String startTime = element.getElementsByTagName("start").item(0).getTextContent();
          String endDay = element.getElementsByTagName("end-day").item(0).getTextContent().toUpperCase();
          String endTime = element.getElementsByTagName("end").item(0).getTextContent();
          String locationPlace = cleanString(element.getElementsByTagName("place").item(0).getTextContent());
          boolean isOnline = Boolean.parseBoolean(element.getElementsByTagName("online").item(0).getTextContent());

          //extract users and cleanup their UIDs
          List<String> users = new ArrayList<>();
          NodeList usersList = element.getElementsByTagName("uid");
          for (int j = 0; j < usersList.getLength(); j++) {
            users.add(cleanString(usersList.item(j).getTextContent()));
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

  //remove starting and closing quotes
  private String cleanString(String input) {
    if (input.startsWith("\"") && input.endsWith("\"")) {
      return input.substring(1, input.length() - 1);
    }
    return input;
  }
}


