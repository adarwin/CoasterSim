package com.adarwin.coaster;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Car {
    //BlockingQueue<Seat> seats;
    int numberOfSeats;
    BlockingQueue<Person> passengers;
    public Car(int numberOfSeats) {
        if (numberOfSeats % 2 > 0) {
            numberOfSeats++;
        }
        this.numberOfSeats = numberOfSeats;
        //seats = new ArrayBlockingQueue<Seat>(numberOfSeats);
        passengers = new ArrayBlockingQueue<Person>(numberOfSeats);
    }
    public int getNumberOfSeats() { return numberOfSeats; }
}
