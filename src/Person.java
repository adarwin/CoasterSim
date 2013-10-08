package com.adarwin.coaster;

import java.awt.Color;

public class Person {
    //Seat seat;
    private Color color;
    public Person(Color color) {
        this.color = color;
    }

    public Color getColor() { return color; }

    /*
    public void sit(Seat seat) {
        this.seat = seat;
        seat.seatPerson(this);
    }
    */

    /*
    public void stand() {
        if (seat != null) {
            seat.unseatPerson();
            seat = null;
        }
    }
    */
}
