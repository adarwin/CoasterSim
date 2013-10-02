package com.adarwin.coaster;

import java.util.ArrayList;

public class Car {
    ArrayList<Seat> seats;
    public Car(int numberOfSeats) {
        if (numberOfSeats % 2 > 0) {
            numberOfSeats++;
        }
        seats = new ArrayList<Seat>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            seats.add(new Seat());
        }
    }
    public int getNumberOfSeats() { return seats.size(); }
}
