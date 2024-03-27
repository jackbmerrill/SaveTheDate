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
import cs3500.calendar.model.Time;

public class SchedulePanel extends JPanel {

  private Schedule schedule;

  public SchedulePanel(Schedule schedule) {
    super();
    this.schedule = schedule;
    this.setSize(new Dimension(700, 600));
  }

  public void updateSchedule(Schedule schedule) {
    this.schedule = schedule;
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawEvents(g);
    drawSchedule(g);
  }

  private void drawSchedule(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();

    for (int i = 1; i < 7; i++) {
      g2d.drawLine(i * getWidth() / 7, 0, i * getWidth() / 7, 24*25);
    }

    for (int height = 0; height <= 24; height++) {
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
    Graphics2D g2d = (Graphics2D) g.create();
    List<Event> events = schedule.getEventsAtTime(null);
    g2d.setColor(Color.red);
    for (Event event : events) {
      Time time = event.getTime();
      if (time.getStartDay().equals(time.getEndDay())) {
        int height = convertTime(time.getEndTime()) - convertTime(time.getStartTime());
        g2d.fillRect((time.getStartDay().order() - 1) * 100, convertTime(time.getStartTime()),
                100, height);
      }
      if (time.getStartDay().order() < time.getEndDay().order()) {
        g2d.fillRect((time.getStartDay().order() - 1) * 100, convertTime(time.getStartTime()),
                100, getHeight() - convertTime(time.getStartTime()));
        for (int days = time.getStartDay().order() + 1; days < time.getEndDay().order(); days++) {
          g2d.fillRect((days - 1) * 100, 0,
                  100, getHeight());
        }
        g2d.fillRect((time.getEndDay().order() - 1) * 100, 0,
                100, convertTime(time.getEndTime()));
      }
    }
  }

  private int convertTime(int hour) {
    int translated = (hour / 100) * 100;
    return (translated + (int) (((hour % 100) / 60.0) * 100.0)) / 4;
  }

}
