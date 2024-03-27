package cs3500.calendar.view;

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


public class CentralSystemFrame extends JFrame implements ICentralSystemPanel {
  private ReadOnlyCentralSystem model;
  private JComboBox<String> userScheduleDropdown;
  private JButton loadButton;
  private JButton saveButton;

  private JButton createEventButton;
  private JButton scheduleEventButton;
  private SchedulePanel schedulePanel;

  public CentralSystemFrame(ReadOnlyCentralSystem model) {
    super("Central System Frame");
    this.model = model;
    this.initializeComponents();
    this.layoutComponents();
    this.attachListeners();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(700, 700); //initial frame size
    this.setLocationRelativeTo(null); //window centering

  }

  private void initializeComponents() {
    //components
    userScheduleDropdown = new JComboBox<>();
    loadButton = new JButton("Load XML");
    saveButton = new JButton("Save XML");
    createEventButton = new JButton("Create Event");
    scheduleEventButton = new JButton("Schedule Event");
    this.schedulePanel = new SchedulePanel(new Schedule("<None>"));
    this.schedulePanel.setSize(700, 1200);
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
          System.out.println("Path to XML file: " + fileChooser.getSelectedFile().
                  getAbsolutePath());
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
          System.out.println("Directory selected for saving XML files: "
                  + fileChooser.getSelectedFile().getAbsolutePath());
        }
      }
    });

    userScheduleDropdown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        schedulePanel = new SchedulePanel(model.getUserSchedule(
                userScheduleDropdown.getSelectedItem().toString()));
        schedulePanel.setPreferredSize(new Dimension(700, 1200));
        revalidate();
        repaint();
      }
    });

    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        IEventFrame eventFrame = new EventFrame(model);
        eventFrame.makeVisible();
      }
    });

    scheduleEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        IEventFrame eventFrame = new EventFrame(model);
        eventFrame.makeVisible();
      }
    });
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

}