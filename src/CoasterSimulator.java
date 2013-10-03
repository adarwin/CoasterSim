package com.adarwin.coaster;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CoasterSimulator {
    private static Coaster coaster;
    private static JFrame frame;
    private static CoasterDrawingPanel coasterDrawingPanel;
    protected static BlockingQueue<Person> mainLine;
    protected static BlockingQueue<Person> car1Line, car2Line,
                                           car3Line, car4Line;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        initializeVariables();

        Thread personSpawner = new Thread(new Runnable() {
            public void run() {
            }
        });
        Thread lineCoordinator = new Thread(new Runnable() {
            public void run() {
                for (;;) {
                    Person person = mainLine.poll();
                    if (person != null) {
                        // place the person in one of the car lines
                    }
                }
            }
        });
        Thread coasterDriver = new Thread(new Runnable() {
            public void run() {
            }
        });
    }

    private static void createAndShowGUI() {
        buildContainers();
        buildComponents();
        addListeners();
        configureLayouts();
        makeEverythingVisible();
    }

    private static void initializeVariables() {
        coaster = new Coaster(2, 4);
        mainLine = new ArrayBlockingQueue<Person>(100);
        car1Line = new ArrayBlockingQueue<Person>(4);
        car2Line = new ArrayBlockingQueue<Person>(4);
        car3Line = new ArrayBlockingQueue<Person>(4);
        car4Line = new ArrayBlockingQueue<Person>(4);
    }
    private static void buildContainers() {
        frame = new JFrame("Coaster Simulator");
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        coasterDrawingPanel = new CoasterDrawingPanel(coaster);
        frame.setContentPane(coasterDrawingPanel);
    }
    private static void buildComponents() {
    }
    private static void addListeners() {
    }
    private static void configureLayouts() {
    }
    private static void makeEverythingVisible() {
        frame.setVisible(true);
    }
}
