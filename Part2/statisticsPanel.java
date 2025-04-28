package Part2;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class statisticsPanel extends JTabbedPane{
    private String[] horseNames;
    private databaseHandler database = new databaseHandler("./Part2/database/horseRaceData.csv");
    List<List<String>> data = database.readAll();
    Map<String, List<String>> horsePerformanceData = new HashMap<>();

    //Loads each of the different statistics tabs
    public statisticsPanel(String[] horseNames) {
        this.horseNames = horseNames;

        this.add("Races", raceHistory());
        this.add("Horses", horsePerformance());
        this.add("Tracks", trackStatistics());
    }

    //Displays a table that shows the different races that have happened
    //Lets user filter to only display races from certain horses
    public JPanel raceHistory(){
        JPanel main = new JPanel();
        String[] columnNames = {
            "Horse Name",
            "Average Speed",
            "Time To Finish",
            "Race Status",
            "Track Condition"
        };

        JPanel horseSelector = selector("Select a horse to filter for", (filter) -> {
            data = database.readAll();
            main.remove(main.getComponentCount() - 1);
            main.add(displayRaceTable(data, columnNames, (String) filter));
            main.revalidate();      // re-layout the panel
            main.repaint();         // repaint the UI
        });

        main.add(horseSelector);

        main.add(displayRaceTable(data, columnNames, "All"));

        horseSelector.setAlignmentX(CENTER_ALIGNMENT);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        return main;
    }

    //Displays a table that holds the average performance of each horse
    //User can filter to show only a selected horse to make it easier to read the data
    public JPanel horsePerformance(){
        JPanel main = new JPanel();
        String[] columnNames = {
            "Horse Name",
            "Average Speed",
            "Average Time To Finish",
            "Win Rate",
            "Fall Rate",
            "Best Track Condition"
        };

        JPanel horseSelector = selector("Select a horse to filter for", (filter) -> {
            data = database.readAll();
            main.remove(main.getComponentCount() - 1);
            main.add(displayPerformanceTable(data, columnNames, (String) filter));
            main.revalidate();      // re-layout the panel
            main.repaint();         // repaint the UI
        });

        main.add(horseSelector);

        main.add(displayPerformanceTable(data, columnNames, "All"));

        horseSelector.setAlignmentX(CENTER_ALIGNMENT);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        return main;
    }

    //Displays a table to holds the statistics for each of the race track conditions
    public JPanel trackStatistics(){
        JPanel main = new JPanel();
        String[] columnNames = {
            "Track Condition",
            "Best Average Speed",
            "Best Time To Finish",
            "Worst Time To Finish",
        };

        main.add(displayTrackTable(data, columnNames, "All"));

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        return main;
    }

    //Displays the actual performance table, it does this by working out the data for the table
    //Using the data from horseRaceData.csv file
    private JScrollPane displayPerformanceTable(List<List<String>> data, String[] columnNames, String filter) {
        for (List<String> row: data){
            if (horsePerformanceData.get(row.get(0)) == null ){
                int count = 0;
                double averageSpeed = 0;
                double averageTime = 0;
                double winRate = 0;
                double fallRate = 0;
                Map<String, Integer> trackConditionTracker = new HashMap<>();
                String bestTrackCondition = "none";
                for (List<String> row2: data){
                        if (row2.get(0).equals(row.get(0))){
                            averageSpeed += Double.valueOf(row2.get(1));
                        if (!row2.get(2).equals("DNF")){
                            averageTime += Double.valueOf(row2.get(2));
                        }
                        if (row2.get(3).equals("WON")){
                            winRate += 1;
                        }
                        else if (row2.get(3).equals("FELL OVER")){
                            fallRate += 1;
                        }
                        
                        if (row2.get(3).equals("WON")){
                            if(trackConditionTracker.get(row2.get(4)) == null){
                                trackConditionTracker.put(row2.get(4), 1);
                            }
                            else if (trackConditionTracker.get(row2.get(4)) != null){
                                trackConditionTracker.put(row2.get(4), trackConditionTracker.get(row2.get(4)) + 1);
                            }
                        }

                        count++;
                    }
                }

                averageSpeed = helperMethods.truncate(averageSpeed / count, 2);
                averageTime = helperMethods.truncate(averageTime / count, 2);
                winRate = helperMethods.truncate(winRate / count, 2) * 100;
                fallRate = helperMethods.truncate(fallRate / count, 2) * 100;

                count = 0;
                for (String key: trackConditionTracker.keySet()){
                    if (trackConditionTracker.get(key) > count){
                        count = trackConditionTracker.get(key);
                        bestTrackCondition = key;
                    }
                }

                List<String> processedData = new ArrayList<>();
                processedData.add(row.get(0));
                processedData.add(String.valueOf(averageSpeed));
                processedData.add(String.valueOf(averageTime));
                processedData.add(String.valueOf(winRate));
                processedData.add(String.valueOf(fallRate));
                processedData.add(String.valueOf(bestTrackCondition));
                
                horsePerformanceData.put(row.get(0), processedData);
            }
        }


        int counter = 0;
        boolean empty = true;
        Object[][] tableData = new Object[15][6];
        for (List<String> row: horsePerformanceData.values()){
            if (filter.equals("All")){
                tableData[counter][0] = row.get(0);
                tableData[counter][1] = row.get(1);
                tableData[counter][2] = row.get(2);
                tableData[counter][3] = row.get(3);
                tableData[counter][4] = row.get(4);
                tableData[counter][5] = row.get(5);

                counter++;

                empty = false;
            }
            else{
                if ((row.get(0)).equals(filter)){
                    tableData[counter][0] = row.get(0);
                    tableData[counter][1] = row.get(1);
                    tableData[counter][2] = row.get(2);
                    tableData[counter][3] = row.get(3);
                    tableData[counter][4] = row.get(4);
                    tableData[counter][5] = row.get(5);

                    counter++;

                    empty = false;
                }
            }
        }

        if (!empty){
            JTable table = new JTable(tableData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            return scrollPane;
        }
        else{
            JScrollPane scrollPane = new JScrollPane(new JLabel("No data!"));

            return scrollPane;
        }
    }

    //Displays the actual Race data table using the data from the same file as the previous method
    private JScrollPane displayRaceTable(List<List<String>> data, String[] columnNames, String filter) {
        int counter = 0;
        boolean empty = true;
        Object[][] tableData = new Object[data.size()][5];
        for (List<String> row: data){
            if (filter.equals("All")){
                tableData[counter][0] = row.get(0);
                tableData[counter][1] = row.get(1);
                tableData[counter][2] = row.get(2);
                tableData[counter][3] = row.get(3);
                tableData[counter][4] = row.get(4);

                counter++;

                empty = false;
            }
            else{
                if ((row.get(0)).equals(filter)){
                    tableData[counter][0] = row.get(0);
                    tableData[counter][1] = row.get(1);
                    tableData[counter][2] = row.get(2);
                    tableData[counter][3] = row.get(3);
                    tableData[counter][4] = row.get(4);

                    counter++;

                    empty = false;
                }
            }
        }

        if (!empty){
            JTable table = new JTable(tableData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            return scrollPane;
        }
        else{
            JScrollPane scrollPane = new JScrollPane(new JLabel("No data!"));

            return scrollPane;
        }
    }

    //Displays the track panel using data from the same file as the previous method
    private JScrollPane displayTrackTable(List<List<String>> data, String[] columnNames, String filter) {
        Map<String, List<String>> trackData = new HashMap<>();
        for (List<String> row: data){
            if (trackData.get(row.get(4)) == null ){
                double bestTime = 0;
                double bestSpeed = 0;
                double worstTime = 0;

                for (List<String> row2: data){
                    if (row2.get(4).equals(row.get(4))){
                        if (!row2.get(2).equals("DNF")){
                            double currentTime = Double.parseDouble(row2.get(2));
                            if (worstTime < currentTime){
                                worstTime = currentTime;
                            }
                        }

                        double currentSpeed = Double.parseDouble(row2.get(1));
                        if (bestSpeed < currentSpeed){
                            bestSpeed = currentSpeed;
                        }

                        bestTime = worstTime;
                        if (!row2.get(2).equals("DNF")){
                            double currentTime = Double.parseDouble(row2.get(2));
                            if (bestTime > currentTime){
                                bestTime = currentTime;
                            }
                        }
                    }
                }

                List<String> processedData = new ArrayList<>();
                processedData.add(String.valueOf(row.get(4)));
                processedData.add(String.valueOf(bestSpeed));
                processedData.add(String.valueOf(bestTime));
                processedData.add(String.valueOf(worstTime));
                
                trackData.put(row.get(4), processedData);
            }
        }


        int counter = 0;
        boolean empty = true;
        Object[][] tableData = new Object[trackData.size()][4];
        for (List<String> row: trackData.values()){
            if (filter.equals("All")){
                tableData[counter][0] = row.get(0);
                tableData[counter][1] = row.get(1);
                tableData[counter][2] = row.get(2);
                tableData[counter][3] = row.get(3);

                counter++;

                empty = false;
            }
            else{
                if ((row.get(0)).equals(filter)){
                    tableData[counter][0] = row.get(0);
                    tableData[counter][1] = row.get(1);
                    tableData[counter][2] = row.get(2);
                    tableData[counter][3] = row.get(3);

                    counter++;

                    empty = false;
                }
            }
        }

        if (!empty){
            JTable table = new JTable(tableData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            return scrollPane;
        }
        else{
            JScrollPane scrollPane = new JScrollPane(new JLabel("No data!"));

            return scrollPane;
        }
    }

    //a generic selector that executes the function it is given
    private JPanel selector(String Label, passedFunctionWithParam<String> method){
        JPanel panel = new JPanel();
        JLabel elementTitle = new JLabel(Label);
        String[] options = new String[horseNames.length + 1];

        options[0] = "All";
        for (int i = 1; i < options.length; i++){
            options[i] = horseNames[i - 1];
        }

        JComboBox<String> laneSelector = new JComboBox<>(options);
        
        laneSelector.addItemListener((ItemEvent item) -> {
            method.execute((String) item.getItem());            
        });

        laneSelector.setSelectedIndex(0);

        panel.add(elementTitle);
        panel.add(laneSelector);

        elementTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        laneSelector.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }
}