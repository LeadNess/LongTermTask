package base;

public class Aircraft {

    private String sideNumber;

    public Aircraft(String number) {
        this.sideNumber = number;
    }

    public String getSideNumber() {
        return sideNumber;
    }

    public void setSideNumber(String sideNumber) {
        this.sideNumber = sideNumber;
    }

    @Override
    public String toString() {
        return "Aircraft " + sideNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
