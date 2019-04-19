package db;

import java.sql.*;
import java.util.*;
import base.*;

public class DB {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:db//db.s3db";

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса db
    private static DB instance = null;

    public static synchronized DB getInstance()
            throws SQLException, java.lang.ClassNotFoundException {
        if (instance == null)
            instance = new DB();
        return instance;
    }

    private Connection connection;

    public DB() throws SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<Pilot> getPilotsList() {

        try (Statement statement = this.connection.createStatement()) {
            List<Pilot> pilots = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT NAME, ACCESS FROM PILOTS");
            while (resultSet.next()) {
                pilots.add(new Pilot(resultSet.getString("NAME"),
                        resultSet.getBoolean("ACCESS")));
            }
            return pilots;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Aircraft> getAircraftsList() {

        try (Statement statement = this.connection.createStatement()) {
            List<Aircraft> aircrafts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT AIRCRAFT FROM BASE");
            while (resultSet.next()) {
                aircrafts.add(new Aircraft(resultSet.getString("AIRCRAFT")));
            }
            return aircrafts;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Map<Aircraft, Pilot> getAircraftsMap() {

        try (Statement statement = this.connection.createStatement()) {
            Map<Aircraft, Pilot> aircrafts = new HashMap<>();
            ResultSet resultSet = statement.executeQuery("SELECT AIRCRAFT, PILOT FROM BASE");
            while (resultSet.next()) {
                aircrafts.put(new Aircraft(resultSet.getString("AIRCRAFT")),
                        new Pilot(resultSet.getString("PILOT"), true));
            }
            return aircrafts;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public void addPilot(Pilot pilot) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO PILOTS(NAME, ACCESS) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, pilot.getName());
            statement.setObject(2, pilot.getAccess());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePilot(Pilot pilot) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM PILOTS WHERE NAME = ?")) {
            statement.setObject(1, pilot.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE BASE SET PILOT = ? WHERE PILOT = ?")) {
            statement.setObject(1, null);
            statement.setObject(2, pilot.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePilotAccess(Pilot pilot, Boolean access) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE PILOTS SET ACCESS = ? WHERE NAME = ?")) {
            statement.setObject(1, access);
            statement.setObject(2, pilot.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAircraft(Aircraft aircraft) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO BASE(AIRCRAFT) " +
                        "VALUES(?)")) {
            statement.setObject(1, aircraft.getSideNumber());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAircraft(Aircraft aircraft) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM BASE WHERE AIRCRAFT = ?")) {
            statement.setObject(1, aircraft.getSideNumber());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPilotToAircraft(Aircraft aircraft, Pilot pilot) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE BASE SET PILOT = ? WHERE AIRCRAFT = ?")) {
            statement.setObject(1, pilot.getName());
            statement.setObject(1, aircraft.getSideNumber());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePilotFromAircraft(Aircraft aircraft) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE BASE SET PILOT = ? WHERE AIRCRAFT = ?")) {
            statement.setObject(1, null);
            statement.setObject(1, aircraft.getSideNumber());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePilotFromAircraft(Pilot pilot) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE BASE SET PILOT = ? WHERE PILOT = ?")) {
            statement.setObject(1, null);
            statement.setObject(1, pilot.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
