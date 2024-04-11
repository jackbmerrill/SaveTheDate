package cs3500.calendar.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Public class for testing the inputs given to the model by the controller and via the view.
 * Does not work for any other purposes. Used purely for testing inputs.
 */
public class MockModel implements ICentralSystem {

  private final Appendable appendable;

  /**
   * Constructor for the fake model. Works only for testing
   * @param appendable the appendable to be taken in
   */
  public MockModel(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void addUser(String userId) {
    add(userId);
  }

  @Override
  public void generateEvent(String name, Time time, Location location, List<String> users) {
    add(name + ", " + time.getStartTime() + ", " + location.isOnline() + ", "
            + location.getPlace() + ", " + users);
  }

  @Override
  public void updateEventName(String userID, String oldName, String newName) {
    add(userID + ", " + oldName + ", " + newName);
  }

  @Override
  public void updateEventTime(String userID, String name, Time newTime) {
    add(userID + ", " + name + ", " + newTime.getStartTime());
  }

  @Override
  public void updateEventLocation(String userID, String name, Location newLocation) {
    add(userID + ", " + name + ", " + newLocation.isOnline() + ", "
            + newLocation.getPlace());
  }

  @Override
  public void removeEvent(String userID, String eventName) {
    add(userID + ", " + eventName);
  }

  @Override
  public void addEventToUser(String userID, String eventName) {
    add(userID + ", " + eventName);
  }

  @Override
  public void loadSchedulesFromXML(String filePath) throws IOException {
    add(filePath);
  }

  @Override
  public void saveSchedulesToXML(String directoryPath, String userID) throws IOException {
    add(directoryPath + ", " + userID);
  }

  @Override
  public List<IEvent> getEventsAtTime(String userId, Time time) {
    return new ArrayList<IEvent>();
  }

  @Override
  public Map<String, ISchedule> getSystem() {
    return null;
  }

  @Override
  public ISchedule getUserSchedule(String userID) {
    return null;
  }

  @Override
  public List<String> getUsers() {
    return null;
  }

  @Override
  public boolean eventConflict(Time time, List<String> users) {
    return false;
  }

  private void add(String str) {
    try {
      this.appendable.append(str + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed");
    }
  }
}
