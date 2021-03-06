package com.adarwin.coaster;

import java.util.ArrayList;

public class Coaster {
    final private ArrayList<Train> trains;
    final protected Train train1;
    final private Track track;
    final private LinkedPoint<Boolean> mainLineSpace;
    final private LinkedPoint<Boolean> car1LineSpace, car2LineSpace,
                                       car3LineSpace, car4LineSpace;

    public Coaster(int numberOfTrains, int numberOfCarsPerTrain) {
        track = new Track();
        train1 = new Train(numberOfCarsPerTrain);
        train1.setRailSection(track.getStartingRailSection());
        trains = new ArrayList<Train>(numberOfTrains);
        /*
        for (int i = 0; i < numberOfTrains; i++) {
            trains.add(new Train(numberOfCarsPerTrain));
        }
        */
        int lineHeight = 20;
        int exitWidthOffset = 10;
        int exitHeightOffset = 9;
        mainLineSpace = new LinkedPoint<Boolean>(false, exitWidthOffset,
                                                 exitHeightOffset, true);
        buildMainLineSpace(lineHeight, exitWidthOffset, exitHeightOffset);

        int carLineLength = 4;
        car1LineSpace = new LinkedPoint<Boolean>(false, carLineLength-1, 0, true);
        car2LineSpace = new LinkedPoint<Boolean>(false, carLineLength-1, 1, true);
        car3LineSpace = new LinkedPoint<Boolean>(false, carLineLength-1, 2, true);
        car4LineSpace = new LinkedPoint<Boolean>(false, carLineLength-1, 3, true);
        buildCarLineSpaces(carLineLength);
    }
    private void buildCarLineSpaces(int carLineLength) {
        for (int i = carLineLength-2; i >= 0; i--) {
            car1LineSpace.add(false, i, 0);
            car2LineSpace.add(false, i, 1);
            car3LineSpace.add(false, i, 2);
            car4LineSpace.add(false, i, 3);
        }
    }
    private void buildMainLineSpace(int lineHeight, int exitWidthOffset,
                                    int exitHeightOffset) {
        /*
        mainLineSpace = new LinkedPoint<Boolean>(false, exitWidthOffset,
                                                 exitHeightOffset, true);
        */
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
    public LinkedPoint<Boolean> getCar1LineSpace() { return car1LineSpace; }
    public LinkedPoint<Boolean> getCar2LineSpace() { return car2LineSpace; }
    public LinkedPoint<Boolean> getCar3LineSpace() { return car3LineSpace; }
    public LinkedPoint<Boolean> getCar4LineSpace() { return car4LineSpace; }
    public Track getTrack() { return track; }
    public ArrayList<Train> getTrains() { return trains; }
}
