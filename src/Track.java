package com.adarwin.coaster;

import java.util.Iterator;

public class Track implements Iterable {
    private final LinkedPoint<RailSection> startingRailSection;
    private final int gridSize = 10;
    public Track() {
        int startingIndex = 2;//gridSize/4;
        int time = 1000;
        RailSection current, previous, next;
        current = new RailSection(time);
        previous = null;
        next = null;
        startingRailSection = new LinkedPoint<RailSection>(current,
                                                           0, startingIndex,
                                                           true);
        //startingRailSection.setStartingPoint(true);
        //startingRailSection = new RailSection(time, 0 ,startingIndex, null);
        for (int i = startingIndex-1; i > 0; i--) {
            startingRailSection.add(new RailSection(time), 0, i);
            //startingRailSection.add(new RailSection(time, 0, i, null));
        }
        for (int i = 0; i < gridSize-1; i++) {
            startingRailSection.add(new RailSection(time), i, 0);
            //startingRailSection.add(new RailSection(time, i, 0, null));
        }
        for (int i = 0; i < gridSize-1; i++) {
            startingRailSection.add(new RailSection(time), gridSize-1, i);
            //startingRailSection.add(new RailSection(time, gridSize-1, i, null));
        }
        for (int i = gridSize-1; i > 0; i--) {
            startingRailSection.add(new RailSection(time), i, gridSize-1);
            //startingRailSection.add(new RailSection(time, i, gridSize-1, null));
        }
        for (int i = gridSize-1; i > startingIndex+1; i--) {
            startingRailSection.add(new RailSection(time), 0, i);
            //startingRailSection.add(new RailSection(time, 0, i, null));
        }
        /*
        RailSection lastRailSection = new RailSection(time, 0, startingIndex+1,
                                                      startingRailSection);
        */
        startingRailSection.addFinalLink(new RailSection(time), 0, startingIndex+1);
        //startingRailSection.add(lastRailSection);
        //startingRailSection.setPrevious(lastRailSection);
        //}
    }
    public Iterator<RailSection> iterator() {
        return new TrackIterator(this);
    }
    public LinkedPoint<RailSection> getStartingRailSection() { return startingRailSection; }
    //public RailSection getStartingRailSection() { return startingRailSection; }
}
