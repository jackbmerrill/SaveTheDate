package cs3500.calendar.view;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.Event;

/**
 * Implements both the central view class and the features class in order to test that
 * when given the correct inputs, the controller does as intended. A mock class.
 * Passes all inputs to the controller methods straight to the given controller.
 */
public class MockView implements ICentralSystemFrame, IFeatures {

  private final IFeatures controller;

  public MockView(IFeatures feature) {
    this.controller = feature;
  }

  @Override
  public void makeVisible(boolean visible) {
    return;
  }

  @Override
  public void setFeature(IFeatures feature) {
    return;
  }

  @Override
  public void createErrorBox(String message) {
    return;
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
  public void scheduleEvent() {

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