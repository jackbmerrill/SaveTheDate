package cs3500.calendar;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.view.EventFrame;
import cs3500.calendar.view.IEventFrame;

/**
 * To run the view.
 */
public final class PlanRunner {
  public static void main(String[] args) {
    CentralSystem centralSystem = new CentralSystem();
    IEventFrame eventFrame = new EventFrame(new ArrayList<>(Arrays.asList("Jack", "Milo", "Dio")));
    eventFrame.makeVisible();
  }
}
