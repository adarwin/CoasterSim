package com.adarwin.coaster;

import java.awt.BorderLayout;
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

public class CoasterSimulator {
    protected static Coaster coaster;
    private static JFrame frame;
    private static JButton stopSimulationButton;
    private static JPanel mainPanel, controlPanel;
    private static CoasterDrawingPanel coasterDrawingPanel;
    protected static BlockingQueue<Person> mainLine;
    protected static BlockingQueue<Person> car1Line, car2Line,
                                           car3Line, car4Line;
    private static Thread personSpawner, lineCoordinator, coasterDriver;

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        initializeVariables();

        personSpawner = new Thread(() -> {
            try {
                Thread.sleep(3000);
                for (int i = 0; i < 50; i++) {
                    mainLine.put(new Person());
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            coasterDrawingPanel.repaint();
                        }
                    });
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                //throw e;
                //break;
            }
        });


        lineCoordinator = new Thread(() -> {
            for (;;) {
                try {
                    // Take a person from the main line
                    Person nextPerson = mainLine.take();
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
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            coasterDrawingPanel.repaint();
                        }
                    });
                } catch (InterruptedException ex) {
                    break;
                }
            }
        });


        coasterDriver = new Thread(() -> {
            try {
                boolean atStart = true;
                for (;;) {//int i = 0; i < 50; i++) {
                    if (atStart) {
                        int numberOfCars = coaster.train1.getNumberOfCars();
                        // Remove any existing passengers
                        for (Car car : coaster.train1.getCars()) {
                            car.passengers.clear();
                        }
                        SwingUtilities.invokeLater(() -> {
                            coasterDrawingPanel.repaint();
                        });
                        Thread.sleep(2000);
                        for (int j = 0; j < numberOfCars; j++) {
                            Car car = coaster.train1.getCar(j);
                            while (car.passengers.remainingCapacity() > 0) {
                                Person person;
                                switch (j) {
                                case 0:
                                    System.out.println("Taking from car1line");
                                    person = car1Line.take();
                                    car.passengers.offer(person);
                                    break;
                                case 1:
                                    System.out.println("Taking from car2line");
                                    person = car2Line.take();
                                    car.passengers.offer(person);
                                    break;
                                case 2:
                                    System.out.println("Taking from car3line");
                                    person = car3Line.take();
                                    car.passengers.offer(person);
                                    break;
                                case 3:
                                    System.out.println("Taking from car4line");
                                    person = car4Line.take();
                                    car.passengers.offer(person);
                                    break;
                                }
                            }
                                SwingUtilities.invokeLater(() -> {
                                    coasterDrawingPanel.repaint();
                                });
                        }
                        // Try to pick up people
                        // for each car, pick up the people in the associated line
                    }
                    atStart = coaster.train1.advance();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            coasterDrawingPanel.repaint();
                        }
                    });
                    //if (atStart) break;
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
        coaster = new Coaster(2, 4);
        mainLine = new ArrayBlockingQueue<Person>(500);
        for (int i = 0; i < 500; i++) {
            //mainLine.put(new Person());
        }
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
    }
    private static void nestContainers() {
        mainPanel.add(coasterDrawingPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }
    private static void addComponentsToContainers() {
        controlPanel.add(stopSimulationButton);
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
