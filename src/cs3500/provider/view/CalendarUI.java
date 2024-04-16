package cs3500.provider.view;

import cs3500.provider.controller.Features;
import cs3500.provider.model.Event;
import model.ReadOnlyPlannerModel;
import cs3500.provider.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The CalendarUI class provides a graphical user interface for displaying a calendar
 * with scheduled events. It allows users to view events
 * scheduled in the planner model,
 * create new events, and save the calendar to a file. This class extends JFrame and implements
 * the PlannerGrid interface for integration with
 * the application's model-view-controller architecture.
 */
public class CalendarUI extends JFrame implements PlannerGrid {

  private static final Map<String, Integer> DAY_TO_INT = Map.of(
      "Sunday", 1,
      "Monday", 2,
      "Tuesday", 3,
      "Wednesday", 4,
      "Thursday", 5,
      "Friday", 6,
      "Saturday", 7
  );


  private ReadOnlyPlannerModel model;
  private Features features;

  private User currentUser;
  private JTable table;
  private HashMap<Integer, Event> intEvent; // Declare this as a class-level attribute.


  /**
   * Constructs a CalendarUI object with a reference to the ReadOnlyPlannerModel.
   *
   * @param model The read-only planner model used to retrieve user and event data.
   */
  public CalendarUI(ReadOnlyPlannerModel model) {
    this.model = model;
    System.out.println(model.users);

    if (!model.getUsers().isEmpty()) {
      this.currentUser = model.getUsers().get(0);
    }
  }

  public void setFeatures(Features features) {
    this.features = features;
  }


  public void render() {
    SwingUtilities.invokeLater(() -> this.createAndShowGUI());
  }

  /**
   * Sets up and displays the calendar's graphical user interface.
   * This includes initializing the main JFrame,
   * configuring the JTable for event display,
   * and adding UI components like buttons and menu items with their action listeners.
   */
  public void createAndShowGUI() {
    JFrame frame = new JFrame("Calendar");
    extractedShowGUI(frame);

    String[] columns = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};
    Object[][] data = new Object[1440][7]; // Assuming 1440 minutes per day

    DefaultTableModel tableModel = new DefaultTableModel(data, columns);
    table = new JTable(tableModel);

    table.setRowHeight(2);
    setupCustomRenderer();
    setupListeners();
    setupComponents(frame);

