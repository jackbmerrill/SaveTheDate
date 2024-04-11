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

  private final Map<String, ISchedule> system;
  private final List<IEvent> events;

  /**
   * To represent a constructor for Central System.
   * Is initialized as an empty hashmap that can take in key String and value Event.
   */
  public CentralSystem() {
    this.system = new HashMap<>();
    this.events = new ArrayList<>();
  }

  /**
   * To represent the ability to create a model with valid schedules, assume they are valid.
   * @param schedules the given list of schedules
   */
  public CentralSystem(List<ISchedule> schedules) {
    this.system = new HashMap<>();
    this.events = new ArrayList<>();
    for (ISchedule sched : schedules) {
      this.system.put(sched.getUserID(), new Schedule(sched));
    }
  }

  @Override
  public void addUser(String userId) {
    this.system.put(userId, new Schedule(userId));
  }

  @Override
  public void generateEvent(String name, Time time, Location location, List<String> users) {
    IEvent generatedEvent = new Event(name, time, location, users);
    events.add(generatedEvent);
    for (String userId : users) {
      // if they don't exist, make new schedule
      system.computeIfAbsent(userId, k -> new Schedule(userId));
      // add event to schedule
      ISchedule userSchedule = system.get(userId);
      userSchedule.addEvent(generatedEvent);
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
    getSchedule(userID).getEvent(name);
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

  private ISchedule getSchedule(String userID) {
    if (this.system.containsKey(userID)) {
      return this.system.get(userID);
    }
    throw new IllegalStateException("System does not contain user");
  }

  @Override
  public void removeEvent(String userID, String eventName) {
    ISchedule userSchedule = getSchedule(userID);
    IEvent event = userSchedule.getEvent(eventName);
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
    for (IEvent event : events) {
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

  @Override
  public void loadSchedulesFromXML(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists() || !file.isFile()) {
      throw new FileNotFoundException("File does not exist: " + filePath);
    }
    XMLReader reader = new XMLReader();
    reader.loadScheduleFromFile(filePath, this);
  }


  @Override
  public void saveSchedulesToXML(String directoryPath, String userID) throws IOException {
    XMLWriter writer = new XMLWriter();
    ISchedule userSchedule = system.get(userID);
    if (userSchedule == null) {
      throw new FileNotFoundException("Schedule not found for user: " + userID);
    }
    String filePath = directoryPath.endsWith(File.separator)
            ? directoryPath + userID + "-schedule.xml"
            : directoryPath + File.separator + userID + "-schedule.xml";
    writer.writeScheduleToFile(filePath, userSchedule, userID);
  }


  @Override
  public List<IEvent> getEventsAtTime(String user, Time time) {
    return getSchedule(user).getEventsAtTime(time);
  }

  @Override
  public Map<String, ISchedule> getSystem() {
    Map<String, ISchedule> systemClone = new HashMap<>();
    for (Map.Entry<String, ISchedule> entry : system.entrySet()) {
      String userID = entry.getKey();
      Schedule clonedSchedule = new Schedule(userID);
      for (IEvent event : system.get(userID).getEventsAtTime(null)) {
        clonedSchedule.addEvent(event);
      }
      systemClone.put(entry.getKey(), clonedSchedule);
    }
    return systemClone;
  }

  @Override
  public ISchedule getUserSchedule(String userID) {
    ISchedule userSchedule = system.get(userID);
    return new Schedule(userSchedule);
  }

  @Override
  public List<String> getUsers() {
    return new ArrayList<>(system.keySet());
  }

  @Override
  public boolean eventConflict(Time time, List<String> users) {
    for (String user : users) {
      if (!system.containsKey(user)) {
        continue;
      }
      if (!getEventsAtTime(user, time).isEmpty()) {
        return true;
      }
    }
    return false;
  }
}
