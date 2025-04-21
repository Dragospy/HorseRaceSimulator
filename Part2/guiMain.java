package Part2;

import Part1.*;
import javax.swing.*;

public class guiMain {
    private static int laneCount = 3; // Number of lanes
    public static void main(String[] args) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Track Setup", trackPanel());
        tabs.addTab("Race Start", racePanel());

        initialiseFrame("Race Horse Simulator", tabs);
    }

    public static void startTheRace(){
        Race currentRace = new Race(15, true);

        for (int i = 0; i < laneCount; i++) {
            currentRace.addHorse(new Horse((char)(i + '0'), "Horse " + i,0.8));
        }

        currentRace.startRace();
    }

    public static JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setSize(button.getPreferredSize());
        button.setLocation(x, y);
        return button;
    }

    public static JPanel racePanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Race Start"));

        JButton startButton = createButton("Start", 25, 25);

        startButton.addActionListener(e -> {
            startTheRace();
        });

        panel.add(startButton);

        return panel;
    }

    public static JPanel trackPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Track Customization"));

        JButton button = createButton("Increment lane", 0, 0);

        // Display a message dialog when the button is clicked
        button.addActionListener(e -> {
            laneCount++;
        });

        panel.add(button);

        return panel;
    }

    public static void initialiseFrame(String title, JComponent tabs) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tabs);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
