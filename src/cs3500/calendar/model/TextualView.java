package cs3500.calendar.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;



public class TextualView {

  private CentralSystem centralSystem;

  public TextualView(CentralSystem centralSystem) {
    this.centralSystem = centralSystem;
  }


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
            String niceStartTime = formattedStartTime.substring(0, 2) + ":" + formattedStartTime.substring(2);
            String niceEndTime = formattedEndTime.substring(0, 2) + ":" + formattedEndTime.substring(2);

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


