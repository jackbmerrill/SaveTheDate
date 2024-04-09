package cs3500.calendar;

import cs3500.calendar.controller.Controller;
import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.CentralSystem;
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
    CentralSystem centralSystem = new CentralSystem();
    //should the controller only take in the view.
    IFeatures controller = new Controller(centralSystem, strategy);
  }
}
