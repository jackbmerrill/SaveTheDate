package cs3500.calendar.view;

import java.io.IOException;
import java.util.List;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;

/**
 * Implements both the central view class and the features class in order to test that
 * when given the correct inputs, the controller does as intended. A mock class.
 * Passes all inputs to the controller methods straight to the given controller.
 */
public class MockView implements ICentralSystemFrame, IFeatures {

  private IFeatures controller;
  private final Appendable appendable;

  public MockView(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void makeVisible(boolean visible) {
    return;
  }

  @Override
  public void setFeature(IFeatures feature) {
    this.controller = feature;
  }

  @Override
  public void createErrorBox(String message) {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("IO Failed.");
    }
  }

  @Override
  public void refresh() {
    return;
  }

  @Override
  public void createEvent(Event event) {
    this.controller.createEvent(event);
  }

  @Override
  public void modifyEvent(Event originalEvent, Event modifiedEvent) {
    this.controller.modifyEvent(originalEvent, modifiedEvent);
  }

  @Override
  public void scheduleEvent(String name, int time, Location loc, List<String> users) {

  }

  @Override
  public void loadXML(String filePath) {
    this.controller.loadXML(filePath);
  }

  @Override
  public void saveXML(String filePath, String userID) {
    this.controller.saveXML(filePath, userID);
  }

  @Override
  public void removeEvent(Event event, String user) {
    this.controller.removeEvent(event, user);
  }
}
