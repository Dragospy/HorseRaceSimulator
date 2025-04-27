package Part2;

import Part1.*;
import java.util.ArrayList;
import java.util.Arrays;

public class helperMethods {
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

    public static double truncate(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.floor(value * factor) / factor;
    }

}