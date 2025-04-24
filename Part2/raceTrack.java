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
    private JPanel[] lanes = new JPanel[15];

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

    public void updateHorse(int horseIndex){
        JLabel horseChar = this.horseCharacters[horseIndex];
        if (horseChar != null){
            horseChar.setText(String.valueOf(this.selectedHorses[horseIndex].getSymbol()));

            horseChar.setLocation(5 + 20 * this.selectedHorses[horseIndex].getDistanceTravelled(), horseChar.getY());
            horseChar.repaint();     // refresh the label
            this.repaint();          // refresh the panel
        }
    }

    private void drawLane(int laneIndex, int yPos){
        JPanel lane = new JPanel();
        lane.setBackground(Color.gray);
        lane.setSize(width, 40);
        lane.setLayout(null);
        lane.setLocation(0, yPos);

        lanes[laneIndex] = lane;

        JLabel currentHorseCharacter = new JLabel(String.valueOf(selectedHorses[laneIndex].getSymbol())); // <- use symbol, not name
        currentHorseCharacter.setOpaque(false); // allow background color to show
        currentHorseCharacter.setBounds(5, 0, 50, 50); // x, y, width, height

        this.horseCharacters[laneIndex] = currentHorseCharacter;
        lane.add(currentHorseCharacter);

        this.add(lane);
    }
}