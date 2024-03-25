package cs3500.calendar.view;

import cs3500.calendar.model.CentralSystem;
import cs3500.calendar.model.ReadOnlyCentralSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CentralSystemFrame extends JFrame {
  private ReadOnlyCentralSystem model;
  private JComboBox<String> userScheduleDropdown;
  private JButton loadButton;
  private JButton saveButton;
  private JPanel schedulePanel;

  public CentralSystemFrame(ReadOnlyCentralSystem model) {
    super("Central System Frame");
    this.model = model;
    this.initializeComponents();
    this.layoutComponents();
    this.attachListeners();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(800, 600); //initial frame size
    this.setLocationRelativeTo(null); //window centering
  }

  private void initializeComponents() {
    //components
    userScheduleDropdown = new JComboBox<>();
    loadButton = new JButton("Load XML");
    saveButton = new JButton("Save XML");
    schedulePanel = new JPanel();

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
          try {
            ((CentralSystem) model).loadSchedulesFromXML(fileChooser.getSelectedFile().getAbsolutePath());
            //update with new schedules
          } catch (Exception ex) {
            ex.printStackTrace();
          }
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
        }
      }
    });
  }
}