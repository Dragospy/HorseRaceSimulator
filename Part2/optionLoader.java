package Part2;

import java.io.*;
import java.util.*;

public class optionLoader {
    private final String attributesFolder;
    private final String accessoriesFolder;

    private final Map<String, List<String>> attributes = new LinkedHashMap<>();
    private final Map<String, List<String>> accessories = new LinkedHashMap<>();



    public optionLoader() {
        this.attributesFolder = "./Part2/assets/attributes";
        this.accessoriesFolder = "./Part2/assets/accessories";
        loadAttributes();
        loadAccessories();
    }

    private void loadAttributes() {
        File folder = new File(attributesFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Attributes folder not found: " + attributesFolder);
            return;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::getName)); // Sort alphabetically by filename
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file.getName().replace(".txt", ""));
                    attributes.put(file.getName().replace(".txt", ""), readOptions(file));
                }
            }
        }
    }


    private void loadAccessories() {
        File folder = new File(accessoriesFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Accessories folder not found: " + accessoriesFolder);
            return;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::getName)); // Sort alphabetically by filename
            for (File file : files) {
                if (file.isFile()) {
                    accessories.put(file.getName().replace(".txt", ""), readOptions(file));
                }
            }
        }
    }


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

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    public Map<String, List<String>> getAccessories() {
        return accessories;
    }

    public List<String> getAttributeTypes() {
    return new ArrayList<>(attributes.keySet());
    }

    public List<String> getAccessoryTypes() {
        return new ArrayList<>(accessories.keySet());
    }
}
