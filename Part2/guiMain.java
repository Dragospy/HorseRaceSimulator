package Part2;

import Part1.*;
import java.awt.event.ItemEvent;
import javax.swing.*;

public class guiMain {
    private final static int height = 800;
    private final static int width = 1200;
    private final static valueContainer laneCount = new valueContainer(3);
    private final static valueContainer trackLength = new valueContainer(15);
    private static raceTrack raceTrack;
    private final static Horse[] availableHorses = {
        new Horse('A', "Horse A" ,0.8),
        new Horse('B', "Horse B" ,0.8),
        new Horse('C', "Horse C" ,0.8),
        new Horse('D', "Horse D" ,0.8),
        new Horse('E', "Horse E" ,0.8),
        new Horse('F', "Horse F" ,0.8),
        new Horse('G', "Horse G" ,0.8),
        new Horse('H', "Horse H" ,0.8),
        new Horse('I', "Horse I" ,0.8),
        new Horse('J', "Horse J" ,0.8),
        new Horse('K', "Horse K" ,0.8),
        new Horse('L', "Horse L" ,0.8),
        new Horse('M', "Horse M" ,0.8),
        new Horse('N', "Horse N" ,0.8),
        new Horse('O', "Horse O" ,0.8),
    }; 
    private final static Horse[] selectedHorses = new Horse[15];

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
        frame.setSize(width, height);
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
        JPanel laneSelectors = new JPanel();
        JLabel title = new JLabel("Track Customization");
        JPanel contentPanel = new JPanel();
        String[] horseNames = new String[availableHorses.length];

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        laneSelectors.setLayout(new BoxLayout(laneSelectors, BoxLayout.Y_AXIS));

        for (int i = 0; i < availableHorses.length; i++){
            horseNames[i] = availableHorses[i].getName();
        }


        //Add panels
        JPanel laneCountElement = idElement("Number of Lanes","Lanes", "lane", laneCount, 0, 15, () -> {
            int count = laneSelectors.getComponentCount();
            if (count > laneCount.value) {
                laneSelectors.remove(count - 1); // remove the last component
                laneSelectors.revalidate();      // re-layout the panel
                laneSelectors.repaint();         // repaint the UI
            }
            else if (count < laneCount.value) {
                JPanel laneSelector = laneSelector(laneCount.value, horseNames);
                laneSelectors.add(laneSelector);
            }

            if (raceTrack != null){
                raceTrack.changeLaneCount(laneCount.value);
            }
        });

        JPanel trackLengthElement = idElement("Track Length" ,"Track length", "length", trackLength, 5, 40, () -> {
            if (raceTrack != null){
                raceTrack.changeTrackLength(trackLength.value);
            }
        });

        for (int i = 1; i <= laneCount.value; i ++){
            JPanel laneSelector = laneSelector(i, horseNames);
            laneSelectors.add(laneSelector);
        }

        contentPanel.add(laneCountElement);
        contentPanel.add(trackLengthElement);

        mainPanel.add(title);
        mainPanel.add(contentPanel);
        mainPanel.add(laneSelectors);

        return mainPanel;
    }

    public static JPanel racePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(new JLabel("Race Start"));

        //Start Race
        JButton startButton = Button("Start", 0, 25);
        startButton.setSize(100, 50); // Set a fixed size for the button
        startButton.setLocation((width - startButton.getWidth()) / 2, 25);
        
        startButton.addActionListener(e -> {
            startTheRace();
        });

        panel.add(startButton);

        raceTrack = new raceTrack(width, laneCount.value, 100, trackLength.value, selectedHorses);

        panel.add(raceTrack);
        
        return panel;
    }

    //Methods

    public static void startTheRace(){
        Race currentRace = new Race(trackLength.value, true);

        currentRace.setRaceTrackGui(raceTrack);

        for (int i = 0; i < laneCount.value; i++) {
            currentRace.addHorse(selectedHorses[i]);
        }

        new Thread(() -> currentRace.startRace()).start(); 
    }

    public static void updateHorsePosition(int i){
        if (raceTrack != null){
            raceTrack.updateHorse(i);
        }
    }

    //Elements

    public static JButton Button(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setSize(button.getPreferredSize());
        button.setLocation(x, y);
        return button;
    }

    private static JPanel laneSelector(int lane, String[] horseNames){
        JPanel panel = new JPanel();
        JLabel elementTitle = new JLabel("Lane " + lane);
        JComboBox<String> laneSelector = new JComboBox<>(horseNames);
        
        laneSelector.addItemListener((ItemEvent arg0) -> {
            selectedHorses[lane-1] = availableHorses[laneSelector.getSelectedIndex()];
            if (raceTrack != null){
                raceTrack.updateHorse(lane-1);
            }
        });

        selectedHorses[lane-1] = availableHorses[lane-1];
        laneSelector.setSelectedIndex(lane - 1);

        panel.add(elementTitle);
        panel.add(laneSelector);

        return panel;
    }

    //Increment / Decrement element
    private static JPanel idElement(String title, String valueName, String displayName, valueContainer value, int min, int max, passedFunction func){
        //Custom lane count
        JPanel valuePanel = new JPanel();
        JLabel elementTitle = new JLabel(title);
        JLabel laneCountLabel = new JLabel(displayName+": " + value.value);
        JButton incrementButton = Button("Increment " + valueName, 0, 0);
        JButton decrementButton = Button("Decrement " + valueName, 0, 0);
        elementTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        laneCountLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        incrementButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        decrementButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        // Increments lane count by 1
        incrementButton.addActionListener(e -> {
            if (value.value < max){
                value.value++;
                laneCountLabel.setText(displayName+": " + value.value);
                func.execute();
            }
        });

        // Decrements lane count by 1
        decrementButton.addActionListener(e -> {
            if (value.value > min){
                value.value--;
                laneCountLabel.setText(displayName+": " + value.value);
                func.execute();   
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
interface passedFunction{
    void execute();
}