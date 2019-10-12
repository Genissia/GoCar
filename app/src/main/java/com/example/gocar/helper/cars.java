package com.example.gocar.helper;

public class cars {
    private String Model_Name;
    private int ProductionYear;
    private double Latitude;
    private double Longitude;
    private int FuelLevel;
    private String image;

    public cars(String Model_Name, int ProductionYear, double Latitude, double Longitude, int FuelLevel, String image) {
        this.Model_Name = Model_Name;
        this.ProductionYear = ProductionYear;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.FuelLevel = FuelLevel;
        this.image = image;
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
}
