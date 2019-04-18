package base;

public class Pilot {

    private String name;
    private Boolean access;

    public Pilot(String name) {
        this.name = name;
        this.access = false;
    }

    public Pilot(String name, Boolean access) {
        this.name = name;
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "Pilot " + name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
