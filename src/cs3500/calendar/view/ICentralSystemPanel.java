package cs3500.calendar.view;

/**
 * To represent the central system panel's capabilities.
 */
public interface ICentralSystemPanel {

  /**
   * Makes the view visible to the user. Shows itself, the current user's event frame.
   */
  void makeVisible();

  /**
   * Signal the view to draw itself and updates the view.
   */
  void refresh();

  /**
   * To select the user's calendar to view.
   */
  void selectUser();

  /**
   * To create a new event.
   */
  void createEvent();

  /**
   * To schedule an event.
   */
  void scheduleEvent();

  /**
   * To add a calendar to the central system.
   */
  void addCalendar();

  /**
   * To save a calendar to the central system.
   */
  void saveCalendar();

  /**
   * To select an event.
   */
  void selectEvent();





}
