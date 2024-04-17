package cs3500.provider.controller;

import java.util.ArrayList;
import java.util.List;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.ICentralSystem;
import cs3500.calendar.model.IEvent;
import cs3500.calendar.model.ITime;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;
import cs3500.provider.model.Event;
import cs3500.provider.model.ReadOnlyPlannerModel;
import cs3500.provider.model.Schedule;
import cs3500.provider.model.User;
import cs3500.provider.view.CalendarUI;
import cs3500.provider.view.EventFrame;


/**
 * Public class adapter designed to connect the provided view to our controller and model.
 * Takes in an implementation of our features interface and takes all inputs of their
 * view to pass into our controller. Their features interface is missing certain functionalities
 * and has a few methods that we do not have in our own code.
 */
public class FeatureAdapter implements Features {

  private final IFeatures controller;
  private final CalendarUI view;
  private final ReadOnlyPlannerModel adaptedModel;

  /**
   * Adapts our existing feature interface to integrate into provider view. Takes in our
   * IFeature and ICentralSystem interfaces
   * @param controller our controller
   * @param model our model
   */
  public FeatureAdapter(IFeatures controller, ICentralSystem model) {
    this.controller = controller;
    this.adaptedModel = new ReadOnlyModelAdapter(model);
    this.view = new CalendarUI(adaptedModel);
    this.view.setFeatures(this);
  }

  @Override
  public void addUser(String xmlFile) {
    this.controller.loadXML(xmlFile);
    view.updateView();
  }

  @Override
  public void saveSchedules(String userName, String filepath) {
    this.controller.saveXML(userName, filepath);
    view.updateView();
  }

  @Override
  public void createEvent(String name, String location, boolean onlineStatus,
                          String startDay, String startTime, String endDay, String endTime,
                          User host, List<User> invitedUsers) {
    ITime time = new Time(Day.valueOf(startDay.toUpperCase()), Integer.parseInt(startTime),
            Day.valueOf(endDay.toUpperCase()), Integer.parseInt(endTime));
    List<String> users = new ArrayList<>();
    for (User user : invitedUsers) {
      users.add(user.getName());
    }
    users.add(0, host.getName());
    this.controller.createEvent(new cs3500.calendar.model.Event(name, time,
            new Location(onlineStatus, location), users));
  }

  @Override
  public void modifyEvent(Event oldEvent, Event newEvent) {
    this.controller.modifyEvent(makeIEvent(oldEvent), makeIEvent(newEvent));
    view.render();
  }

  private static IEvent makeIEvent(Event event) {
    ITime oldTime = new Time(Day.valueOf(event.getStartDay().toUpperCase()),
            Integer.parseInt(event.getStartTime()),
            Day.valueOf(event.getEndDay().toUpperCase()),
            Integer.parseInt(event.getEndTime()));
    List<String> users = new ArrayList<>();
    for (User user : event.getInvitedUsers()) {
      users.add(user.getName());
    }
    users.add(0, event.getHost().getName());
    return new cs3500.calendar.model.Event(event.getName(), oldTime,
            new Location(event.getOnlineStatus(), event.getLocation()),
            users);
  }

  @Override
  public void removeEvent(Event eventId) {
    this.controller.removeEvent(makeIEvent(eventId), eventId.getHost().getName());
    view.render();
  }

  @Override
  public void openEventFrame() {
    EventFrame eventFrame = new EventFrame(null, adaptedModel, this, view);
    eventFrame.setVisible(true);
  }

  @Override
  public void switchUser(String userId) {
    return;
  }

  /**
   * Adapter for provider read only model. We cannot make it work as there are no interfaces
   * for users or schedules, which the user each contain. There are no javadocs on the interface,
   * so we do not even know what errors are supposed to be thrown when. All I can do is guess
   * what each method is supposed to do.
   */
  public static class ReadOnlyModelAdapter implements ReadOnlyPlannerModel {

    private final ReadOnlyCentralSystem model;
    private final List<User> users;

    /**
     * Constructor for the read only model adapter.
     * @param model our model to be taken in
     */
    public ReadOnlyModelAdapter(ReadOnlyCentralSystem model) {
      this.model = model;
      this.users = new ArrayList<>();
      populateUsers();
    }

    private void populateUsers() {
      for (String user : model.getUsers()) {
        User thisUser = new User(user);
        this.users.add(thisUser);
        Schedule sched = thisUser.getSchedule();
        for (IEvent event : model.getEventsAtTime(user, null)) {
          sched.addEvent(adaptEvent(event));
        }
      }
    }

    private Event adaptEvent(IEvent event) {
      User host = null;
      List<User> invitees = new ArrayList<>();
      for (User user : users) {
        if (event.getHost().equals(user.getName())) {
          host = user;
        } else if (event.getUsers().contains(user.getName())) {
          invitees.add(user);
        }
      }
      return new Event(event.getName(), event.getLocation().getPlace(),
              event.getLocation().isOnline(),
              event.getTime().getStartDay().toString(),
              String.format("%04d", event.getTime().getStartTime()),
              event.getTime().getEndDay().toString(),
              String.format("%04d", event.getTime().getEndTime()),
              host, invitees);
    }

    @Override
    public User getUser(String name) {
      for (User user : users) {
        if (user.getName().equals(name)) {
          return user;
        }
      }
      throw new IllegalArgumentException("No such user exists");
      //I dont even know if this is the correct exception or if one should be thrown.
    }

    @Override
    public List<User> getUsers() {
      List<User> usersCopy = new ArrayList<>();
      for (User user : users) {
        usersCopy.add(new User(user));
      }
      return usersCopy;
    }

    @Override
    public User getCurrentUser() {
      //we have no idea what this supposed to do.
      return null;
    }
  }
}
