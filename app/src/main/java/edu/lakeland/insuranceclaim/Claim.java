package edu.lakeland.insuranceclaim;

import android.graphics.Bitmap;

import java.util.Date;

public class Claim {
    public final String TAG = "Meals";
    private Integer claimID;
    private String date;
    private String driverName;
    private String auto;
    private Double latitude;
    private Double longitude;
    private Bitmap picture;

    public Claim(){
        claimID = -1;
        date = "";
        driverName = "";
        auto = "";
        latitude = 0.0;
        longitude = 0.0;
    }
    public void setClaimID(Integer claimID){this.claimID = claimID;}
    public Integer getClaimID(){return claimID;}
    public void setDate(String date) {
        this.date = date;
    }
    public String getClaimDate() {
        return this.date;
    }
    public void setDriverName(String driverName){
        this.driverName = driverName;
    }
    public String getDriverName() {
        return this.driverName;
    }
    public void setAuto(String auto){
        this.auto = auto;
    }
    public String getAuto() {
        return this.auto;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude){ this.latitude = latitude; }
    public Double getLongitude(){
        return this.longitude;
    }
    public void setLongitude(Double longitude){ this.longitude = longitude; }
    public Bitmap getPicture() { return picture; }
    public void setPicture(Bitmap picture) { this.picture = picture; }

}
