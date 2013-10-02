package com.adarwin.coaster;

import java.util.ArrayList;

public class Coaster {
    private ArrayList<Train> trains;
    protected Train train1;
    private Track track;
    public Coaster(int numberOfTrains, int numberOfCarsPerTrain) {
        track = new Track();
        train1 = new Train(numberOfCarsPerTrain);
        train1.setRailSection(track.getStartingRailSection());
        trains = new ArrayList<Train>(numberOfTrains);
        for (int i = 0; i < numberOfTrains; i++) {
            trains.add(new Train(numberOfCarsPerTrain));
        }
    }
    public Track getTrack() { return track; }
    public ArrayList<Train> getTrains() { return trains; }
}
