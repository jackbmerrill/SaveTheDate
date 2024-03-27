package cs3500.calendar.view;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Schedule;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SchedulePanel extends JPanel {

  private final Schedule schedule;

  private static final int START_Y = 50;
  private static final int ROW_HEIGHT = 100;
  private static final int COL_WIDTH = 100;
  private static final int HOURS_IN_DAY = 24;

  public SchedulePanel(Schedule schedule) {
    this.schedule = schedule;
    this.setPreferredSize(new Dimension(800, 2400));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawSchedule(g);
  }

  private void drawSchedule(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    for (int i = 0; i < Day.values().length; i++) {
      g2d.drawString(Day.values()[i].toString(), COL_WIDTH * i, START_Y);
    }

    for (int hour = 0; hour < HOURS_IN_DAY; hour++) {
      int y = START_Y + (hour * ROW_HEIGHT);
      g2d.drawLine(0, y, getWidth(), y);
      g2d.drawString(hour + ":00", 0, y - 5);
    }

    //draw events
    Map<Day, List<Event>> eventsByDay = schedule.getEventsByDay();
    for (Map.Entry<Day, List<Event>> entry : eventsByDay.entrySet()) {
      int dayIndex = entry.getKey().ordinal();
      for (Event event : entry.getValue()) {

        int eventStartHour = event.getTime().getStartTime();
        int eventEndHour = event.getTime().getEndTime();
        int eventDuration = eventEndHour - eventStartHour;

        int x = dayIndex * COL_WIDTH;
        int y = START_Y + (eventStartHour * ROW_HEIGHT);
        int height = eventDuration * ROW_HEIGHT;

        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y, COL_WIDTH, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString(event.getName(), x + 5, y + 20);
      }
    }
  }
}
