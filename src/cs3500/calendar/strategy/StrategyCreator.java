package cs3500.calendar.strategy;

/**
 * Represents a scheduling strategy that chooses the type of strategy to use based on the specified
 * input.
 */
public final class StrategyCreator {

  /**
   * To represent the types of scheduling strategies.
   */
  public enum Strategy { ANYTIME, WORKHOURS }

  /**
   * Factory method to decide on the strategy.
   * @param strat strategy.
   * @return an instance of the necessary strategy
   */
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
