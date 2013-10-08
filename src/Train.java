package com.adarwin.coaster;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Train {
    private final ArrayList<Car> cars;
    private volatile LinkedPoint<RailSection> currentRailSection;
    private AtomicBoolean shouldDepart;

    public Train(int numberOfCars) {
        shouldDepart = new AtomicBoolean(false);
        int numberOfSeatsPerCar = 4;
        cars = new ArrayList<Car>(numberOfCars);
        for (int i = 0; i < numberOfCars; i++) {
            cars.add(new Car(numberOfSeatsPerCar));
        }
    }

    public boolean shouldDepart() {
        return shouldDepart.get();
    }

    public void setShouldDepart(boolean value) {
        shouldDepart.set(value);
    }

    public boolean getAndSetShouldDepart(boolean value) {
        return shouldDepart.getAndSet(value);
    }

    /*
    public void seatPassengers() {
        for (Car car : cars) {
            car.getPassengers().clear();
        }
        int numberOfCars = getNumberOfCars();
        for (int i = 0; i < numberOfCars; i++) {
            Car car = getCar(i);
            while (car.getPassengers().remainingCapacity() > 0) {
                Thread.sleep(200);
                Person person;
                switch (j) {
                case 0:
                    person = car1Line.take();
                    car.getPassengers().offer(person);
                    break;
                case 1:
                    person = car2Line.take();
                    car.getPassengers().offer(person);
                    break;
                case 2:
                    person = car3Line.take();
                    car.getPassengers().offer(person);
                    break;
                case 3:
                    person = car4Line.take();
                    car.getPassengers().offer(person);
                    break;
                }
            }
        }
    }
    */

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
