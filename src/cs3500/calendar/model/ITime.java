package cs3500.calendar.model;


/**
 * To represent time. Has a start time, end time, start day, end day. Each time can last up
 * to exactly a week. Event cannot start and end at the same time.
 */

public interface ITime {

    /**
     * Checks if the given time overlaps with this time. Returns true if there is an overlap,
     * false if there is not.
     * @param other the other time to check
     * @return true if the times overlap
     */
    boolean isOverlap(ITime other);


    /**
     * To format the time in a readable XML format.
     * @param time the time.
     * @return formats the time in a readable XML format.
     */
    static String formatTime(int time) {
      return String.format("%04d", time);
    }

    /**
     * Gets the starting time of the time. Used for the XML and the view.
     * @return an integer representation of the start time
     */
    int getStartTime();

    /**
     * Gets the ending time of the time. Used for the XML and the view.
     * @return an integer representation of the end time
     */
    int getEndTime();

    /**
     * Gets the start day of the time. Used for the XML and the view.
     * @return an enum representing the start day
     */
    Day getStartDay();

    /**
     * Gets the end day of the time. Used for the XML and the view.
     * @return an enum representing the end day
     */
    Day getEndDay();


}
