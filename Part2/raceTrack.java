package Part2;

import Part1.*;
import java.awt.Color;
import javax.swing.*;

public class raceTrack extends JPanel{
    private int width;
    private int laneCount;
    private JPanel finishLine;
    private Horse[] selectedHorses;
    private JLabel[] horseCharacters = new JLabel[15];
    private JLabel[] horseLabels = new JLabel[15];
    private double conditionMultiplier = 1;

    public raceTrack(int width, int laneCount, int yPos, int raceLength, Horse[] selectedHorses) {
        this.width = width;
        this.laneCount = laneCount;
        this.selectedHorses = selectedHorses;

        setSize(width, 40 * laneCount);
        setLocation(0, yPos);
        setLayout(null);

        finishLine = new JPanel();

        finishLine.setSize(5, this.getHeight());
        finishLine.setBackground(Color.red);
        finishLine.setLocation(20 * raceLength, 0);
        this.add(finishLine);

        for (int i = 0; i < laneCount; i++){
            drawLane(i, i * 40);
        }        
    }

    public void changeLaneCount(int laneCount){
        setSize(width, 40 * laneCount);
        finishLine.setSize(5, 40 * laneCount);

        int count = this.getComponentCount();
        if (count > laneCount + 1) {
            this.remove(count - 1); // remove the last component
            this.revalidate();      // re-layout the panel
            this.repaint();         // repaint the UI
        }
        else if (count < laneCount + 1) {
            drawLane(laneCount - 1, 40* (laneCount - 1));
        }
    }

    public void changeTrackLength(int raceLength){
        finishLine.setLocation(20 * raceLength, 0);
    }

    //Horse index is not needed if we dont change horse
    public void updateHorse(int laneIndex){
        JLabel horseChar = this.horseCharacters[laneIndex];
        if (horseChar != null){
            Horse currentHorse = this.selectedHorses[laneIndex];

            horseChar.setText(String.valueOf(currentHorse.getSymbol()));
            horseLabels[laneIndex].setText(currentHorse.getName() + " (Current confidence " + currentHorse.getConfidence() + ")" );
            horseChar.setLocation(5 + 20 * currentHorse.getDistanceTravelled(), horseChar.getY());
            horseChar.putClientProperty("index", laneIndex);
            horseChar.putClientProperty("name", currentHorse.getName());
            horseChar.repaint();     // refresh the label
            this.repaint();          // refresh the panel
        }
    }

    public void updateHorseCustomization(Horse currentHorse, String horseName){
        for (int i = 0; i < laneCount; i++){
            String currentHorseName = (String)(horseCharacters[i].getClientProperty("name"));
            if (currentHorseName.equals(horseName)){
                horseCharacters[i].setText(currentHorse.getSymbol());
                this.repaint();   
            }
        }

    }

    public void setCondition(Double mutiplier){
        this.conditionMultiplier = mutiplier;
    }

    public Double getCondition(){
        return this.conditionMultiplier;
    }

    public void raceFinished(String type, String message){
        if (type.equals("noWinner")){
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (type.equals("winner")) {
            JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.OK_CANCEL_OPTION);
        }
    }

    private void drawLane(int laneIndex, int yPos){
        Horse currentHorse = selectedHorses[laneIndex];
        JPanel laneContainer = new JPanel();
        JPanel lane = new JPanel();
        JLabel currentHorseCharacter = new JLabel(String.valueOf(currentHorse.getSymbol()));
        JLabel laneLabel = new JLabel(currentHorse.getName() + " (Current confidence " + currentHorse.getConfidence() + ")" );

        laneContainer.setLayout(null);
        laneContainer.setSize(width, 40);
        laneContainer.setLocation(0, yPos);

        lane.setBackground(Color.gray);
        lane.setSize(width - 350, 40);
        lane.setLayout(null);
        lane.setLocation(0, 0);

        currentHorseCharacter.setOpaque(false); // allow background color to show
        currentHorseCharacter.setBounds(5, 0, 50, 50); // x, y, width, height
        currentHorseCharacter.putClientProperty("index", laneIndex);
        currentHorseCharacter.putClientProperty("name", currentHorse.getName());
        this.horseCharacters[laneIndex] = currentHorseCharacter;

        laneLabel.setBounds(width - 325, 0, 300, 50);
        horseLabels[laneIndex] = laneLabel;

        lane.add(currentHorseCharacter);
        laneContainer.add(lane);
        laneContainer.add(laneLabel);
    
        this.add(laneContainer);

    }
}