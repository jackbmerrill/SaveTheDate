package cs3500.calendar.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.Schedule;

public class SchedulePanel extends JPanel {

  private final Schedule schedule;

  private static final int START_Y = 50;
  private static final int ROW_HEIGHT = 100;
  private static final int COL_WIDTH = 100;
  private static final int HOURS_IN_DAY = 24;

  public SchedulePanel(Schedule schedule) {
    this.schedule = schedule;
    this.setSize(new Dimension(700, 1200));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawSchedule(g);
    drawEvents(g);
  }

  private void drawSchedule(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    for (int i = 1; i < 7; i++) {
      g2d.drawLine(i * getWidth() / 7, 0, i * getWidth() / 7, getHeight());
    }

    for (int height = 0; height < 24; height++) {
      if (height % 4 == 0) {
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawLine(0, height * 25, getWidth(), height * 25);
        g2d.setStroke(oldStroke);
      }
      g2d.drawLine(0, height * 25, getWidth(), height * 25);
    }
  }

  private void drawEvents(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
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
