package cs3500.calendar.model;

/**
 * To represent a central system class.
 *
 */

import java.util.ArrayList;
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


  //need to fix this for the input
  @Override
  public void addUser(String userId, Schedule schedule) {
    this.system.put(userId, new Schedule(userId));
  }

  @Override
  public void generateEvent(String name, Time time, Location location, List<String> users) {
    Event generatedEvent = new Event(name, time, location, users);
    for (String user : users) {
       if (system.containsKey(user)) {
         //if overlap of the object with the schedule, dont add the event and remove the user from
         //the event
         system.get(user).addEvent(generatedEvent);
       } else {
         system.put(user, new Schedule(user));
         system.get(user).addEvent(generatedEvent);
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
      getSchedule(userID).modifyEventTime(name, newTime);
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

  //needs to return a new copy of the schedule
  @Override
  public Schedule userSchedule(String userId) {
    return this.getSchedule(userId);
  }
}
