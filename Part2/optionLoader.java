package Part2;

import java.io.*;
import java.util.*;

public class optionLoader {
    private String optionFolder;

    private final Map<String, List<String>> optionList = new LinkedHashMap<>();

    //Sets the option folder that we will be checking and loads said option folder
    public optionLoader(String option) {
        this.optionFolder = "./Part2/assets/"+option;
        loadOption();
    }

    //Checks if option folder exists first
    //It then sorts them alphabetically and saves a map that contains each option along with its suboptions 
    //from that folder
    private void loadOption() {
        File folder = new File(optionFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder not found: " + optionFolder);
            return;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::getName)); // Sort alphabetically by filename
            for (File file : files) {
                if (file.isFile()) {
                    optionList.put(file.getName().replace(".txt", ""), readOptions(file));
                }
            }
        }
    }

    //Returns a String list of the different options of a given file
    private List<String> readOptions(File file) {
        List<String> options = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    options.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getName());
            System.out.println("The following error occured: " + e );
        }
        return options;
    }

    public Map<String, List<String>> getOptions() {
        return optionList;
    }


    public List<String> getOptionTypes() {
    return new ArrayList<>(optionList.keySet());
    }

}
