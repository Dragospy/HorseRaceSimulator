package Part2;

import Part1.*;
import java.util.ArrayList;
import javax.swing.*;

public class guiMain {

    private final static valueContainer laneCount = new valueContainer(3);
    private final static valueContainer trackLength = new valueContainer(15);
    private static JPanel raceTrack = null;
    private final static ArrayList<Horse> horses = new ArrayList<>();

    public static void main(String[] args) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Track Setup", trackPanel());
        tabs.addTab("Horse Customization", horseCustomization());
        tabs.addTab("Race Stats", statistics());
        tabs.addTab("Race Bets", betting());
        tabs.addTab("Race Start", racePanel());

        initialiseFrame("Race Horse Simulator", tabs);
    }

    public static void initialiseFrame(String title, JComponent tabs) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tabs);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }


    //Panels

    public static JPanel betting(){
        //Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        return mainPanel;
    }

    public static JPanel statistics(){
        //Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        return mainPanel;
    }

    public static JPanel horseCustomization(){
        //Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        return mainPanel;
    }

    public static JPanel trackPanel(){
        //Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Track Customization");
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mainPanel.add(title);

        JPanel contentPanel = new JPanel();

        //Add panels
        contentPanel.add(idElement("Number of Lanes","Lanes", "lane", laneCount, 0, 15));
        contentPanel.add(idElement("Track Length" ,"Track length", "length", trackLength, 5, 40));

        mainPanel.add(contentPanel);

        return mainPanel;
    }

    public static JPanel racePanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Race Start"));

        //Start Race
        JButton startButton = Button("Start", 25, 25);
        
        startButton.addActionListener(e -> {
            startTheRace();
        });

        panel.add(startButton);
        
        return panel;
    }

    //Methods

    public static void startTheRace(){
        Race currentRace = new Race(trackLength.value, true);

        for (int i = 0; i < laneCount.value; i++) {
            currentRace.addHorse(new Horse((char)(i + '0'), "Horse " + i,0.8));
        }

        currentRace.startRace();
    }

    //Elements

    public static JButton Button(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setSize(button.getPreferredSize());
        button.setLocation(x, y);
        return button;
    }

    //Increment / Decrement element
    private static JPanel idElement(String title, String valueName, String displayName, valueContainer value, int min, int max){
        //Custom lane count
        JPanel valuePanel = new JPanel();
        JLabel elementTitle = new JLabel(title);
        elementTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JLabel laneCountLabel = new JLabel(displayName+": " + value.value);
        laneCountLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Increments lane count by 1
        JButton incrementButton = Button("Increment " + valueName, 0, 0);
        incrementButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        incrementButton.addActionListener(e -> {
            if (value.value < max){
                value.value++;
                laneCountLabel.setText(displayName+": " + value.value);
            }
        });

        // Decrements lane count by 1
        JButton decrementButton = Button("Decrement " + valueName, 0, 0);
        decrementButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        decrementButton.addActionListener(e -> {
            if (value.value > min){
                value.value--;
                laneCountLabel.setText(displayName+": " + value.value);   
            }
        });

        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));

        //Add components to panel
        valuePanel.add(elementTitle);
        valuePanel.add(incrementButton);
        valuePanel.add(laneCountLabel);
        valuePanel.add(decrementButton);

        return valuePanel;
    }
}

class valueContainer{
    int value;

    public valueContainer(int newValue) {
        value = newValue;
    }
}
