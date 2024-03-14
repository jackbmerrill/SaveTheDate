package cs3500.calendar.model;

/**
 * To represent a central system class.
 *
 */

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The central system of the NUPlanner is the main entry point of the system.
 * This central system keeps track of all users and their individual schedules and
 * ensures all events between schedules are consistent. From here, events can be created,
 * modified, or even removed. It is the job of the central system to make sure all invited
 * users get events added or removed from their schedules. The central system must also
 * ensure any modification of an existing event is consistent in every invited user’s schedule.
 * This part also ensures if the host of an event has their event removed, every invited user’s
 * schedule is also updated to remove the event. If the user removes an event that they are not
 * hosting, then the event is only removed from their schedule. Naturally the events must be
 * updated to remove the invitee. However, there should be no issues if a user invited to an
 * event does not exist in the system because the current instance of the system might only have
 * some of the users loaded in.
 *
 * We will assume all user names (or uid) are unique for ease.
 * We are also creating a system based on trust. This means any user can modify events,
 * regardless if they are hosts of the event or not. In other words, there is no distinct power a
 * user has in the system. If a user wishes to interact with the system, they have the same
 * privilege as a client. 1
 */
public class CentralSystem implements ICentralSystem {

  private final Map<String, Schedule> system;

  /**
   * To represent a constructor for Central System.
   * Is initialized as an empty hashmap that can take in key String and value Event.
   */
  public CentralSystem() {
    this.system = new HashMap<>();
  }


  @Override
  public void addUser(String userId, Schedule schedule) {
    this.system.put(userId, new Schedule());
  }

  @Override
  public void generateEvent(String name, Time time, Location location, List<String> users) {
    Event generatedEvent = new Event(name, time, location, users);

    //iterate over each user
    for (String userId : users) {
      //ensure they exist otherwise make a new schedule
      ensureUserExists(userId);

      //add the event to the users schedule
      Schedule userSchedule = system.get(userId);
      userSchedule.addEvent(generatedEvent);
    }
  }


  public void ensureUserExists(String userId) {
    system.putIfAbsent(userId, new Schedule());
  }


  @Override
  public void updateEventName(String userID, String oldName, String newName) {
    getSchedule(userID).modifyEventName(oldName, newName);
  }

  @Override
  public void updateEventTime(String userID, String name, Time newTime) {
    getSchedule(userID).modifyEventTime(name, newTime);
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
  public Schedule userSchedule(String userId) {
    return this.getSchedule(userId);
  }

  //load all schedules from an XML
  public void loadSchedulesFromXML(String filePath) {
    ReadXML reader = new ReadXML();
    reader.loadScheduleFromFile(filePath, this);
  }

  //save all schedules to their assigned XML files
  public void saveSchedulesToXML(String directoryPath) {
    XmlWriter writer = new XmlWriter();
    for (Map.Entry<String, Schedule> entry : system.entrySet()) {
      //ensure directoryPath ends with seperator
      String filePath = directoryPath.endsWith(File.separator)
              ? directoryPath + entry.getKey() + "-schedule.xml"
              : directoryPath + File.separator + entry.getKey() + "-schedule.xml";
      try {
        writer.writeScheduleToFile(filePath, entry.getValue());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  //return entire system
  public Map<String, Schedule> getSystem() {
    return this.system;
  }
}
