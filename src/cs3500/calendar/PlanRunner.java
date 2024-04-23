package cs3500.calendar;

import java.util.ArrayList;
import java.util.List;

import cs3500.calendar.controller.Controller;
import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.AnyTimeSchedulingStrategySaturday;
import cs3500.calendar.strategy.SchedulingStrategies;
import cs3500.calendar.strategy.StrategyCreator;
import cs3500.calendar.view.SaturdayCentralSystemFrame;
import cs3500.provider.view.CentralFrameAdapter;

/**
 * This allows the user to run the view of the calendar. The user can create, remove, schedule etc.
 */
public final class PlanRunner {

  /**
   * To represent the main runnable calendar.
   * @param args the desired strategy for the controller.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException("No valid input");
    }
    SchedulingStrategies strategy;
    switch (args[0].toLowerCase()) {
      case "anytime":
        strategy = StrategyCreator.strategyCreator(StrategyCreator.Strategy.ANYTIME);
        break;
      case "workhours":
        strategy = StrategyCreator.strategyCreator(StrategyCreator.Strategy.WORKHOURS);
        break;
      default:
        throw new IllegalArgumentException("Invalid strategy type");
    }

    Time time1 = new Time(Day.MONDAY, 1200, Day.MONDAY, 1300);
    Time time2 = new Time(Day.WEDNESDAY, 0000, Day.FRIDAY, 2300);

    Location loc1 = new Location(true, "Churchill 101");
    List<String> list1 = new ArrayList<>(List.of("Jack", "Milo", "Jake", "Lucia", "Dio"));
    Schedule schedule1021 = new Schedule("1021");
    CentralSystem centralSystem = new CentralSystem();
    centralSystem.generateEvent("Event1", time1, loc1, list1);
    centralSystem.generateEvent("Event2", time2, loc1, list1);

    if (args.length > 1) {
      if (args[1].toLowerCase().equals("provider")) {
        new Controller(new CentralFrameAdapter(centralSystem), centralSystem, strategy);
      } else if (args[1].toLowerCase().equals("saturday")) {
        if (args[0].toLowerCase().equals("anytime")) {
          strategy = new AnyTimeSchedulingStrategySaturday();
        }
        new Controller(new SaturdayCentralSystemFrame(centralSystem), centralSystem, strategy);
      }
    } else {
      new Controller(centralSystem, strategy);
    }
  }
}
