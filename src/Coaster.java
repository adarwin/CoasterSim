package com.adarwin.coaster;

import java.util.ArrayList;

public class Coaster {
    private ArrayList<Train> trains;
    protected Train train1;
    private Track track;
    private LinkedPoint<Boolean> mainLineSpace;

    public Coaster(int numberOfTrains, int numberOfCarsPerTrain) {
        track = new Track();
        train1 = new Train(numberOfCarsPerTrain);
        train1.setRailSection(track.getStartingRailSection());
        trains = new ArrayList<Train>(numberOfTrains);
        for (int i = 0; i < numberOfTrains; i++) {
            trains.add(new Train(numberOfCarsPerTrain));
        }
        int lineHeight = 20;
        int exitWidthOffset = 10;
        int exitHeightOffset = 9;
        mainLineSpace = new LinkedPoint<Boolean>(false, exitWidthOffset,
                                                 exitHeightOffset);
        mainLineSpace.setStartingPoint(true);
        int horizontalCoord = exitWidthOffset-1;
        for (int i = exitHeightOffset; i >= 0; i--) {
            mainLineSpace.add(false, horizontalCoord, i);
        }
        mainLineSpace.add(false, --horizontalCoord, 0);
        //horizontalCoord--;
        while (horizontalCoord > 3) {
            horizontalCoord--;
            for (int i = 0; i < lineHeight; i++) {
                mainLineSpace.add(false, horizontalCoord, i);
                if (horizontalCoord == 4) {
                    System.out.println(i);
                }
            }
            mainLineSpace.add(false, --horizontalCoord, lineHeight-1);
            horizontalCoord--;
            for (int i = lineHeight-1; i >= 0; i--) {
                mainLineSpace.add(false, horizontalCoord, i);
            }
            mainLineSpace.add(false, --horizontalCoord, 0);
            if (horizontalCoord < 0) 
                System.out.println("Oh NO!!!");
        }
    }
    public LinkedPoint<Boolean> getMainLineSpace() { return mainLineSpace; }
    public Track getTrack() { return track; }
    public ArrayList<Train> getTrains() { return trains; }
}
