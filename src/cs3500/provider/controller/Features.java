package cs3500.provider.controller;

import model.Event;
import model.User;

import java.io.File;
import java.util.List;

public interface Features {

  /**
   * Adds a user and their schedule to the system from an XML file.
   * @param xmlFile The XML file containing the user's schedule.
   */
  void addUser(String xmlFile);

  /**
   * Saves all schedules in the system to XML files in a specified directory.
   * @param userName
   * @param filepath
   */
  void saveSchedules(String userName, String filepath);

  /**
   * Creates a new event.
   */
  void createEvent(String name, String location, boolean onlineStatus, String startDay,
                   String startTime, String endDay, String endTime,
                   User host, List<User> invitedUsers);

  /**
   * Modifies an existing event.
   * @param eventId The ID of the event to modify.
   * @param newDetails The new event to replace the old event.
   */
  void modifyEvent(Event oldEvent, Event newEvent);

  /**
   * Removes an event from the system.
   * @param eventId The ID of the event to remove.
   */
  void removeEvent(Event eventId);

  /**
   * Opens the frame to create or modify an event.
   */
  void openEventFrame();

  /**
   * Switches the currently viewed user in the system.
   * @param userId The ID of the user to view.
   */
  void switchUser(String userId);
}
