package com.adarwin.coaster;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;

public class CoasterDrawingPanel extends JPanel {
    private Coaster coaster;
    private Track track;
    public CoasterDrawingPanel(Coaster coaster) {
        super();
        this.coaster = coaster;
        track = coaster.getTrack();
        setLayout(null);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int multiplier = 50;
        RailSection startingRailSection = track.getStartingRailSection();
        RailSection railSection = startingRailSection;
        g.setColor(Color.blue);
        while ((railSection = railSection.getNext()) != null)
        {
            g.drawRect(railSection.x*multiplier, railSection.y*multiplier,
                       multiplier, multiplier);
            g.setColor(Color.black);
            if (railSection == startingRailSection) break;
        }
        //RailSection[][] railSectionPlane = track.getRailSectionPlane();
        /*
        for (int i = 0; i < 4; i++) {
            g.drawRect(i*multiplier, 0, multiplier, multiplier);
            g.drawRect(i*multiplier, 3*multiplier, multiplier, multiplier);
            g.drawRect(0, i*multiplier, multiplier, multiplier);
            g.drawRect(3*multiplier, i*multiplier, multiplier, multiplier);
        }
        */
        //ArrayList<Train> trains = coaster.getTrains();
        //Train train = trains.get(0);
        Train train = coaster.train1;
        int numberOfCars = train.getNumberOfCars();
        railSection = train.getCurrentRailSection();
        int x;
        int y;
        g.setColor(Color.red);
        for (int i = 0; i < numberOfCars; i++) {
            Car car = train.getCar(i);
            int numberOfSeats = car.getNumberOfSeats();
            x = railSection.x;
            y = railSection.y;
            g.drawRect(x*multiplier, y*multiplier, multiplier, multiplier);
            railSection = railSection.getPrevious();
        }
        g.setColor(Color.black);

    }
}
