package gui;

import java.util.*;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import base.Aircraft;
import base.MilitaryBase;
import base.Pilot;

public class JApplication {

    private MilitaryBase base;

    private JFrame window = new JFrame();

    private JButton grandAccessButton = new JButton("Grand pilot admission");
    private JButton revokeAccessButton = new JButton("Revoke pilot admission");
    private JButton editPilotsListButton = new JButton("Edit pilots list");
    private JButton editAircraftsListButton = new JButton("Edit aircrafts list");
    private JButton printBaseButton = new JButton("Show base");
    private JButton exitButton = new JButton("Exit programm");


    public JApplication()
            throws java.sql.SQLException, java.lang.ClassNotFoundException {

        base = new MilitaryBase();

        window.setSize(600, 400);
        window.setTitle("MilitaryBase");
        window.setLayout(new GridLayout(6, 1));

        //JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(6, 1));
        //panel.setSize(600, 400);

        window.add(grandAccessButton);
        grandAccessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grandAccess();
            }
        });

        window.add(revokeAccessButton);
        revokeAccessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                revokeAccess();
            }
        });

        window.add(editPilotsListButton);
        editPilotsListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editPilotsList();
            }
        });

        window.add(editAircraftsListButton);
        editAircraftsListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editAircraftsList();
            }
        });

        window.add(printBaseButton);
        printBaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printBase();
            }
        });

        window.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options = { "Yes", "No" };
                int rc = JOptionPane.showOptionDialog(
                        window, "Close window?",
                        "Confirm", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (rc == 0) {
                    window.setVisible(false);
                    System.exit(0);
                }
            }
        });

        //window.add(panel);
        //window.pack();
        //JTextArea table = new JTextArea(6, 5);
        //window.add(table);

        //Разместим программу по центру
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent event) {}

            public void windowClosed(WindowEvent event) {}

            public void windowDeactivated(WindowEvent event) {}

            public void windowDeiconified(WindowEvent event) {}

            public void windowIconified(WindowEvent event) {}

            public void windowOpened(WindowEvent event) {}

            public void windowClosing(WindowEvent event)
            {
                Object[] options = { "Yes", "No" };
                int rc = JOptionPane.showOptionDialog(
                        event.getWindow(), "Close window?",
                        "Confirm", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (rc == 0) {
                    event.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
        });

    }

    public void run() {
        window.setVisible(true);
    }

    public void close() {
        window.setVisible(false);
        System.exit(0);
    }

    public void grandAccess() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 100);
        dWindow.setTitle("Grand access");
        dWindow.setLayout(new GridLayout(2, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JTextField input = new JTextField("Pilot's name", 30);
        dWindow.add(input);

        JButton butt = new JButton("Grand access to pilot");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = input.getText();
                    Pilot pilot = new Pilot(name);
                    List<Pilot> pilotList = base.getPilotsList();

                    if (pilotList.contains(pilot)) {
                        Pilot basePilot = new Pilot("");

                        for(Pilot p : pilotList)
                            if (p.getName().equals(name))
                                basePilot = p;

                        if (basePilot.getAccess()) {
                            throw new Exception("Pilot already has access.");
                        }
                        else {
                            base.grandAdmission(basePilot);
                            JOptionPane.showConfirmDialog(dWindow, "Admission was successfully issued",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else throw new Exception("No such pilot in base.");
                }
                catch (Exception ex) {
                    JOptionPane.showConfirmDialog(dWindow, ex.getMessage(), "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    dWindow.setVisible(false);
                }
            }
        });
    }

    public void revokeAccess() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 100);
        dWindow.setTitle("Revoke access");
        dWindow.setLayout(new GridLayout(2, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JTextField input = new JTextField("Pilot's name", 30);
        dWindow.add(input);

        JButton butt = new JButton("Revoke access to pilot");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = input.getText();
                    Pilot pilot = new Pilot(name);
                    List<Pilot> pilotList = base.getPilotsList();

                    if (pilotList.contains(pilot)) {
                        Pilot basePilot = new Pilot("");

                        for(Pilot p : pilotList)
                            if (p.getName().equals(name))
                                basePilot = p;

                        if (basePilot.getAccess()) {
                            throw new Exception("Pilot already has not access.");
                        }
                        else {
                            base.grandAdmission(basePilot);
                            JOptionPane.showConfirmDialog(dWindow, "Admission was successfully issued",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else throw new Exception("No such pilot in base.");
                }
                catch (Exception ex) {
                    JOptionPane.showConfirmDialog(dWindow, ex.getMessage(), "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    dWindow.setVisible(false);
                }
            }
        });
    }

    public void editPilotsList() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 200);
        dWindow.setTitle("Edit pilots list");
        dWindow.setLayout(new GridLayout(4, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JTextField input = new JTextField("Pilot's name", 30);
        dWindow.add(input);

        JRadioButton addButt = new JRadioButton("Add pilot");
        JRadioButton delButt = new JRadioButton("Delete pilot");

        ButtonGroup group = new ButtonGroup();
        group.add(addButt);
        group.add(delButt);

        dWindow.add(addButt);
        dWindow.add(delButt);

        JButton butt = new JButton("Edit");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (addButt.isSelected()) {
                        String name = input.getText();
                        if(name.isEmpty()) throw new Exception("Invalid name");
                        Pilot pilot = new Pilot(name);

                        base.addPilot(pilot);
                        JOptionPane.showConfirmDialog(dWindow, "Pilot was successfully added",
                                "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String name = input.getText();
                        Pilot pilot = new Pilot(name);

                        base.deletePilot(pilot);
                        JOptionPane.showConfirmDialog(dWindow, "Pilot was successfully deleted",
                                "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (Exception ex) {
                    JOptionPane.showConfirmDialog(dWindow, ex.getMessage(), "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    dWindow.setVisible(false);
                }
            }
        });
    }

    public void editAircraftsList() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 200);
        dWindow.setTitle("Edit aircrafts list");
        dWindow.setLayout(new GridLayout(4, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JTextField input = new JTextField("Aircraft number", 30);
        dWindow.add(input);

        JRadioButton addButt = new JRadioButton("Add aircraft");
        JRadioButton delButt = new JRadioButton("Delete aircraft");

        ButtonGroup group = new ButtonGroup();
        group.add(addButt);
        group.add(delButt);

        dWindow.add(addButt);
        dWindow.add(delButt);

        JButton butt = new JButton("Edit");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (addButt.isSelected()) {
                        String number = input.getText();
                        if(number.isEmpty()) throw new Exception("Invalid side number");
                        Aircraft aircraft = new Aircraft(number);

                        base.addAircraft(aircraft);
                        JOptionPane.showConfirmDialog(dWindow, "Aircraft was successfully added",
                                "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String number = input.getText();
                        Aircraft aircraft = new Aircraft(number);

                        base.deleteAircraft(aircraft);
                        JOptionPane.showConfirmDialog(dWindow, "Aircraft was successfully deleted",
                                "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (Exception ex) {
                    JOptionPane.showConfirmDialog(dWindow, ex.getMessage(), "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    dWindow.setVisible(false);
                }
            }
        });
    }

    public void printBase() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 100);
        dWindow.setTitle("Show base");
        dWindow.setLayout(new GridLayout(3, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);
    }

}
