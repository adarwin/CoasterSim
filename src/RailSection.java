package com.adarwin.coaster;

public class RailSection {
    private boolean occupied;
    private int travelTime;
    protected int x, y;
    private RailSection previous, next;

    public RailSection(int travelTime, int x, int y, RailSection next) {
        this.travelTime = travelTime;
        this.x = x;
        this.y = y;
        this.next = next;
        occupied = false;
    }
    public void setOccupied(boolean value) {
        occupied = value;
    }
    public int getTravelTime() { return travelTime; }
    public RailSection getNext() { return next; }
    public void setNext(RailSection next) { this.next = next; }
    public RailSection getPrevious() { return previous; }
    public void setPrevious(RailSection previous) { this.previous = previous; }
    public void add(RailSection newSection) {
        if (next == null) {
            setNext(newSection);
            newSection.setPrevious(this);
        } else {
            next.add(newSection);
        }
    }
}
