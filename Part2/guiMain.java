package Part2;

import Part1.*;
import java.util.ArrayList;
import javax.swing.*;

public class guiMain {
    private static int laneCount = 3; // Number of lanes
    public static void main(String[] args) {
        ArrayList<JComponent> components = new ArrayList<>();   
        
        JButton button = createButton("Increment lane", 0, 0);
        JButton startButton = createButton("Start", 25, 25);

        // Display a message dialog when the button is clicked
        button.addActionListener(e -> {
            laneCount++;
        });

        startButton.addActionListener(e -> {
            startTheRace();
        });

        components.add(button);
        components.add(startButton);

        initialiseFrame("Horse Racing Simulator", components);
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

    public static void initialiseFrame(String title, ArrayList<JComponent> components) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Use null layout to allow manual positioning of components
        for (JComponent component : components) {
            frame.add(component);
        }
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
