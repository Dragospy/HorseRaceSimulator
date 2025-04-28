package Part2;

import Part1.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class helperMethods {

    //Formats words to be in the following format: colour -> Colour, horseColour -> Horse Colour
    public static String formatWord(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder output = new StringBuilder();
        char[] chars = input.toCharArray();

        output.append(Character.toUpperCase(chars[0])); // capitalize first letter

        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                output.append(' '); // insert space before uppercase letter
            }
            output.append(c);
        }

        return output.toString();
    }

    //Saves the data of a given horse
    public static void saveHorse(Horse horse){
        databaseHandler horseData = new databaseHandler("./Part2/database/horses.csv");
        databaseHandler horseAttributes= new databaseHandler("./Part2/database/attributes.csv");
        databaseHandler horseAccessories = new databaseHandler("./Part2/database/accessories.csv");

        horseData.updateIf(1, horse.getName(), Arrays.asList(horse.getSymbol(), horse.getName(), String.valueOf(horse.getConfidence())));

        ArrayList<String> attributeValues = new ArrayList<>();

        attributeValues.add((horse.getName()));

        for (String attributeValue : horse.getAttributeMap().values()){
            attributeValues.add(attributeValue);
        }

        horseAttributes.updateIf(0, horse.getName(), attributeValues);

        ArrayList<String> accessoryValues = new ArrayList<>();

        accessoryValues.add(horse.getName());

        for (String accessoryValue : horse.getAccessoriesMap().values()){
            accessoryValues.add(accessoryValue);
        }

        horseAccessories.updateIf(0, horse.getName(), accessoryValues);

    }

    //Saves the race data of a given horse so that it can be used for statistics
    public static void saveRaceData(Horse horse, String condition){
        databaseHandler horseData = new databaseHandler("./Part2/database/horseRaceData.csv");
        List<String> data = new ArrayList<>();
        double[] horseRaceData = horse.getCurrentRaceData();


        data.add(horse.getName());

        double averageSpeed = truncate((horseRaceData[0]/horseRaceData[1]), 2);
        data.add((String.valueOf(averageSpeed).equals("NaN"))? String.valueOf(0): String.valueOf(averageSpeed));


        if (horse.hasFallen()){
            data.add("DNF");
        }
        else{
            data.add(String.valueOf(truncate(horseRaceData[1], 2)));
        }
        
        if (horseRaceData[2] == 0){
            data.add("LOST");
        }
        else if (horseRaceData[2] == 1){
            data.add("WON");
        }
        else if (horseRaceData[2] == 2){
            data.add("FELL OVER");
        }

        data.add(condition);
        
        horseData.insert(data);
    }

    //Truncates doubles to a given number of decimal places
    public static double truncate(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.floor(value * factor) / factor;
    }

}