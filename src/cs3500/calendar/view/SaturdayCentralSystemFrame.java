package cs3500.calendar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Schedule;

/**
 * To represent the frame for the central system. This class works the same as the
 * CentralSystemFrame class, but sets the schedule panel as a Saturday schedule panel (starting on
 * Saturday rather than Sunday.) It is unable to directly modify the model, and only able to access
 * the observable features. The central system frame consists of four buttons, enabling creation and
 * scheduling of events, and import and export of XMLs.
 * The schedule panel handles direct interaction with existing events and is a core component
 * of the central frame. The frame is unable to be resized and upon closing, quits the program.
 * The current user can also be switched via dropdown. Listeners can be replaced by the view.
 */
public class SaturdayCentralSystemFrame extends CentralSystemFrame {

  /**
   * Constructor for the central system frame. Takes in a read only central system to
   * display the view without being able to modify any aspects of the model.
   *
   * @param model the calendar moel
   */
  public SaturdayCentralSystemFrame(ReadOnlyCentralSystem model) {
    super(model);
  }

  @Override
  protected void setSchedulePanel() {
    this.schedulePanel = new SaturdaySchedulePanel(new Schedule("<None>"), this.model);
  }

}
