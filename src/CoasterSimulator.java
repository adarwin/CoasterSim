package com.adarwin.coaster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CoasterSimulator {
    protected final static Coaster coaster = new Coaster(2, 4);
    private static JFrame frame;
    private static JButton stopSimulationButton, sendCoasterButton;
    private static JPanel mainPanel, controlPanel;
    private static CoasterDrawingPanel coasterDrawingPanel;
    private static Timer repaintTimer;
    protected static BlockingQueue<Person> mainLine;
    protected static BlockingQueue<Person> car1Line, car2Line,
                                           car3Line, car4Line;
    private static Thread personSpawner, lineCoordinator, coasterDriver;

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                repaintTimer = new Timer(33, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        coasterDrawingPanel.repaint();
                    }
                });
                repaintTimer.start();
            }
        });

        initializeVariables();

        personSpawner = new Thread(() -> {
            try {
                Thread.sleep(3000);
                for (;;) {//int i = 0; i < 50; i++) {
                    int rgbValue = (int)(Math.random()*0xFFFFFF);
                    int sleepTime = 50+(int)(Math.random()*500);
                    mainLine.put(new Person(new Color(rgbValue)));
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                //throw e;
                //break;
            }
        });


        lineCoordinator = new Thread(() -> {
            Person nextPerson;
            for (;;) {
                try {
                    //if (!coaster.train1.getCurrentRailSection().isStartingPoint()) {
                    // Take a person from the main line
                    nextPerson = mainLine.take();
                    //Thread.sleep(200);
                    // Find an empty space in one of the 4 car lines
                    for(;;) {
                        if (car1Line.offer(nextPerson)) {
                            break;
                        } else if (car2Line.offer(nextPerson)) {
                            break;
                        } else if (car3Line.offer(nextPerson)) {
                            break;
                        } else if (car4Line.offer(nextPerson)) {
                            break;
                        }
                    }
                    //}
                } catch (InterruptedException ex) {
                    break;
                }
            }
        });


        coasterDriver = new Thread(() -> {
            try {
                boolean atStart = true;
                for (;;) {
                    if (atStart) {
                        int numberOfCars = coaster.train1.getNumberOfCars();
                        // Remove any existing passengers
                        for (Car car : coaster.train1.getCars()) {
                            car.getPassengers().clear();
                        }
                        Thread.sleep(1000);
                        for (int j = 0; j < numberOfCars; j++) {
                            if (coaster.train1.shouldDepart()) break;
                            Car car = coaster.train1.getCar(j);
                            while (car.getPassengers().remainingCapacity() > 0) {
                                if (coaster.train1.shouldDepart()) break;
                                Thread.sleep(200);
                                Person person;
                                switch (j) {
                                case 0:
                                    person = car1Line.take();
                                    car.getPassengers().offer(person);
                                    break;
                                case 1:
                                    person = car2Line.take();
                                    car.getPassengers().offer(person);
                                    break;
                                case 2:
                                    person = car3Line.take();
                                    car.getPassengers().offer(person);
                                    break;
                                case 3:
                                    person = car4Line.take();
                                    car.getPassengers().offer(person);
                                    break;
                                }
                            }
                        }
                        coaster.train1.setShouldDepart(false);
                        if (coaster.train1.getAndSetShouldDepart(false)) {
                            Thread.sleep(1000);
                        }
                        // Try to pick up people
                        // for each car, pick up the people in the associated line
                    }
                    atStart = coaster.train1.advance();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                //break;
            }
        });



        personSpawner.start();
        coasterDriver.start();
        lineCoordinator.start();
    }

    private static void createAndShowGUI() {
        buildContainers();
        buildComponents();
        configureLayouts();
        nestContainers();
        addComponentsToContainers();
        addListeners();
        makeEverythingVisible();
    }

    private static void initializeVariables() throws InterruptedException {
        mainLine = new ArrayBlockingQueue<Person>(96);
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
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        coasterDrawingPanel = new CoasterDrawingPanel(coaster);
        frame.setContentPane(mainPanel);
    }
    private static void buildComponents() {
        stopSimulationButton = new JButton("Stop Simulation");
        stopSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                personSpawner.interrupt();
                coasterDriver.interrupt();
                lineCoordinator.interrupt();
            }
        });
        sendCoasterButton = new JButton("Send Coaster");
        sendCoasterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                coaster.train1.setShouldDepart(true);
                //CoasterSimulator.notifyAll();
            }
        });
    }
    private static void nestContainers() {
        mainPanel.add(coasterDrawingPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }
    private static void addComponentsToContainers() {
        controlPanel.add(stopSimulationButton);
        controlPanel.add(sendCoasterButton);
    }
    private static void addListeners() {
    }
    private static void configureLayouts() {
        mainPanel.setLayout(new BorderLayout());
        controlPanel.setLayout(new FlowLayout());
    }
    private static void makeEverythingVisible() {
        frame.setVisible(true);
    }
}
