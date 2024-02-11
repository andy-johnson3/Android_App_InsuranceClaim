package edu.lakeland.insuranceclaim;

public class Driver {
    private String fName;
    private String lName;
    private String bDay;

    public Driver(){
        fName = "";
        lName = "";
        bDay = "";
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setbDay(String bDay) {
        this.bDay = bDay;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getbDay() {
        return bDay;
    }

    public String getFullName() { return fName + " " + lName; }
}
