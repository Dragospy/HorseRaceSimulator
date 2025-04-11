package Part1;
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
    private final char fallSymbol = '❌';

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        horses = new ArrayList<>();
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     */
    public void addHorse(Horse theHorse)
    {
        horses.add(theHorse);
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
            System.out.println("No horses, race cannot start!");
            return;
        }

        ArrayList<Horse> winners = new ArrayList<>();
        boolean finished = false; //declare a local variable to tell us when the race is finished
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : horses) {
            horse.goBackToStart();
        }
                      
        while (!finished)
        {
            
            //move each horse
            for (Horse horse: horses){
                moveHorse(horse);
            }
                        
            //print the race positions
            printRace();

            //Figure out if a horse has won
            for (Horse horse : horses) {
                if (raceWonBy(horse)) {
                    winners.add(horse);
                }
            }
            
            //if any of the three horses has won the race is finished
            if ( !winners.isEmpty() )
            {
                //Print the winner(s)
                if (winners.size() > 1) {
                    System.out.print("It's a tie between: ");
                    int i = 0;
                    for (Horse horse : winners) {
                        System.out.print(horse.getName());
                        if (i < winners.size() - 1) {
                            System.out.print(", ");
                        }
                        i++;
                    }
                } else {
                    System.out.println("The winner is... " + (winners.get(0)).getName() + "!");
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
                System.out.println("Error: " + e);
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
        
        System.out.print("\033\143");
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for (Horse horse : horses) {
            printLane(horse);
            System.out.println();
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
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
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print(fallSymbol);

            //print the spaces after the horse
            multiplePrint(' ',spacesAfter - 1);
        }
        else
        {
            System.out.print(theHorse.getSymbol());

            //print the spaces after the horse
            multiplePrint(' ',spacesAfter);
        }
        
        //print the | for the end of the track
        System.out.print('|');

        //print the horse's name and confidence 
        System.out.print(" " + theHorse.getName() + " (Current confidence " + theHorse.getConfidence() + ")");
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
            System.out.print(aChar);
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
            System.out.println("All horses have fallen. No winners.");
            return true;
        }

        return false;
    }
}
