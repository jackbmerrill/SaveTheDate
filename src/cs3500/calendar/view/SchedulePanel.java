package cs3500.calendar.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.calendar.model.Day;
import cs3500.calendar.model.Event;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Schedule;
import cs3500.calendar.model.Time;

public class SchedulePanel extends JPanel implements MouseListener {

  private Schedule schedule;
  private final ReadOnlyCentralSystem system;

  public SchedulePanel(Schedule schedule, ReadOnlyCentralSystem system) {
    super();
    this.schedule = schedule;
    this.system = system;
    this.setSize(new Dimension(700, 600));
    addMouseListener(this);
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


  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("COORDS X,Y " + e.getX() + ", " + e.getY());
    int day = e.getX() / 100 + 1;
    int time = e.getY() * 4;
    int minute = time % 100;
    int hour = time / 100;
    if (minute >= 60) {
      hour += minute / 60;
      minute %= 60;
    }
    time = hour * 100 + minute;
    int endTime;
    if (time % 100 < 59) {
      endTime = time + 1;
    } else {
      endTime = time + 100;
    }
    System.out.println("Day" + day + ", Time" + time + ", EndTime: " + endTime);
    List<Event> events = schedule.getEventsAtTime(
            new Time(Day.getDay(day), time, Day.getDay(day), endTime));
    if (events.isEmpty()) {
      return;
    }
    IEventFrame eventFrame = new EventFrame(this.system, events.get(0));
    eventFrame.makeVisible();
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
