package Part1;
import Part2.raceTrack;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private final int raceLength;
    private final ArrayList<Horse> horses;  
    private final boolean displayText;  
    private final char fallSymbol = '❌';
    private boolean finished = false;
    private raceTrack raceTrackGUI;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, boolean textVersion)
    {
        // initialise instance variables
        raceLength = distance;
        horses = new ArrayList<>();
        displayText = textVersion; //set to false to stop printing
    }
    
    /**
     * Adds a new lane and the horse inside of it (for TEXT version)
     * 
     * @param theHorse the horse to be added to the race
     */
    public void addHorse(Horse theHorse)
    {
        horses.add(theHorse);
    }

    /**
     * Adds an empty lane (for GUI version)
     * 
     */
    public void addLane()
    {
        horses.add(null);
    }

    /**
     * Adds a horse to the race in a given lane (for GUI version)
     * 
     * @param theHorse the horse to be added to the race
     * @param laneId the lane that the horse is being placed into
     */
    public void setHorseLane(Horse theHorse, int laneId)
    {
        horses.set(laneId, theHorse);
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {

        if (horses.isEmpty()){
            println("No horses, race cannot start!");
            return;
        }

        ArrayList<Horse> winners = new ArrayList<>();
        finished = false; //declare a local variable to tell us when the race is finished
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (int i = 0; i <  horses.size(); i++){
            if (horses.get(i) != null){
                (horses.get(i)).goBackToStart();
                if (raceTrackGUI != null){
                    raceTrackGUI.updateHorse(i);
                }
            }
        }
                      
        while (!finished)
        {
            
            //move each horse
            for (int i = 0; i <  horses.size(); i++){
                if (horses.get(i) != null){
                    moveHorse(horses.get(i));
                    if (raceTrackGUI != null){
                        raceTrackGUI.updateHorse(i);
                    }
                }
            }
                        
            //print the race positions
            printRace();

            //Figure out if a horse has won
            for (Horse horse : horses) {
                if (horse != null && raceWonBy(horse)) {
                    winners.add(horse);
                }
            }
            
            //if any of the three horses has won the race is finished
            if ( !winners.isEmpty() )
            {   
                String message;
                //Print the winner(s)
                if (winners.size() > 1) {
                    message = "It's a tie between: ";
                    print("It's a tie between: ");
                    int i = 0;
                    for (Horse horse : winners) {
                        print(horse.getName());
                        message = horse.getName();
                        if (i < winners.size() - 1) {
                            print(", ");
                            message += ", ";
                        }
                        i++;
                    }
                    if (raceTrackGUI != null){
                        raceTrackGUI.raceFinished("noWinner", message);
                    }
                    
                } else {
                    message = "The winner is... " + (winners.get(0)).getName() + "!";
                    println(message);
                    if (raceTrackGUI != null){
                        raceTrackGUI.raceFinished("winner", message);
                    }
                }

                finished = true;
            }

            if (haveAllFallen()) {
                finished = true;
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(InterruptedException e){
                println("Error: " + e);
            }
        }

    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        return (theHorse.getDistanceTravelled() == raceLength && !theHorse.hasFallen());
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        
        print("\033\143");
        
        multiplePrint('=',raceLength+3); //top edge of track
        println("");

        for (Horse horse : horses) {
            printLane(horse);
            println("");
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        println("");    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            print(fallSymbol);

            //print the spaces after the horse
            multiplePrint(' ',spacesAfter - 1);
        }
        else
        {
            print(theHorse.getSymbol());

            //print the spaces after the horse
            multiplePrint(' ',spacesAfter);
        }
        
        //print the | for the end of the track
        print('|');

        //print the horse's name and confidence 
        print(" " + theHorse.getName() + " (Current confidence " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            print(aChar);
            i = i + 1;
        }
    }

    /***
     * Checks if all horses have fallen
     * and returns true if they have
     */
    private boolean haveAllFallen() {
        int fallenCount = 0;
        //check if all horses have fallen
        for (Horse horse : horses) {
            if (horse.hasFallen()) {
                fallenCount++;
            }
        }

        if (fallenCount == horses.size()) {
            println("All horses have fallen. No winners.");
            return true;
        }

        return false;
    }

    public void setRaceTrackGui(raceTrack raceTrackObject){
        raceTrackGUI = raceTrackObject;
    }

    public boolean isFinished(){
        return this.finished;
    }

     /**
     * Print a string to the console
     * This method only prints something if the displayText variable is true
     * 
     * @param text the text to print
     */

    private void print(String text)
    {
        if (displayText) {
            System.out.print(text);
        }
    }

    /**
     * Print a character to the console
     * This method only prints something if the displayText variable is true
     * 
     * @param text the text to print
     */
    private void print(char text)
    {
        if (displayText) {
            System.out.print(text);
        }
    }

    /**
     * Print a string to the console and add a new line
     * This method only prints something if the displayText variable is true
     * 
     * @param text the text to print
     */
    private void println(String text)
    {
        if (displayText) {
            System.out.println(text);
        }
    }
}
