package com.adarwin.coaster;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CoasterSimulator {
    private static Coaster coaster;
    private static JFrame frame;
    private static CoasterDrawingPanel coasterDrawingPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        coaster = new Coaster(2, 4);
    }

    private static void createAndShowGUI() {
        initializeVariables();
        buildContainers();
        buildComponents();
        addListeners();
        configureLayouts();
        makeEverythingVisible();
    }

    private static void initializeVariables() {
        coaster = new Coaster(2, 4);
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
