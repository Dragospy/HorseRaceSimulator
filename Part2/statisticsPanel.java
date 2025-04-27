package Part2;

import Part1.*;
import java.io.Console;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class statisticsPanel extends JTabbedPane{

    public statisticsPanel() {
        this.add("Races", raceHistory());
    }

    public JPanel raceHistory(){
        JPanel main = new JPanel();
        databaseHandler database = new databaseHandler("./Part2/database/horseRaceData.csv");
        List<List<String>> data = database.readAll();
        Object[][] tableData = new Object[data.size()][5];
        String[] columnNames = {
            "Horse Name",
            "Average Speed",
            "Time To Finish",
            "Race Status",
            "Track Condition"
        };

        int counter = 0;
        for (List<String> row: data){
            tableData[counter][0] = row.get(0);
            tableData[counter][1] = row.get(1);
            tableData[counter][2] = row.get(2);
            tableData[counter][3] = row.get(3);
            tableData[counter][4] = row.get(4);

            counter++;
        }

        JTable table = new JTable(tableData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);


        main.add(scrollPane);

        return main;
    }
    
    
}