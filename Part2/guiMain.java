package Part2;

import Part1.*;
import javax.swing.*;
public class guiMain {

    private final static valueContainer laneCount = new valueContainer(3);
    private final static valueContainer trackLength = new valueContainer(15);

    public static void main(String[] args) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Track Setup", trackPanel());
        tabs.addTab("Race Start", racePanel());

        initialiseFrame("Race Horse Simulator", tabs);
    }

    public static void startTheRace(){
        Race currentRace = new Race(trackLength.value, true);

        for (int i = 0; i < laneCount.value; i++) {
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

//Increment / Decrement element
    private static JPanel idElement(String valueName, String displayName, valueContainer value){
        //Custom lane count
        JPanel laneCountPanel = new JPanel();
        JLabel laneCountLabel = new JLabel(displayName+": " + value.value);

        // Increments lane count by 1
        JButton incrementButton = createButton("Increment " + valueName, 0, 0);
        incrementButton.addActionListener(e -> {
            value.value++;
            laneCountLabel.setText(displayName+": " + value.value);
        });

        // Decrements lane count by 1
        JButton decrementButton = createButton("Decrement " + valueName, 0, 0);
        decrementButton.addActionListener(e -> {
            if (value.value >0){
                value.value--;
                laneCountLabel.setText(displayName+": " + value.value);   
            }
        });

        //Add components to panel
        laneCountPanel.add(incrementButton);
        laneCountPanel.add(laneCountLabel);
        laneCountPanel.add(decrementButton);

        return laneCountPanel;
    }

    public static JPanel trackPanel(){
        //Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Track Customization");
        title.setAlignmentX(title.CENTER_ALIGNMENT);
        mainPanel.add(title);

        //Add panels
        mainPanel.add(idElement("Lanes", "lane", laneCount));
         mainPanel.add(idElement("Track length", "length", trackLength));
        return mainPanel;
    }

    public static void initialiseFrame(String title, JComponent tabs) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tabs);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
}


class valueContainer{
    int value;

    public valueContainer(int newValue) {
        value = newValue;
    }
}