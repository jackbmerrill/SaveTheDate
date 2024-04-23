package cs3500.calendar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Schedule;

public class SaturdayCentralSystemFrame extends CentralSystemFrame {

  /**
   * Constructor for the central system frame. Takes in a read only central system to
   * display the view without being able to modify any aspects of the model.
   *
   * @param model the calendar model
   */
  public SaturdayCentralSystemFrame(ReadOnlyCentralSystem model) {
    super(model);
  }

  @Override
  protected void setSchedulePanel() {
    this.schedulePanel = new SaturdaySchedulePanel(new Schedule("<None>"), this.model);
  }

  @Override
  protected void createEventButtonListener() {
    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        IEventFrame eventFrame = new SaturdayEventFrame(model,
                userScheduleDropdown.getSelectedItem().toString());
        eventFrame.setFeature(controller);
        eventFrame.makeVisible();
      }
    });
  }
}
