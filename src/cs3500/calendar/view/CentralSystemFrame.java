package cs3500.calendar.view;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.ReadOnlyCentralSystem;
import cs3500.calendar.model.Schedule;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * To represent the frame for the central system. Is unable to directly modify the model,
 * only able to access the observable features. The central system frame consists of four
 * buttons, enabling creation and scheduling of events, and import and export of XMLs.
 * The schedule panel handles direct interaction with existing events and is a core component
 * of the central frame. The frame is unable to be resized and upon closing, quits the program.
 * The current user can also be switched via dropdown. Listeners can be replaced by the view.
 */
public class CentralSystemFrame extends JFrame implements ICentralSystemFrame {
  protected final ReadOnlyCentralSystem model;
  private JComboBox<String> userScheduleDropdown;
  private JButton loadButton;
  private JButton saveButton;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JButton toggleHostColor;
  protected SchedulePanel schedulePanel;
  private IFeatures controller;

  /**
   * Constructor for the central system frame. Takes in a read only central system to
   * display the view without being able to modify any aspects of the model.
   * @param model the calendar model
   */
  public CentralSystemFrame(ReadOnlyCentralSystem model) {
    super("Central System Frame");
    this.model = model;
    this.initializeComponents();
    setSchedulePanel();
    this.layoutComponents();
    this.attachListeners();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(700, 700); //initial frame size
    this.setLocationRelativeTo(null); //window centering
  }

  protected void setSchedulePanel() {
    this.schedulePanel = new SchedulePanel(new Schedule("<None>"), this.model);
  }

  private void initializeComponents() {
    //components
    userScheduleDropdown = new JComboBox<>();
    loadButton = new JButton("Load XML");
    saveButton = new JButton("Save XML");
    createEventButton = new JButton("Create Event");
    scheduleEventButton = new JButton("Schedule Event");
    toggleHostColor = new JButton("Toggle host color");
    this.setResizable(false);
    //users and schedules
    for (String user : model.getUsers()) {
      userScheduleDropdown.addItem(user);
    }
  }

  private void layoutComponents() {
    //components
    this.setLayout(new BorderLayout());
    this.add(userScheduleDropdown, BorderLayout.NORTH);
    this.add(schedulePanel, BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(createEventButton);
    buttonPanel.add(scheduleEventButton);
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(toggleHostColor);
    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  private void attachListeners() {
    //component listeners
    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(CentralSystemFrame.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          controller.loadXML(fileChooser.getSelectedFile().getAbsolutePath());
        }
      }
    });

    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showSaveDialog(CentralSystemFrame.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          controller.saveXML(fileChooser.getSelectedFile().getAbsolutePath(),
                  userScheduleDropdown.getSelectedItem().toString());
        }
      }
    });

    userScheduleDropdown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        schedulePanel.updateSchedule(model.getUserSchedule(
                userScheduleDropdown.getSelectedItem().toString()));
        schedulePanel.updateUI();
      }
    });

    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        IEventFrame eventFrame = new EventFrame(model,
                userScheduleDropdown.getSelectedItem().toString());
        eventFrame.setFeature(controller);
        eventFrame.makeVisible();
      }
    });

    scheduleEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        IEventFrame schedule = new SchedulingFrame(model);
        schedule.setFeature(controller);
        schedule.makeVisible();
      }
    });

    toggleHostColor.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        schedulePanel.toggleHostColor();
      }
    });

  }



  @Override
  public void makeVisible(boolean visible) {
    this.setVisible(visible);
  }

  @Override
  public void setFeature(IFeatures feature) {
    this.controller = feature;
    this.schedulePanel.setFeature(feature);
  }

  @Override
  public void createErrorBox(String message) {
    new ErrorBox(message);
  }

  @Override
  public void refresh() {
    this.schedulePanel.updateSchedule(model.getUserSchedule(
            userScheduleDropdown.getSelectedItem().toString()));
    this.repaint();
  }

}