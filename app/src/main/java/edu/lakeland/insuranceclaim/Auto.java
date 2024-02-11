package edu.lakeland.insuranceclaim;

import androidx.annotation.NonNull;

public class Auto {
    private String make;
    private String model;
    private String licensePlate;

    public Auto(){
        make = "";
        model = "";
        licensePlate = "";
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    @NonNull
    @Override
    public String toString() {
        return make + " " + model + ", " + licensePlate;
    }
}
