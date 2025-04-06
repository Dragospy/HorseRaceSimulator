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
    public Horse(String horseName, char horseSymbol, double horseConfidence)
    {
       this.name = horseName;  //Horse's name
       this.symbol = horseSymbol;  //Horse's symbol
       this.confidence = horseConfidence;  //Horse's confidence
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
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
