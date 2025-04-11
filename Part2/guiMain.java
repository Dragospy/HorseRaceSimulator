package Part2;

import Part1.*;
import java.util.ArrayList;
import javax.swing.*;

public class guiMain {
    public static void main(String[] args) {
        ArrayList<JComponent> components = new ArrayList<>();    
        
        JButton button = createButton("Cool Button", 0, 0);

        // Display a message dialog when the button is clicked
        button.addActionListener(e -> JOptionPane.showMessageDialog(null,
        "Welcome to OOP at Queen Mary University of London"));

        components.add(button);

        initialiseFrame("Horse Racing Simulator", components);

        Race currentRace = new Race(15, false);

        Horse horse1 = new Horse('A', "Horse A",0.8);
        Horse horse2 = new Horse('B', "Horse B", 0.6);
        Horse horse3 = new Horse('C', "Horse C", 0.7);
        currentRace.addHorse(horse1);
        currentRace.addHorse(horse2);
        currentRace.addHorse(horse3);

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
