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

    public List<Pilot> getPilotsList() {
        return pilotsList;
    }

    public List<Aircraft> getAircraftsList() {
        return aircraftsList;
    }

    public Map<Aircraft, Pilot> getBaseMap() {
        return baseMap;
    }

    public void grandAdmission(Pilot pilot) {
        for(int i = 0; i < pilotsList.size(); i++)
            if (pilotsList.get(i) == pilot)
                pilotsList.get(i).setAccess(true);

        db.changePilotAccess(pilot, true);
    }

    public void revokeAdmission(Pilot pilot) {
        for(int i = 0; i < pilotsList.size(); i++)
            if (pilotsList.get(i) == pilot)
                pilotsList.get(i).setAccess(false);

        db.changePilotAccess(pilot, false);
    }

    public void deletePilot(Pilot pilot) {

        Aircraft aircraft = null;
        for (Aircraft air: baseMap.keySet())
            if(baseMap.get(air).equals(pilot))
                aircraft = air;

        if(!aircraft.equals(null))
            baseMap.replace(aircraft, pilot, null);

        pilotsList.remove(pilot);
        db.deletePilot(pilot);
    }

    public void addPilotToAircraft(Aircraft aircraft, Pilot pilot) {
        /*
         * TODO: Get pilot's info
         */

        baseMap.put(aircraft, pilot);
        db.addPilotToAircraft(aircraft, pilot);
    }

    public void addPilot(Pilot pilot) {
        /*
         * TODO: Get pilot's info
         */

        pilotsList.add(pilot);
        db.addPilot(pilot);
    }

    public void addAircraft(Aircraft aircraft) {
        aircraftsList.add(aircraft);
        db.addAircraft(aircraft);
    }

    public void deleteAircraft(Aircraft aircraft) {
        aircraftsList.remove(aircraft);
        db.deleteAircraft(aircraft);
    }
}
