package com.example.gocar.helper;
import com.example.gocar.MainActivity;
public class cars extends MainActivity implements Comparable<cars>{
    private int uniqueid;
    private String Model_Name;
    private int ProductionYear;
    private double Latitude;
    private double Longitude;
    public static Double latidub;
    public static Double longdub;

    private int FuelLevel;
    private String image;
    double dist;

    public cars(int uniqueid, String Model_Name, int ProductionYear, double latTo, double lonTO, int FuelLevel, String image,double dist) {
        this.uniqueid = uniqueid;
        this.Model_Name = Model_Name;
        this.ProductionYear = ProductionYear;
        this.Latitude = latTo;
        this.latidub = latTo;
        this.Longitude = lonTO;
        this.longdub = lonTO;
        this.FuelLevel = FuelLevel;
        this.image = image;
        this.dist = dist;


    }
    public int compareTo(cars cars) {
        return Double.compare(this.dist,cars.dist);
    }


    public int getuniqueid() {
        return uniqueid;
    }
    public String getModelName() {
        return Model_Name;
    }

    public int getProductionYear() {
        return ProductionYear;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public int getFuelLevel() {
        return FuelLevel;
    }

    public String getImage() {
        return image;
    }

    public double getdist()
    {
        return dist;
    }

}
