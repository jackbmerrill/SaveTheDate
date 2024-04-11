package cs3500.calendar.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import cs3500.calendar.model.Event;
import cs3500.calendar.model.ICentralSystem;
import cs3500.calendar.model.IEvent;
import cs3500.calendar.model.Location;
import cs3500.calendar.strategy.SchedulingStrategies;
import cs3500.calendar.view.CentralSystemFrame;
import cs3500.calendar.view.ICentralSystemFrame;

/**
 * Implementation of the IFeatures interface to create a controller which delegates
 * the interactions between the view and the model of the Calendar package. Takes in all
 * input information from the view and passes it to the model, catching any exceptions
 * or errors and displays them in the view.
 */
public class Controller implements IFeatures {

  private final ICentralSystem model;
  private final ICentralSystemFrame view;
  private SchedulingStrategies strategy;

  /**
   * Constructor for the controller. Takes in a model of the ICentralSystem interface and
   * creates a new view with the model using the view we created as the default.
   * @param model the model to be taken in
   */
  public Controller(ICentralSystem model, SchedulingStrategies strategy) {
    this.model = Objects.requireNonNull(model);
    this.view = new CentralSystemFrame(model);
    this.strategy = strategy;
    this.view.setFeature(this);
    this.view.makeVisible(true);
  }

  /**
   * Constructor that enables the specification of the view, rather than using the default
   * view. Enables use of mock views and other versions for testing.
   * @param view     the view to be taken in
   * @param model    the model to be taken in
   * @param strategy the strategy to be taken in
   */
  public Controller(ICentralSystemFrame view, ICentralSystem model,
                    SchedulingStrategies strategy) {
    this.model = Objects.requireNonNull(model);
    this.view = view;
    this.strategy = strategy;
    this.view.setFeature(this);
    this.view.makeVisible(true);
    this.view.refresh();
  }

  @Override
  public void createEvent(IEvent event) {
    try {
      model.generateEvent(event.getName(), event.getTime(),
              event.getLocation(), event.getUsers());
      this.view.refresh();
    } catch (IllegalStateException e) {
      view.createErrorBox(e.getMessage());
    }
  }

  @Override
  public void modifyEvent(IEvent originalEvent, IEvent modifiedEvent) {
    String host = originalEvent.getHost();
    String name = originalEvent.getName();
    try {
      if (!originalEvent.getName().equals(modifiedEvent.getName())) {
        this.model.updateEventName(host, name, modifiedEvent.getName());
        name = modifiedEvent.getName();
      }
      if (!originalEvent.getTime().equals(modifiedEvent.getTime())) {
        this.model.updateEventTime(host, name, modifiedEvent.getTime());
      }
      if (!originalEvent.getLocation().equals(modifiedEvent.getLocation())) {
        this.model.updateEventLocation(host, name, modifiedEvent.getLocation());
      }
      //removing users
      for (String user : originalEvent.getUsers()) {
        if (!modifiedEvent.getUsers().contains(user)) {
          this.model.removeEvent(user, name);
        }
      }
      //adding users
      for (String user : modifiedEvent.getUsers()) {
        if (!originalEvent.getUsers().contains(user)) {
          this.model.addEventToUser(user, name);
        }
      }
      this.view.refresh();
    } catch (IllegalStateException | IllegalArgumentException e) {
      this.view.createErrorBox(e.getMessage());
    }
  }

  @Override
  public void scheduleEvent(String name, int time, Location loc, List<String> users) {
    if (strategy == null) {
      view.createErrorBox("Scheduling Strategy is not set");
      return;
    }
    try {
      IEvent event = strategy.findTime(model, name, time, loc, users);
      model.generateEvent(event.getName(), event.getTime(), event.getLocation(), event.getUsers());
      this.view.refresh();
    } catch (IllegalStateException e) {
      view.createErrorBox(e.getMessage());
    }
  }

  @Override
  public void loadXML(String filePath) {
    try {
      this.model.loadSchedulesFromXML(filePath);
    } catch (IOException e) {
      this.view.createErrorBox(e.getMessage());
    }
  }

  @Override
  public void saveXML(String filePath, String userID) {
    try {
      this.model.saveSchedulesToXML(filePath, userID);
    } catch (IOException e) {
      this.view.createErrorBox(e.getMessage());
    }
  }

  @Override
  public void removeEvent(IEvent event, String user) {
    try {
      this.model.removeEvent(user, event.getName());
      this.view.refresh();
    } catch (IllegalStateException e) {
      this.view.createErrorBox(e.getMessage());
    }
  }
}
