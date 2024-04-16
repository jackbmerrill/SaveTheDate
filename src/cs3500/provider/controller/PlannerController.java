package cs3500.provider.controller;

import model.Event;
import model.PlannerModel;
import model.User;
import cs3500.provider.view.CalendarUI;
import cs3500.provider.view.EventFrame;

import java.util.List;


/**
 * Manages interactions between the model and view in a calendar planner application,
 * implementing actions based on user input.
 */
public class PlannerController implements Features {
  private PlannerModel model;
  private CalendarUI view;


  /**
   * Initializes the controller with a model and view, linking them together.
   *
   * @param model The data model of the planner.
   * @param view  The user interface of the planner.
   */
  public PlannerController(PlannerModel model, CalendarUI view) {
    this.model = model;
    this.view = view;
    this.view.setFeatures(this);
  }

  /**
   * Adds a user by reading from an XML file and updates the view.
   *
   * @param xmlFilePath Path to the user's XML file.
   */
  @Override
  public void addUser(String xmlFilePath) {
    model.addUserFromXML(xmlFilePath);
    view.updateView();
  }


  /**
   * Exports a user's schedule to an XML file.
   *
   * @param userName Name of the user.
   * @param filepath Destination file path.
   */
  @Override
  public void saveSchedules(String userName, String filepath) {
    model.exportUserScheduleToXML(userName, filepath);
  }

  @Override
  public void createEvent(String name, String location, boolean onlineStatus,
                          String startDay, String startTime, String endDay,
                          String endTime, User host, List<User> invitedUsers) {
    Event event = new Event(name, location, onlineStatus,
        startDay, startTime, endDay, endTime, host, invitedUsers);
    model.createEvent(event);
  }

  @Override
  public void modifyEvent(Event oldEvent, Event newEvent) {

    model.updateEvent(oldEvent, newEvent);

  }

  @Override
  public void removeEvent(Event eventToRemove) {
    model.removeEvent(eventToRemove);

  }

  @Override
  public void openEventFrame() {
    EventFrame eventFrame = new EventFrame(null, model, this, view);
    eventFrame.setVisible(true);
  }


  @Override
  public void switchUser(String userId) {
    this.model.setCurrentUser(model.getUser(userId));
  }

  public void autoSchedule() {
  //    was not able to implement
  //    would use brute force approach by finding the first available time slot which works
  //    check this against the rest of the user or invitees.
  //    keep repeating process until one time slot is found
  }
}
