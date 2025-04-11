package Part1;
/**
 * This class is the blueprint for an object that represents a Horse.
 * 
 * @author Dragos Soalca 
 * @version 0.1
 */
public class Horse
{
    //Fields of class Horse
    private final String name;
    private char symbol;
    private double confidence;
    private boolean  fallen = false;
    private int distanceTravelled = 0;
    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.name = horseName;  //Horse's name
       this.symbol = horseSymbol;  //Horse's symbol

        if (horseConfidence < 0){
            this.confidence = 0;  //Horse's confidence cannot be negative
        }
        else if (horseConfidence > 1){
            this.confidence = 1;  //Horse's confidence cannot be greater than 1
        }
        else{
            this.confidence = horseConfidence;  //new confidence is between 0 and 1 so we set it as the actual confidence
        }
    }
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.fallen = true;  //Horse has fallen                      
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public char getSymbol()
    {
        return this.symbol;              
    }
    
    public void goBackToStart()
    {
        this.distanceTravelled = 0;  //Horse goes back to start
        this.fallen = false;  //Horse is not fallen anymore
    }
    
    public boolean hasFallen()
    {
        return this.fallen;  //Horse has fallen
    }

    public void moveForward()
    {
        //Move the horse fowrard by one unit 
        if (!fallen){
            this.distanceTravelled++;
        }
    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence < 0){
            this.confidence = 0;  //Horse's confidence cannot be negative
        }
        else if (newConfidence > 1){
            this.confidence = 1;  //Horse's confidence cannot be greater than 1
        }
        else{
            confidence = newConfidence;  //new confidence is between 0 and 1 so we set it as the actual confidence
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
