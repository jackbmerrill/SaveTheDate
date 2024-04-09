package cs3500.calendar;


import java.util.ArrayList;
import java.util.List;

import cs3500.calendar.controller.Controller;
import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.strategy.AnyTimeSchedulingStrategies;
import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Location;
import cs3500.calendar.strategy.SchedulingStrategies;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.WorkHoursSchedulingStrategy;

/**
 * To run the view.
 */
public final class PlanRunner {

  /**
   * To represent the main runnable calendar.
   * @param args args
   */
  public static void main(String[] args) {
    CentralSystem centralSystem = new CentralSystem();
    Time time1 = new Time(Day.MONDAY, 800, Day.MONDAY, 2359);
    Time time2 = new Time(Day.FRIDAY, 800, Day.SUNDAY, 2359);
    Location loc1 = new Location(true, "Churchill 101");
    List<String> list1 = new ArrayList<>(List.of("Jack", "Frank"));
    centralSystem.addUser("Milo");
    centralSystem.addUser("Dio");
    centralSystem.generateEvent("Event1", time1, loc1, list1);
    centralSystem.generateEvent("Event2", time2, loc1, list1);

    SchedulingStrategies strategy = null;
    if (args.length > 0) {
      if ("anytime".equalsIgnoreCase(args[0])) {
        strategy = new AnyTimeSchedulingStrategies();
      } else if ("workhours".equalsIgnoreCase(args[0])) {
        strategy = new WorkHoursSchedulingStrategy();
      } else {
        System.err.println("Not a valid strategy.");
        return;
      }
    }

    IFeatures controller = new Controller(centralSystem, strategy);
  }
}
