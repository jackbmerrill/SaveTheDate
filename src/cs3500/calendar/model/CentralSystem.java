package cs3500.calendar.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.calendar.xml.XMLReader;
import cs3500.calendar.xml.XMLWriter;

/**
 * Implementation class of the central system. Has a map of string to schedule and a
 * List of all the events within the system. Enables the creation of new events, modification,
 * deletion, input of XML files and output of XMLs.
 */
public class CentralSystem implements ICentralSystem {

  private final Map<String, Schedule> system;
  private final List<Event> events;

  /**
   * To represent a constructor for Central System.
   * Is initialized as an empty hashmap that can take in key String and value Event.
   */
  public CentralSystem() {
    this.system = new HashMap<>();
    this.events = new ArrayList<>();
  }


  //need to fix this for the input
  @Override
  public void addUser(String userId) {
    this.system.put(userId, new Schedule(userId));
  }

  @Override
  public void generateEvent(String name, Time time, Location location, List<String> users) {
    Event generatedEvent = new Event(name, time, location, users);
    events.add(generatedEvent);
    for (String userId : users) {
      // if they don't exist, make new schedule
      system.computeIfAbsent(userId, k -> new Schedule(userId));

      // add event to schedule
      Schedule userSchedule = system.get(userId);
      try {
        userSchedule.addEvent(generatedEvent);
      } catch (IllegalStateException e) {
        throw new IllegalArgumentException("Error adding event for user " + userId + ": " + e.getMessage());
      }
    }
  }

  @Override
  public void updateEventName(String userID, String oldName, String newName) {
    for (String user : getSchedule(userID).getAllEventUsers(oldName)) {
      getSchedule(user).modifyEventName(oldName, newName);
    }
  }

  //check for all schedules connected
  @Override
  public void updateEventTime(String userID, String name, Time newTime) {
    for (String user : system.get(userID).getEvent(name).getUsers()) {
      getSchedule(user).modifyEventTime(name, newTime);
    }
  }

  @Override
  public void updateEventLocation(String userID, String name, Location newLocation) {
    getSchedule(userID).modifyEventLocation(name, newLocation);
  }

  // checks to unsure that the user is within the central system
  // and then gets the schedule associated with the user

  private Schedule getSchedule(String userID) {
    if (this.system.containsKey(userID)) {
      return this.system.get(userID);
    }
    throw new IllegalStateException("System does not contain user");
  }


  @Override
  public void removeEvent(String userID, String eventName) {
    Schedule userSchedule = getSchedule(userID);
    Event event = userSchedule.getEvent(eventName);
    if (event.getHost().equals(userID)) {
      for (String user : event.getUsers()) {
        if (this.system.containsKey(user)) {
          system.get(user).removeEvent(eventName);
        }
      }
    } else {
      userSchedule.removeEvent(eventName);
    }
  }

  @Override
  public void addEventToUser(String userID, String eventName) {
    for (Event event : events) {
      if (event.getName().equals(eventName)) {
        system.putIfAbsent(userID, new Schedule(userID));
        List<String> users = event.getUsers();
        users.add(userID);
        event.updateUsers(users);
        system.get(userID).addEvent(event);
        return;
      }
    }
    throw new IllegalStateException("Event does not exist");
  }

  public void loadSchedulesFromXML(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists() || !file.isFile()) {
      throw new FileNotFoundException("File does not exist: " + filePath);
    }
    XMLReader reader = new XMLReader();
    reader.loadScheduleFromFile(filePath, this);
  }



  @Override
  public void saveSchedulesToXML(String directoryPath, List<String> userIDs) throws IOException {
    XMLWriter writer = new XMLWriter();
    for (String userID : userIDs) {
      Schedule userSchedule = system.get(userID);
      if (userSchedule == null) {
        throw new FileNotFoundException("Schedule not found for user: " + userID);
      }
      String filePath = directoryPath.endsWith(File.separator)
              ? directoryPath + userID + "-schedule.xml"
              : directoryPath + File.separator + userID + "-schedule.xml";
      writer.writeScheduleToFile(filePath, userSchedule, userID);
    }
  }


  @Override
  public List<Event> getEventsAtTime(String user, Time time) {
    return getSchedule(user).getEventsAtTime(time);
  }

  @Override
  public Map<String, Schedule> getSystem() {
    Map<String, Schedule> systemClone = new HashMap<>();
    for (Map.Entry<String, Schedule> entry : system.entrySet()) {
      String userID = entry.getKey();
      Schedule clonedSchedule = new Schedule(userID);
      for (Event event : system.get(userID).getEventsAtTime(null)) {
        clonedSchedule.addEvent(event);
      }
      systemClone.put(entry.getKey(), clonedSchedule);
    }
    return systemClone;
  }
}
