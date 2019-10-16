package com.example.gocar.helper;
import com.example.gocar.MainActivity;
public class cars extends MainActivity implements Comparable<cars>{
    private String Model_Name;
    private int ProductionYear;
    private double Latitude;
    private double Longitude;
    private int FuelLevel;
    private String image;
    double dist;

    public cars(String Model_Name, int ProductionYear, double latTo, double lonTO, int FuelLevel, String image,double dist) {
        this.Model_Name = Model_Name;
        this.ProductionYear = ProductionYear;
        this.Latitude = latTo;
        this.Longitude = lonTO;
        this.FuelLevel = FuelLevel;
        this.image = image;
        this.dist = dist;


    }
    public int compareTo(cars cars) {
        return Double.compare(this.dist,cars.dist);

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
