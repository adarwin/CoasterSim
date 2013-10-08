package com.adarwin.coaster;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Car {
    private final int numberOfSeats;
    private final BlockingQueue<Person> passengers;

    public Car(int numberOfSeats) {
        if (numberOfSeats % 2 > 0) {
            numberOfSeats++;
        }
        this.numberOfSeats = numberOfSeats;
        passengers = new ArrayBlockingQueue<Person>(numberOfSeats);
    }

    public BlockingQueue<Person> getPassengers() { return passengers; }
    public int getNumberOfSeats() { return numberOfSeats; }
}
