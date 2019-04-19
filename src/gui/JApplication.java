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

    private JButton grandAccessButton             = new JButton("Grand pilot admission"     );
    private JButton revokeAccessButton            = new JButton("Revoke pilot admission"    );
    private JButton editPilotsListButton          = new JButton("Edit pilots list"          );
    private JButton editAircraftsListButton       = new JButton("Edit aircrafts list"       );
    private JButton pickUpPilotToAircraftButton   = new JButton("Pick up pilot to aircraft" );
    private JButton removePilotFromAircraftButton = new JButton("Remove pilot from aircraft");
    private JButton printPilotsButton             = new JButton("Show pilots"               );
    private JButton printBaseButton               = new JButton("Show base"                 );
    private JButton exitButton                    = new JButton("Exit programm"             );


    public JApplication()
            throws java.sql.SQLException, java.lang.ClassNotFoundException {

        base = new MilitaryBase();

        window.setSize(600, 400);
        window.setTitle("MilitaryBase");
        window.setLayout(new GridLayout(9, 1));

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

        window.add(pickUpPilotToAircraftButton);
        pickUpPilotToAircraftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pickUpPilotToAircraft();
            }
        });

        window.add(removePilotFromAircraftButton);
        removePilotFromAircraftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removePilotFromAircraft();
            }
        });

        window.add(printPilotsButton);
        printPilotsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printPilots();
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

    private void grandAccess() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 150);
        dWindow.setTitle("Grand access");
        dWindow.setLayout(new GridLayout(3, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JLabel label = new JLabel("Choose pilot:");
        dWindow.add(label);

        List<Pilot> pilotList = new ArrayList<>(base.getPilotsList());
        pilotList.removeIf((Pilot pilot)->(pilot.getAccess()));

        String[] freePilots = new String[pilotList.size()];
        for(int i = 0; i < freePilots.length; i++)
            freePilots[i] = pilotList.get(i).getName();

        JComboBox editComboBox = new JComboBox(freePilots);
        editComboBox.setEditable(true);
        dWindow.add(editComboBox);

        JButton butt = new JButton("Grand access to pilot");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = (String)editComboBox.getSelectedItem();
                    Pilot pilot = null;
                    if(pilotList.size() == 0) throw new Exception("No pilots without access in base.");
                    for(Pilot p : pilotList)
                        if (p.getName().equals(name))
                            pilot = p;

                    base.grandAdmission(pilot);
                    JOptionPane.showConfirmDialog(dWindow, "Admission was successfully issued",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
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

    private void revokeAccess() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(200, 150);
        dWindow.setTitle("Revoke access");
        dWindow.setLayout(new GridLayout(3, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JLabel label = new JLabel("Choose pilot:");
        dWindow.add(label);

        List<Pilot> pilotList = new ArrayList<>(base.getPilotsList());
        pilotList.removeIf((Pilot pilot)->(!pilot.getAccess()));

        String[] freePilots = new String[pilotList.size()];
        for(int i = 0; i < freePilots.length; i++)
            freePilots[i] = pilotList.get(i).getName();

        JComboBox editComboBox = new JComboBox(freePilots);
        editComboBox.setEditable(true);
        dWindow.add(editComboBox);

        JButton butt = new JButton("Revoke access to pilot");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = (String)editComboBox.getSelectedItem();
                    Pilot pilot = null;
                    if(pilotList.size() == 0) throw new Exception("No pilots with access in base.");
                    for(Pilot p : pilotList)
                        if (p.getName().equals(name))
                            pilot = p;

                    base.revokeAdmission(pilot);
                    JOptionPane.showConfirmDialog(dWindow, "Admission was successfully issued",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
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

    private void editPilotsList() {
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
                        Pilot pilot = null;
                        List<Pilot> pilotList = base.getPilotsList();

                        for(Pilot p : pilotList)
                            if (p.getName().equals(name))
                                pilot = p;

                        if(pilotList.contains(pilot)) {
                            base.deletePilot(pilot);
                            JOptionPane.showConfirmDialog(dWindow, "Pilot was successfully deleted",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        }
                        else throw new Exception("No such pilot in base.");
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

    private void editAircraftsList() {
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
                        Aircraft aircraft = null;

                        List<Aircraft> aircraftList = base.getAircraftsList();

                        for(Aircraft a : aircraftList)
                            if (a.getSideNumber().equals(number))
                                aircraft = a;

                        if(aircraftList.contains(aircraft)) {
                            base.deleteAircraft(aircraft);
                            JOptionPane.showConfirmDialog(dWindow, "Aircraft was successfully deleted",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        }
                        else throw new Exception("No such aircraft in base.");
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

    private void pickUpPilotToAircraft() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(250, 200);
        dWindow.setTitle("Pick up pilot to aircraft");
        dWindow.setLayout(new GridLayout(5, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JLabel label1 = new JLabel("Choose free aircraft");
        dWindow.add(label1);

        Map<Aircraft, Pilot> baseMap = base.getBaseMap();

        List<Aircraft> aircraftList = new ArrayList<>(base.getAircraftsList());
        aircraftList.removeIf((Aircraft aircraft)->(baseMap.get(aircraft)) != null);
        String[] aircrafts = new String[aircraftList.size()];
        for(int i = 0; i < aircrafts.length; i++)
            aircrafts[i] = aircraftList.get(i).getSideNumber();

        JComboBox editComboBox1 = new JComboBox(aircrafts);
        editComboBox1.setEditable(true);
        dWindow.add(editComboBox1);

        JLabel label2 = new JLabel("Choose free pilot");
        dWindow.add(label2);

        List<Pilot> pilotList = new ArrayList<>(base.getPilotsList());
        pilotList.removeIf((Pilot pilot)->(!pilot.getAccess() || baseMap.containsValue(pilot)));

        String[] freePilots = new String[pilotList.size()];
        for(int i = 0; i < freePilots.length; i++)
            freePilots[i] = pilotList.get(i).getName();

        JComboBox editComboBox2 = new JComboBox(freePilots);
        editComboBox2.setEditable(true);
        dWindow.add(editComboBox2);


        JButton butt = new JButton("Edit");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name   = (String)editComboBox1.getSelectedItem();
                    String number = (String)editComboBox2.getSelectedItem();
                    if(aircraftList.size() == 0 || pilotList.size() == 0) throw new Exception("Invalid input");
                    Pilot pilot = null;
                    Aircraft aircraft = null;

                    for(Pilot p : pilotList)
                        if(p.getName().equals(name))
                            pilot = p;

                    for(Aircraft a : aircraftList)
                        if(a.getSideNumber().equals(number))
                            aircraft = a;

                    base.addPilotToAircraft(aircraft, pilot);
                    JOptionPane.showConfirmDialog(dWindow, "Pilot was successfully added to aircraft",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(dWindow, ex.getMessage(), "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    dWindow.setVisible(false);
                }
            }
        });
    }

    private void removePilotFromAircraft() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(250, 150);
        dWindow.setTitle("Remove pilot from aircraft");
        dWindow.setLayout(new GridLayout(3, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        JLabel label1 = new JLabel("Choose aircraft to remove pilot");
        dWindow.add(label1);

        Map<Aircraft, Pilot> baseMap = base.getBaseMap();

        List<Aircraft> aircraftList = new ArrayList<>(base.getAircraftsList());
        aircraftList.removeIf((Aircraft aircraft)->(baseMap.get(aircraft)) == null);
        String[] aircrafts = new String[aircraftList.size()];
        for(int i = 0; i < aircrafts.length; i++)
            aircrafts[i] = aircraftList.get(i).getSideNumber();

        JComboBox editComboBox = new JComboBox(aircrafts);
        editComboBox.setEditable(true);
        dWindow.add(editComboBox);

        JButton butt = new JButton("Edit");
        dWindow.add(butt);
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String number   = (String)editComboBox.getSelectedItem();
                    if(aircraftList.size() == 0) throw new Exception("Invalid input");
                    Pilot pilot = null;
                    Aircraft aircraft = null;

                    for(Map.Entry<Aircraft, Pilot> entry : baseMap.entrySet()) {
                        if(entry.getKey().getSideNumber().equals(number)) {
                            pilot = entry.getValue();
                            aircraft = entry.getKey();
                        }
                    }

                    base.removePilotFromAircraft(aircraft, pilot);
                    JOptionPane.showConfirmDialog(dWindow, pilot + " was successfully removed from aircraft " + number,
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(dWindow, ex.getMessage(), "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    dWindow.setVisible(false);
                }
            }
        });
    }

    private void printPilots() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(300, 200);
        dWindow.setTitle("Show pilots");
        dWindow.setLayout(new GridLayout(1, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        List<Pilot> pilotsList = new ArrayList<>(base.getPilotsList());
        String[] columnNames = {"Pilot", "Access"};
        String[][] data = new String[pilotsList.size()][2];
        for(int i = 0; i < data.length; i++) {
            data[i][0] = pilotsList.get(i).getName();
            data[i][1] = pilotsList.get(i).getAccess() ? "+" : "-";
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dWindow.add(scrollPane);
    }

    private void printBase() {
        JDialog dWindow = new JDialog(window);

        dWindow.setSize(300, 200);
        dWindow.setTitle("Show base");
        dWindow.setLayout(new GridLayout(1, 1));
        dWindow.setVisible(true);

        dWindow.setLocationRelativeTo(null);

        Map<Aircraft, Pilot> baseMap = base.getBaseMap();
        List<Aircraft> aircraftList = new ArrayList<>(base.getAircraftsList());
        String[] columnNames = {"Aircraft", "Pilot"};
        String[][] data = new String[baseMap.size()][2];
        for(int i = 0; i < data.length; i++) {
            data[i][0] = aircraftList.get(i).getSideNumber();

            Pilot pilot = baseMap.get(aircraftList.get(i));
            data[i][1] = (pilot != null) ? baseMap.get(aircraftList.get(i)).getName() : "";
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dWindow.add(scrollPane);
    }

}
