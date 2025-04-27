package Part2;

import Part1.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class customizationPanel extends JPanel{
    private Horse currentHorse = null;
    private Map<String, List<String>> attributes;
    private Map<String, List<String>> accessories;
    private Race currentRace;

    public customizationPanel(Horse selectedHorse, raceTrack currentTrack, int horseIndex, Race currentRace) {
        this.accessories = null;
        this.currentHorse = selectedHorse;
        this.currentRace = currentRace;
        optionLoader attributesLoader = new optionLoader("attributes"); 
        optionLoader accessoriesLoader= new optionLoader("accessories"); 
        JPanel attributesPanel = new JPanel();
        JPanel accessoryPanel = new JPanel();

        JPanel symbolPanel = new JPanel();
        JLabel textFieldLabel = new JLabel("Horse Symbol");
        JTextField textField = new JTextField(2);

        symbolPanel.add(textFieldLabel);
        symbolPanel.add(textField);

        textField.setText(selectedHorse.getSymbol());

        textField.addActionListener((ActionEvent e) -> {
            String symbol = textField.getText();

            if (symbol.codePointCount(0, symbol.length()) == 1){
                selectedHorse.setSymbol(symbol);
                currentTrack.updateHorseCustomization(selectedHorse, selectedHorse.getName());
                helperMethods.saveHorse(currentHorse);
            }
            else{
                JOptionPane.showMessageDialog(null, "Invalid Symbol", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadAttributes(attributesLoader);
        loadAcessories(accessoriesLoader);   

        loadSelectors(attributesPanel, attributes, currentTrack, horseIndex, "attribute");
        loadSelectors(accessoryPanel, accessories, currentTrack, horseIndex, "accessory");
        
        this.add(symbolPanel);
        this.add(attributesPanel);
        this.add(accessoryPanel);

    }

    private void loadSelectors(JPanel panel, Map<String, List<String>> loopAbleList, raceTrack currentTrack, int horseIndex, String type) {
        for (String itemName: loopAbleList.keySet()){
            JLabel title = new JLabel(helperMethods.formatWord(itemName));
            JPanel selectorContainer = new JPanel();
            JComboBox<String> selector = new JComboBox<>(loopAbleList.get(itemName).toArray(new String[0]));
        
            selector.addItemListener((ItemEvent item) -> {
                if (type.equals("accessory")){
                    currentHorse.setAccessory(itemName, (String) item.getItem());
                }
                else if (type.equals("attribute")){
                    currentHorse.setAttribute(itemName, (String) item.getItem());
                }

                currentTrack.updateHorseCustomization(currentHorse, currentHorse.getName());
                helperMethods.saveHorse(currentHorse);
            });

            if (type.equals("accessory")){
                selector.setSelectedItem(currentHorse.getAccessory(itemName));
            }
            else if (type.equals("attribute")){
                selector.setSelectedItem(currentHorse.getAttribute(itemName));
            }
            

            selectorContainer.add(title);
            selectorContainer.add(selector);
            panel.add(selectorContainer);

            selectorContainer.setLayout(new BoxLayout(selectorContainer, BoxLayout.Y_AXIS));
            title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            selector.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        }
    }



    public void saveHorseData (Horse selectedHorse){

    }

    public void loadAttributes(optionLoader loader){
        attributes = loader.getOptions();
    }

    public void loadAcessories(optionLoader loader){
        accessories = loader.getOptions();
    }



}