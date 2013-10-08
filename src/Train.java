package com.adarwin.coaster;

import java.util.ArrayList;

public class Train {
    private final ArrayList<Car> cars;
    private volatile LinkedPoint<RailSection> currentRailSection;

    public Train(int numberOfCars) {
        int numberOfSeatsPerCar = 4;
        cars = new ArrayList<Car>(numberOfCars);
        for (int i = 0; i < numberOfCars; i++) {
            cars.add(new Car(numberOfSeatsPerCar));
        }
    }

    public void setRailSection(LinkedPoint<RailSection> rs) {
        currentRailSection = rs;
    }

    public Car getCar(int index) { return cars.get(index); }

    public int getNumberOfCars() { return cars.size(); }

    public LinkedPoint<RailSection> getCurrentRailSection() { return currentRailSection; }

    public ArrayList<Car> getCars() { return cars; }

    public synchronized boolean advance() {
        currentRailSection = currentRailSection.getNext();
        return currentRailSection.isStartingPoint();
    }
}