    updateView();  // Load initial data for currentUser
    frame.pack();
    frame.setSize(800, 600);
    frame.setVisible(true);
  }


  private void extractedShowGUI(JFrame frame) {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    // Create a menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    // Create and add "Open Calendar" menu item
    JMenuItem openItem = new JMenuItem("Add calender");
    openItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Select calendar file");
      int returnValue = fileChooser.showOpenDialog(this);

      if (returnValue == JFileChooser.APPROVE_OPTION) {
        features.addUser(fileChooser.getSelectedFile().getAbsolutePath());
        render();
      }
    });

    fileMenu.add(openItem);

    JMenuItem saveItem = new JMenuItem("Save Schedule");
    saveItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Save Schedule");
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnValue = fileChooser.showSaveDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        String directoryPath = fileChooser.getSelectedFile().getAbsolutePath();
        features.saveSchedules(currentUser.getName(), directoryPath);
        JOptionPane.showMessageDialog(this, "Schedule saved successfully.");
      }
    });
    fileMenu.add(saveItem);


    menuBar.add(fileMenu);
    frame.setJMenuBar(menuBar);
  }

  private void setupCustomRenderer() {
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus,
                                                     int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value,
            isSelected, hasFocus, row, column);

        // Apply color based on event presence
        int currTime = column * 1440 + row;
        component.setBackground(intEvent != null && intEvent
            .containsKey(currTime) ? Color.RED : Color.WHITE);

        // Border logic to highlight each hour
        if ((row + 1) % 60 == 0) { // Every hour
          setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.GRAY));
        } else {
          setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY));
        }

        return component;
      }
    });
  }

  private void setupListeners() {
    table.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int column = table.columnAtPoint(e.getPoint());
        int currTime = column * 1440 + row;
        Event clickedEvent = intEvent.get(currTime);
        if (clickedEvent != null) {
          EventFrame eventFrame = new EventFrame(clickedEvent, model, features, null);
          eventFrame.render();
        }
      }
    });
  }

  private void setupComponents(JFrame frame) {
    frame.add(new JScrollPane(table), BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel();
    JComboBox<String> dropdownMenu = new JComboBox<>(model.getUsers()
        .stream().map(User::getName).toArray(String[]::new));
    dropdownMenu.addActionListener(e -> {
      String selectedUserName = (String) dropdownMenu.getSelectedItem();
      currentUser = model.getUser(selectedUserName);
      updateView();
    });
    buttonPanel.add(dropdownMenu);
    JButton createEventButton = new JButton("Create event");
    createEventButton.addActionListener(e -> new EventFrame(null, model, features, this).render());
    buttonPanel.add(createEventButton);
    frame.add(buttonPanel, BorderLayout.SOUTH);
    frame.pack();
    frame.setSize(800, 600);
    frame.setVisible(true);

    JButton scheduleEvent = new JButton("Schedule event");
    scheduleEvent.addActionListener(e -> new AutoEventFrame(model).render());
    buttonPanel.add(scheduleEvent);
    frame.add(buttonPanel, BorderLayout.SOUTH);
    frame.pack();
    frame.setSize(800, 600);
    frame.setVisible(true);
  }

  /**
   * Updates the calendar view based on the current user's events.
   * This method clears the current calendar view and repopulates it with the events
   * of the selected user. It effectively refreshes the display to represent any changes
   * in the event data or selected user.
   */
  public void updateView() {
    clearCalendarView();
    intEvent = printEventsForCurrentUser();  // Update intEvent here.
    fillCalendarWithEvents(intEvent);
  }

  private void clearCalendarView() {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    model.setRowCount(1440);
  }

  private HashMap<Integer, Event> printEventsForCurrentUser() {
    HashMap<Integer, Event> timeToEvent = new HashMap<>();

    if (currentUser != null && currentUser.getSchedule() != null) {
      List<Event> events = currentUser.getSchedule()
          .getEvents(); // Get the events list of the current user
      for (Event e : events) {
        int startDay = DAY_TO_INT.get(e.getStartDay()) - 1;
        int startHour = Integer.parseInt(e.getStartTime().substring(0, 2));
        int startMinutes = Integer.parseInt(e.getStartTime().substring(2, 4));
        int startTimeMinutes = startDay * 1440 + startHour * 60 + startMinutes;

        int endDay = DAY_TO_INT.get(e.getEndDay()) - 1;
        int endHour = Integer.parseInt(e.getEndTime().substring(0, 2));
        int endMinutes = Integer.parseInt(e.getEndTime().substring(2, 4));
        int endTimeMinutes = endDay * 1440 + endHour * 60 + endMinutes;

        if (endTimeMinutes > startTimeMinutes) {
          for (int i = startTimeMinutes; i <= endTimeMinutes; i++) {
            timeToEvent.put(i, e);
          }
        } else {
          for (int i = startTimeMinutes; i <= 10080; i++) {
            timeToEvent.put(i, e);
          }
        }
      }
    }

    return timeToEvent;
  }


  /**
   * Helper method to calculate total minutes
   * from the start of the week given
   * a time in HHMM format and the day index.
   *
   * @param time     String in HHMM format
   * @param dayIndex Index of the day starting from 0 (Sunday) to 6 (Saturday)
   * @return Total minutes from the start of the week
   */
  private int calculateMinutesFromWeekStart(String time, int dayIndex) {
    int hour = Integer.parseInt(time.substring(0, 2));
    int minutes = Integer.parseInt(time.substring(2, 4));
    return dayIndex * 1440 + hour * 60 + minutes; // Correct calculation
  }

  /**
   * Fills the calendar with events for the currently selected user.
   * This method maps
   * event times to table cells, coloring
   * and labeling them to represent scheduled events.
   * It uses the internal event mapping to determine
   * which table cells correspond to events.
   *
   * @param events The mapping of minute indices to events for the current user.
   */
  private void fillCalendarWithEvents(HashMap<Integer, Event> events) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Clear existing content
    model.setRowCount(1440); // Reset rows for a new day

    for (Map.Entry<Integer, Event> entry : events.entrySet()) {
      int minuteOfDay = entry.getKey() % 1440;
      int dayOfWeek = entry.getKey() / 1440;
      if (dayOfWeek < 7) { // Ensure we do not exceed the column index
        model.setValueAt(entry.getValue().getName(), minuteOfDay, dayOfWeek);
      }
    }

    table.repaint(); // Ensure the table updates visually
  }


}
