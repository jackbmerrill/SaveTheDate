package cs3500.calendar.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Mock class to test an IO failure and how the controller handles that error.
 * Does not work for any other purpose
 */
public class MockModelIOFail implements ICentralSystem {

  @Override
  public void addUser(String userId) {
    return;
  }

  @Override
  public void generateEvent(String name, Time time, Location location, List<String> users) {
    return;
  }

  @Override
  public void updateEventName(String userID, String oldName, String newName) {
    return;
  }

  @Override
  public void updateEventTime(String userID, String name, Time newTime) {
    return;
  }

  @Override
  public void updateEventLocation(String userID, String name, Location newLocation) {
    return;
  }

  @Override
  public void removeEvent(String userID, String eventName) {
    return;
  }

  @Override
  public void addEventToUser(String userID, String eventName) {
    return;
  }

  @Override
  public void loadSchedulesFromXML(String filePath) throws IOException {
    throw new IOException("IO failed");
  }

  @Override
  public void saveSchedulesToXML(String directoryPath, String userID) throws IOException {
    throw new IOException("IO failed");
  }

  @Override
  public List<IEvent> getEventsAtTime(String userId, Time time) {
    return null;
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
}
