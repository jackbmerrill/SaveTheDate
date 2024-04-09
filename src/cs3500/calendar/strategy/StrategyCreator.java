package cs3500.calendar.strategy;

public final class StrategyCreator {

  public enum Strategy { ANYTIME, WORKHOURS }

  public static SchedulingStrategies strategyCreator(Strategy strat) {
    switch (strat) {
      case ANYTIME:
        return new AnyTimeSchedulingStrategies();
      case WORKHOURS:
        return new WorkHoursSchedulingStrategy();
      default:
        throw new IllegalArgumentException("Invalid strategy");
    }
  }
}
