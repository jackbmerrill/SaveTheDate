package cs3500.provider.model;

/**
 * The IScheduler interface defines the operations for scheduling and managing events,
 * as well as adding, removing, and retrieving users within a scheduling system. It provides
 * methods to manipulate user entries and events effectively.
 */
public interface PlannerModel extends ReadOnlyPlannerModel {
  void addUser(User u);

  void removeUser(User u);

  void removeEvent(Event event);

  User getUser(String name);

  void createEvent(Event e);

  void addUserFromXML(String filePath);

  void exportUserScheduleToXML(String username, String filePath);

  void updateEvent(Event originalEvent, Event updatedEvent);

  User getCurrentUser();

  void setCurrentUser(User newUser);

}
