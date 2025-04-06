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
        fallen = true;  //Horse has fallen                      
    }
    
    public double getConfidence()
    {
        return confidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol()
    {
        return symbol;              
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;  //Horse goes back to start
        fallen = false;  //Horse is not fallen anymore
    }
    
    public boolean hasFallen()
    {
        return fallen;  //Horse has fallen
    }

    public void moveForward()
    {
        //Move the horse fowrard by one unit 
        if (!fallen){
            distanceTravelled++;
        }
    }

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }
    
}
