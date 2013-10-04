package com.adarwin.coaster;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;

public class CoasterDrawingPanel extends JPanel {
    private Coaster coaster;
    private Track track;
    private int trackXCoord, trackYCoord;

    public CoasterDrawingPanel(Coaster coaster) {
        super();
        this.coaster = coaster;
        track = coaster.getTrack();
        setLayout(null);
        trackXCoord = 150;
        trackYCoord = 30;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int multiplier = 50;
        int lineSpaceSize = 10;
        int verticalOffset = 100;
        int horizontalOffset = 0;
        // Draw people in main line
        //synchronized(this) {
            LinkedPoint<Boolean> currentLineSpace = coaster.getMainLineSpace();
            for (Person person : CoasterSimulator.mainLine) {
                int x = currentLineSpace.x*lineSpaceSize+horizontalOffset;
                int y = currentLineSpace.y*lineSpaceSize+verticalOffset;
                //g.drawRect(x, y, lineSpaceSize, lineSpaceSize);
                g.fillOval(x, y, lineSpaceSize, lineSpaceSize);
                currentLineSpace = currentLineSpace.getNext();
                if (currentLineSpace == null) break;
            }
        //}
        // Draw people in sub lines
        // Draw coasters
        // Draw track
        LinkedPoint<RailSection> startingRailSection = track.getStartingRailSection();
        LinkedPoint<RailSection> railSection = startingRailSection;
        g.setColor(Color.blue);
        g.drawRect(trackXCoord+(startingRailSection.x*multiplier),
                   trackYCoord+(startingRailSection.y*multiplier),
                   multiplier, multiplier);
        g.setColor(Color.black);
        while ((railSection = railSection.getNext()) != null)
        {
            g.drawRect(trackXCoord+(railSection.x*multiplier),
                       trackYCoord+(railSection.y*multiplier),
                       multiplier, multiplier);
            if (railSection == startingRailSection) break;
        }

        // Draw trains on track
        Train train = coaster.train1;
        int numberOfCars = train.getNumberOfCars();
        LinkedPoint<RailSection> railSectionAtFrontOfTrain = train.getCurrentRailSection();
        railSection = railSectionAtFrontOfTrain;
        System.out.println("(" + railSectionAtFrontOfTrain.x + ", " + railSectionAtFrontOfTrain.y + ")");
        int x, y;
        g.setColor(Color.red);
        for (int i = 0; i < numberOfCars; i++) {
            x = trackXCoord+railSection.x*multiplier;
            y = trackYCoord+railSection.y*multiplier;
            Car car = train.getCar(i);
            int numberOfSeats = car.getNumberOfSeats();
            int numberOfRows = numberOfSeats/2;
            int seatSize = 15;
            int seatMargin = 7;
            for (int j = 0; j < numberOfRows; j++) {
                g.drawRect(x+seatMargin, y+seatMargin+j*seatSize+seatMargin*j,
                           seatSize, seatSize);
                g.drawRect(x+multiplier-seatMargin-seatSize,
                           y+seatMargin+j*seatSize+seatMargin*j,
                           seatSize, seatSize);
            }
            g.drawRect(x, y, multiplier, multiplier);
            railSection = railSection.getPrevious();
        }
        g.setColor(Color.black);
    }
}
