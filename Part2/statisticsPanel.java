package Part2;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public statisticsPanel(String[] horseNames) {
        this.horseNames = horseNames;

        this.add("Races", raceHistory());
        this.add("Horses", horsePerformance());
    }

    public JPanel raceHistory(){
        JPanel main = new JPanel();
        String[] columnNames = {
            "Horse Name",
            "Average Speed",
            "Time To Finish",
            "Race Status",
            "Track Condition"
        };

        JPanel horseSelector = selector("Horse", (filter) -> {
            data = database.readAll();
            main.remove(main.getComponentCount() - 1);
            main.add(displayRaceTable(data, columnNames, (String) filter));
            main.revalidate();      // re-layout the panel
            main.repaint();         // repaint the UI
        });

        main.add(horseSelector);

        main.add(displayRaceTable(data, columnNames, "All"));

        return main;
    }

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

        JPanel horseSelector = selector("Horse", (filter) -> {
            data = database.readAll();
            main.remove(main.getComponentCount() - 1);
            main.add(displayPerformanceTable(data, columnNames, (String) filter));
            main.revalidate();      // re-layout the panel
            main.repaint();         // repaint the UI
        });

        main.add(horseSelector);

        main.add(displayPerformanceTable(data, columnNames, "All"));

        return main;
    }

    private JScrollPane displayPerformanceTable(List<List<String>> data, String[] columnNames, String filter) {
        for (List<String> row: data){
            System.out.println(row.get(0));
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
                winRate = helperMethods.truncate(winRate / count, 2);
                fallRate = helperMethods.truncate(fallRate / count, 2);

                count = 0;
                for (String key: trackConditionTracker.keySet()){
                    if (trackConditionTracker.get(key) > count){
                        count = trackConditionTracker.get(key);
                        bestTrackCondition = key;
                    }
                }

                System.out.println(averageSpeed + " " + averageTime + " " + winRate + " " + fallRate + " " + bestTrackCondition);

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
        Object[][] tableData = new Object[data.size()][6];
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

        return panel;
    }

}