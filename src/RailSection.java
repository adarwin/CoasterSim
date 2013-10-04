package com.adarwin.coaster;

public class RailSection {
    private boolean occupied;
    private int travelTime;

    public RailSection(int travelTime) {
        this.travelTime = travelTime;
        occupied = false;
    }

    public void setOccupied(boolean value) {
        occupied = value;
    }

    public int getTravelTime() { return travelTime; }
}
