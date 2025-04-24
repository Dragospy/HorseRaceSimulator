package Part2;

import java.awt.Color;
import javax.swing.*;

public class raceTrack extends JPanel{
    public int width;
    public int laneCount;
    public JPanel finishLine;

    public raceTrack(int width, int laneCount, int yPos, int raceLength) {
        this.width = width;
        this.laneCount = laneCount;

        setBackground(Color.gray);
        setSize(width, 40 * laneCount);
        setLocation(0, yPos);
        setLayout(null);

        finishLine = new JPanel();

        finishLine.setSize(20, this.getHeight());
        finishLine.setBackground(Color.BLACK);
        finishLine.setLocation(20 * raceLength, 0);
        this.add(finishLine);
    }

    public void changeLaneCount(int laneCount){
        setSize(width, 40 * laneCount);
        finishLine.setSize(20, this.getHeight());
    }

    public void changeTrackLength(int raceLength){
        finishLine.setLocation(20 * raceLength, 0);
    }
       
}