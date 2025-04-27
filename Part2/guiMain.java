package Part2;

import Part1.*;
import java.awt.event.ItemEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class guiMain {
    private final static int height = 800;
    private final static int width = 1200;
    private final static valueContainer laneCount = new valueContainer(3);
    private final static valueContainer trackLength = new valueContainer(15);
    private final static Horse[] availableHorses = new Horse[15];
    private final static Horse[] selectedHorses = new Horse[15];
    private static Horse currentHorse = null;
    private static Race currentRace = null;
    private static raceTrack raceTrack;

    public static void main(String[] args) {
        loadHorses();

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
        String[] horseNames = new String[availableHorses.length];
        JPanel horseSelectorPanel = new JPanel();
        JPanel customizationPanel = new customizationPanel(availableHorses[0], raceTrack, 0);
        JLabel selectorTitle = new JLabel("Select Horse ");
        currentHorse = availableHorses[0];
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Horse selector

        for (int i = 0; i < availableHorses.length; i++){
            horseNames[i] = availableHorses[i].getName();
        }

        JComboBox<String> horseSelector = new JComboBox<>(horseNames);
        
        horseSelector.addItemListener((ItemEvent item) -> {
            if (raceTrack != null){
                currentHorse = availableHorses[horseSelector.getSelectedIndex()];
                mainPanel.remove(mainPanel.getComponentCount() - 1);
                mainPanel.add(new customizationPanel(availableHorses[horseSelector.getSelectedIndex()], raceTrack, horseSelector.getSelectedIndex()));
                mainPanel.revalidate();      // re-layout the panel
                mainPanel.repaint();         // repaint the UI
            }
        });
        
        horseSelector.setSelectedIndex(0);
        horseSelectorPanel.add(selectorTitle);
        horseSelectorPanel.add(horseSelector);

        mainPanel.add(horseSelectorPanel);
        mainPanel.add(customizationPanel);

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

        raceTrack = new raceTrack(width, laneCount.value, 100, trackLength.value, selectedHorses);

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
        panel.add(raceTrack);
        
        return panel;
    }

    //Methods

    public static void loadHorses(){
        databaseHandler horseData = new databaseHandler("./Part2/database/horses.csv");
        databaseHandler horseAttributes= new databaseHandler("./Part2/database/attributes.csv");
        databaseHandler horseAccessories = new databaseHandler("./Part2/database/accessories.csv");
        optionLoader loader = new optionLoader();   
        
        List<List<String>> records = horseData.readAll();
        List<List<String>> allAttributes = horseAttributes.readAll();
        List<List<String>> allAccessories = horseAccessories.readAll();
        int counter = 0;

        for (List<String> record : records) {
            Map<String, String> attributes = new LinkedHashMap<>();
            int attributeCount = 1;
            for (String attribute: loader.getAttributeTypes()){
                attributes.put(attribute, (allAttributes.get(counter)).get(attributeCount));
                attributeCount++;
            }

            Map<String, String> accessories = new LinkedHashMap<>();
            int accessoryCount = 1;
            for (String accessory: loader.getAccessoryTypes()){
                accessories.put(accessory, (allAccessories.get(counter)).get(accessoryCount));
                accessoryCount++;
            }

            availableHorses[counter] = new Horse(record.get(0), record.get(1), Double.parseDouble(record.get(2)), attributes, accessories);
            counter++;
        }
    }

    public static void startTheRace(){
        if (currentRace != null && currentRace.isFinished()){
            currentRace = new Race(trackLength.value, true);

            currentRace.setRaceTrackGui(raceTrack);

            for (int i = 0; i < laneCount.value; i++) {
                currentRace.addHorse(selectedHorses[i]);
            }

            new Thread(() -> currentRace.startRace()).start(); 
        }
        else if (currentRace != null && !currentRace.isFinished()){
            JOptionPane.showMessageDialog(null, "A race is already ongoing", "Error", JOptionPane.WARNING_MESSAGE);
        }
        else if (currentRace == null){
            currentRace = new Race(trackLength.value, true);

            currentRace.setRaceTrackGui(raceTrack);

            for (int i = 0; i < laneCount.value; i++) {
                currentRace.addHorse(selectedHorses[i]);
            }

            new Thread(() -> currentRace.startRace()).start(); 
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
        
        laneSelector.addItemListener((ItemEvent item) -> {
            selectedHorses[lane-1] = availableHorses[laneSelector.getSelectedIndex()];
            if (raceTrack != null){
                System.out.println(laneSelector.getSelectedIndex());
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
