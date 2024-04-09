package cs3500.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.calendar.controller.Controller;
import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Location;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;
import cs3500.calendar.strategy.SchedulingStrategies;
import cs3500.calendar.strategy.StrategyCreator;


/**
 * To run the view.
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
    Time time2 = new Time(Day.WEDNESDAY, 0000, Day.SATURDAY, 1600);

    Location loc1 = new Location(true, "Churchill 101");
    List<String> list1 = new ArrayList<>(List.of("Jack", "Milo", "Jake", "Lucia", "Dio"));
    Schedule schedule1021 = new Schedule("1021");
    CentralSystem centralSystem = new CentralSystem();
    centralSystem.generateEvent("Event1", time1, loc1, list1);
    centralSystem.generateEvent("Event2", time2, loc1, list1);
    new Controller(centralSystem, strategy);
  }
}
