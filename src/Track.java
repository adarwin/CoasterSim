package com.adarwin.coaster;

import java.util.Iterator;

public class Track implements Iterable {
    private RailSection[][] railSectionPlane;
    private RailSection startingRailSection;
    private final int gridSize = 10;
    public Track() {
        railSectionPlane = new RailSection[gridSize][gridSize];
        int startingIndex = gridSize/3;
        int time = 1000;
        startingRailSection = new RailSection(time, 0, startingIndex, null);
        for (int i = startingIndex-1; i > 0; i--) {
            startingRailSection.add(new RailSection(time, 0, i, null));
        }
        for (int i = 0; i < gridSize-1; i++) {
            startingRailSection.add(new RailSection(time, i, 0, null));
        }
        for (int i = 0; i < gridSize-1; i++) {
            startingRailSection.add(new RailSection(time, gridSize-1, i, null));
        }
        for (int i = gridSize-1; i > 0; i--) {
            startingRailSection.add(new RailSection(time, i, gridSize-1, null));
        }
        for (int i = gridSize-1; i > startingIndex+1; i--) {
            startingRailSection.add(new RailSection(time, 0, i, null));
        }
        RailSection lastRailSection = new RailSection(time, 0, startingIndex+1,
                                                      startingRailSection);
        startingRailSection.add(lastRailSection);
        startingRailSection.setPrevious(lastRailSection);
            /*
            railSectionPlane[i][0] = new RailSection(1000);
            railSectionPlane[i][3] = new RailSection(1000);
            railSectionPlane[0][i] = new RailSection(1000);
            railSectionPlane[3][i] = new RailSection(1000);
            */
        //}
    }
    public Iterator<RailSection> iterator() {
        return new TrackIterator(this);
    }
    public RailSection[][] getRailSectionPlane() { return railSectionPlane; }
    public RailSection getStartingRailSection() { return startingRailSection; }
}
