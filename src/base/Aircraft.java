package base;

public class Aircraft {

    private int sideNumber;
    private String name;

    public Aircraft(int number) {
        this.sideNumber = number;
        this.name = "";
    }

    public Aircraft(int sideNumber, String name) {
        this.sideNumber = sideNumber;
        this.name = name;
    }

    public int getSideNumber() {
        return sideNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSideNumber(int sideNumber) {
        this.sideNumber = sideNumber;
    }

    public String toString() {
        return "base.Aircraft\nSide number: " + sideNumber + "\nName: " + name;
    }

}
