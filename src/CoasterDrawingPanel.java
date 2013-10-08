package com.adarwin.coaster;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class CoasterDrawingPanel extends JPanel {
    private Coaster coaster;
    private Track track;
    private int trackXCoord, trackYCoord;

    public CoasterDrawingPanel(Coaster coaster) {
        super();
        this.coaster = coaster;
        track = coaster.getTrack();
        setLayout(null);
        trackXCoord = 250;
        trackYCoord = 30;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int multiplier = 50;
        int lineSpaceSize = 10;
        int verticalOffset = 130;
        int horizontalOffset = 50;
        // Draw people in main line
        //synchronized(this) {
            LinkedPoint<Boolean> currentLineSpace = coaster.getMainLineSpace();
            for (Person person : CoasterSimulator.mainLine) {
                g.setColor(person.getColor());
                int x = currentLineSpace.x*lineSpaceSize+horizontalOffset;
                int y = currentLineSpace.y*lineSpaceSize+verticalOffset;
                //g.drawRect(x, y, lineSpaceSize, lineSpaceSize);
                g.fillOval(x, y, lineSpaceSize, lineSpaceSize);
                currentLineSpace = currentLineSpace.getNext();
                if (currentLineSpace == null) break;
            }
            g.setColor(Color.black);
        //}
        verticalOffset = 150;
        horizontalOffset = 200;
        // Draw people in sub lines
        //currentLineSpace = coaster.getCar1LineSpace();
        drawCarLine(g, CoasterSimulator.car1Line, coaster.getCar1LineSpace(),
                    lineSpaceSize, horizontalOffset, verticalOffset, 0, 50);
        drawCarLine(g, CoasterSimulator.car2Line, coaster.getCar2LineSpace(),
                    lineSpaceSize, horizontalOffset, verticalOffset, 0, 50);
        drawCarLine(g, CoasterSimulator.car3Line, coaster.getCar3LineSpace(),
                    lineSpaceSize, horizontalOffset, verticalOffset, 0, 50);
        drawCarLine(g, CoasterSimulator.car4Line, coaster.getCar4LineSpace(),
                    lineSpaceSize, horizontalOffset, verticalOffset, 0, 50);
        // Draw coasters
        // Draw track
        LinkedPoint<RailSection> startingRailSection = track.getStartingRailSection();
        LinkedPoint<RailSection> railSection = startingRailSection;
        //g.setColor(Color.blue);
        g.drawRect(trackXCoord+(startingRailSection.x*multiplier),
                   trackYCoord+(startingRailSection.y*multiplier),
                   multiplier, multiplier);
        //g.setColor(Color.black);
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
        //System.out.println("(" + railSectionAtFrontOfTrain.x + ", " + railSectionAtFrontOfTrain.y + ")");
        int carXCoord, carYCoord;
        g.setColor(Color.red);
        for (int i = 0; i < numberOfCars; i++) {
            carXCoord = trackXCoord+railSection.x*multiplier;
            carYCoord = trackYCoord+railSection.y*multiplier;
            Car car = train.getCar(i);
            int numberOfSeats = car.getNumberOfSeats();
            int numberOfRows = numberOfSeats/2;
            int seatSize = 15;
            int seatMargin = 7;
            int rowNumber = 0;
            int seatNumber = 0;
            g.setColor(Color.black);
            for (Person person : car.getPassengers()) {
                g.setColor(person.getColor());
                g.fillOval(carXCoord+seatMargin + (seatSize-lineSpaceSize)/2 +
                           (seatNumber*seatSize) + (seatMargin*seatNumber),
                           carYCoord+seatMargin + (seatSize-lineSpaceSize)/2 +
                           (rowNumber*seatSize) + (seatMargin*rowNumber),
                           lineSpaceSize, lineSpaceSize);
                seatNumber++;
                if (seatNumber > 1) {
                    seatNumber = 0;
                    rowNumber++;
                }
            }
            g.setColor(Color.red);
            for (int j = 0; j < numberOfRows; j++) {
                g.drawRect(carXCoord+seatMargin, carYCoord+seatMargin+j*seatSize+seatMargin*j,
                           seatSize, seatSize);
                g.drawRect(carXCoord+multiplier-seatMargin-seatSize,
                           carYCoord+seatMargin+j*seatSize+seatMargin*j,
                           seatSize, seatSize);
            }
            g.drawRect(carXCoord, carYCoord, multiplier, multiplier);
            railSection = railSection.getPrevious();
        }
        g.setColor(Color.black);
    }
    private void drawCarLine(Graphics g, BlockingQueue<Person> carLine,
                             LinkedPoint<Boolean> currentLineSpace,
                             int lineSpaceSize, int xCoord, int yCoord,
                             int xScalar, int yScalar) {
        if (xScalar == 0) xScalar = lineSpaceSize;
        if (yScalar == 0) yScalar = lineSpaceSize;
        for (Person person : carLine) {
            g.setColor(person.getColor());
            int x = currentLineSpace.x * xScalar + xCoord;
            int y = currentLineSpace.y * yScalar + yCoord;
            g.fillOval(x, y, lineSpaceSize, lineSpaceSize);
            currentLineSpace = currentLineSpace.getNext();
            if (currentLineSpace == null) break;
        }
        g.setColor(Color.black);
    }
}
