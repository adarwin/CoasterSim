package com.adarwin.coaster;

import java.util.ArrayList;

public class Train {
    ArrayList<Car> cars;
    RailSection currentRailSection;
    public Train(int numberOfCars) {
        //currentRailSection = railSection;
        cars = new ArrayList<Car>(numberOfCars);
        for (int i = 0; i < numberOfCars; i++) {
            cars.add(new Car(4));
        }
    }
    public void setRailSection(RailSection rs) {
        currentRailSection = rs;
    }
    public Car getCar(int index) { return cars.get(index); }
    public int getNumberOfCars() { return cars.size(); }
    public RailSection getCurrentRailSection() { return currentRailSection; }
    public ArrayList<Car> getCars() { return cars; }
}