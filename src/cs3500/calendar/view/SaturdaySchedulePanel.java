package cs3500.calendar.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;


import cs3500.calendar.model.Day;
import cs3500.calendar.model.IEvent;
import cs3500.calendar.model.ISchedule;
import cs3500.calendar.model.ITime;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Time;

/**
 *  Represents the SchedulePanel of a system that starts on Saturday. Each red rectangle represents
 *  an event and clicking on said event opens it in an event frame. As events are added, the panel
 *  updates to match the schedule object. The schedule itself inside the panel can be replaced to
 *  display a different schedule.
 */
public class SaturdaySchedulePanel extends SchedulePanel {

  /**
   * Constructor for the schedule panel starting on Saturday rather than Sunday.
   * Takes in a schedule to start with and the central system with which it is working.
   * From there, draws all parts of the panel.
   *
   * @param schedule the desired starting schedule
   * @param system   the central system as read only
   */
  public SaturdaySchedulePanel(ISchedule schedule, ReadOnlyCentralSystem system) {
    super(schedule, system);
  }

  @Override
  protected void drawEvents(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    List<IEvent> events = schedule.getEventsAtTime(null);
    for (IEvent event : events) {
      ITime time = event.getTime();
      if (toggleColors && schedule.getUserID().equals(event.getHost())) {
        g2d.setColor(Color.blue);
      } else {
        g2d.setColor(Color.red);
      }

      if (time.getStartDay().equals(time.getEndDay())) {
        int height = convertTime(time.getEndTime()) - convertTime(time.getStartTime());
        g2d.fillRect((modifiedOrder(time.getStartDay()) - 1) * 100,
                convertTime(time.getStartTime()),
                100, height);
      }

      if (modifiedOrder(time.getStartDay()) < modifiedOrder(time.getEndDay())) {
        g2d.fillRect((modifiedOrder(time.getStartDay()) - 1) * 100,
                convertTime(time.getStartTime()),
                100, 24 * 25 - convertTime(time.getStartTime()));
        for (int days = modifiedOrder(time.getStartDay()) + 1;
             days < modifiedOrder(time.getEndDay()); days++) {
          g2d.fillRect((days - 1) * 100, 0,
                  100, 25 * 24);
        }
        g2d.fillRect((modifiedOrder(time.getEndDay()) - 1) * 100, 0,
                100, convertTime(time.getEndTime()));
      }

      if (modifiedOrder(time.getStartDay()) > modifiedOrder(time.getEndDay())) {
        g2d.fillRect((modifiedOrder(time.getStartDay()) - 1) * 100,
                convertTime(time.getStartTime()),
                100, 24 * 25 - convertTime(time.getStartTime()));
        for (int days = modifiedOrder(time.getStartDay()) + 1; days < 8; days++) {
          g2d.fillRect((days - 1) * 100, 0,
                  100, 24 * 25);
        }
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Day day = modifiedDay(e.getX() / 100 + 1);
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
    List<IEvent> events = schedule.getEventsAtTime(
            new Time(day, time, day, endTime));
    if (events.isEmpty()) {
      return;
    }
    IEventFrame eventFrame = new EventFrame(
            this.system, events.get(0), this.schedule.getUserID());
    eventFrame.setFeature(this.controller);
    eventFrame.makeVisible();
  }

  private int modifiedOrder(Day day) {
    switch (day) {
      case SATURDAY:
        return 1;
      case SUNDAY:
        return 2;
      case MONDAY:
        return 3;
      case TUESDAY:
        return 4;
      case WEDNESDAY:
        return 5;
      case THURSDAY:
        return 6;
      case FRIDAY:
        return 7;
      default:
        throw new IllegalArgumentException("No valid day");
    }
  }

  private Day modifiedDay(int order) {
    switch (order) {
      case 1: return Day.SATURDAY;
      case 2: return Day.SUNDAY;
      case 3: return Day.MONDAY;
      case 4: return Day.TUESDAY;
      case 5: return Day.WEDNESDAY;
      case 6: return Day.THURSDAY;
      case 7: return Day.FRIDAY;
      default: throw new IllegalArgumentException("invalid input");
    }
  }
}
