package com.adarwin.coaster;

public class Seat {
    Person person;
    public Seat() {
        person = null;
    }
    public void seatPerson(Person person) {
        this.person = person;
    }
    public Person unseatPerson() {
        if (person != null) {
            Person temp = person;
            person = null;
            return temp;
        } else {
            return null;
        }
    }
}
