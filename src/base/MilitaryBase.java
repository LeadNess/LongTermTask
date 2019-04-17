package base;

import java.util.*;
import db.DB;

public class MilitaryBase {

    private DB db;
    private List<Pilot> pilotsList;
    private List<Aircraft> aircraftsList;
    private Map<Aircraft, Pilot> baseMap;

    public MilitaryBase() throws java.sql.SQLException, java.lang.ClassNotFoundException {
        db = new DB();

        pilotsList    = new ArrayList<>(db.getPilotsList());
        aircraftsList = new ArrayList<>(db.getAircraftsList());
        baseMap       = new HashMap<>(db.getAircraftsMap());
    }

    public static void main(String[] args)
            throws java.sql.SQLException, java.lang.ClassNotFoundException {
        MilitaryBase base = new MilitaryBase();
    }

    public void grandAdmission(Pilot pilot) {
        /*
         * TODO: Get pilot's info
         */

        db.changePilotAccess(pilot, true);
    }

    private void revokeAdmission(Pilot pilot) {
        /*
         * TODO: Get pilot's info
         */

        db.changePilotAccess(pilot, false);
    }

    private void editPilotsList() {
        /*
         * TODO: Get pilot's info
         */
        String name = "";
        Pilot pilot = new Pilot(name);

        pilotsList.add(pilot);
        db.addPilot(pilot);
    }

    private void editAircraftsList() {
        /*
         * TODO: Get aircraft's info
         */
        String sideNumber = "";
        Aircraft aircraft = new Aircraft(sideNumber);

        aircraftsList.add(aircraft);
        db.addAircraft(aircraft);
    }
}
