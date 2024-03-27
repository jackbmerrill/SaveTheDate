package cs3500.calendar;


import java.util.ArrayList;
import java.util.List;
import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Time;
import cs3500.calendar.view.CentralSystemFrame;

/**
 * To run the view.
 */
public final class PlanRunner {

  public static void main(String[] args) {
    CentralSystem centralSystem = new CentralSystem();
    Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
    Location loc1 = new Location(true, "Churchill 101");
    List<String> list1 = new ArrayList<>(List.of("1201", "Frank"));
    centralSystem.addUser("Milo");
    centralSystem.addUser("Jack");
    centralSystem.addUser("Dio");
    centralSystem.generateEvent("Event1", time1, loc1, list1);
    CentralSystemFrame frame = new CentralSystemFrame(centralSystem);
    frame.makeVisible();
  }
}
