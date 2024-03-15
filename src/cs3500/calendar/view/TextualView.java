package cs3500.calendar.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;


/**
 * Represents a textual view of schedules and their events along with all of their details
 * in a CentralSystem. Each users unique schedule and their included events are displayed in a
 * user-friendly readable textual format. It is ordered by time and date.
 */
public class TextualView {

  private final CentralSystem centralSystem;

  /**
   * Constructor for the textual view for the system with a given CentralSystem.
   * @param centralSystem the central system which contains all schedules that need to be viewed
   *                      in a textual format.
   */

  public TextualView(CentralSystem centralSystem) {
    this.centralSystem = centralSystem;
  }


  /**
   * Generates a textual representation of all the schedules of the unique users within
   * the central system. It takes each user one by one and lists all the events they have planned
   * sorted in chronological order taking both day and time into account. Additionally, each unique event
   * mentions its name, time, location, online status, and invitees for every unique user that is a participant
   * and formats it in a readable textual format.
   *
   * Furthermore, when an event spans multiple days it will show up every day that its active for the user
   * with the starting and finishing day and time in order to give the user a daily reminder of what
   * their already started but unfinished events are so that they dont have to look back into their schedule.
   *
   * @return a String which contains a textual representation in a formated view of all of the schedules in
   * a given system
   */
  public String generateTextualView() {
    StringBuilder textualViewBuilder = new StringBuilder();
    Map<String, Schedule> systemSchedules = centralSystem.getSystem();

    for (Map.Entry<String, Schedule> entry : systemSchedules.entrySet()) {
      String userId = entry.getKey();
      Schedule schedule = entry.getValue();
      Map<Day, List<Event>> eventsByDay = schedule.getEventsByDay();

      textualViewBuilder.append("User: ").append(userId).append("\n");

      for (Day day : Day.values()) {
        List<Event> eventsForDay = eventsByDay.get(day);

        if (eventsForDay != null) {
          Collections.sort(eventsForDay, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
              return Integer.compare(e1.getTime().getStartTime(), e2.getTime().getStartTime());
            }
          });
        }

        textualViewBuilder.append(day.toString()).append(":\n");

        if (eventsForDay != null && !eventsForDay.isEmpty()) {
          for (Event event : eventsForDay) {
            String formattedStartTime = Time.formatTime(event.getTime().getStartTime());
            String formattedEndTime = Time.formatTime(event.getTime().getEndTime());
            String niceStartTime = formattedStartTime.substring(0, 2) + ":" + formattedStartTime.
                    substring(2);
            String niceEndTime = formattedEndTime.substring(0, 2) + ":" + formattedEndTime.
                    substring(2);

            textualViewBuilder.append("    name: ").append(event.getName()).append("\n")
                    .append("    time: ").append(event.getTime().getStartDay()).append(": ")
                    .append(niceStartTime).append(" -> ")
                    .append(event.getTime().getEndDay()).append(": ")
                    .append(niceEndTime).append("\n")
                    .append("    location: ").append(event.getLocation().getPlace()).append("\n")
                    .append("    online: ").append(event.getLocation().isOnline()).append("\n")
                    .append("    invitees: \n");

            for (String user : event.getUsers()) {
              textualViewBuilder.append("        ").append(user).append("\n");
            }
          }
        } else {
          textualViewBuilder.append("    No events\n");
        }
      }
      textualViewBuilder.append("\n");
    }

    return textualViewBuilder.toString();
  }
}


