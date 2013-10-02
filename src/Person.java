package com.adarwin.coaster;

public class Person {
    Seat seat;
    public Person() {}
    public void sit(Seat seat) {
        this.seat = seat;
        seat.seatPerson(this);
    }
    public void stand() {
        if (seat != null) {
            seat.unseatPerson();
            seat = null;
        }
    }
}
