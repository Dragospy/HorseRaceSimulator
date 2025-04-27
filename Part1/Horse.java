package Part1;

import java.util.HashMap;
import java.util.Map;

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
    private String symbol;
    private double confidence;
    private boolean  fallen = false;
    private int distanceTravelled = 0;
    private Map<String, String> horseAttributes = new HashMap<>();
    private Map<String, String> horseAccessories = new HashMap<>();
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(String horseSymbol, String horseName, double horseConfidence)
    {
       this.name = horseName;  //Horse's name
       this.symbol = horseSymbol;  //Horse's symbol

        if (horseConfidence <= 0){
            this.confidence = 0.1;  //Horse's confidence cannot be negative
        }
        else if (horseConfidence > 1){
            this.confidence = 1;  //Horse's confidence cannot be greater than 1
        }
        else{
            this.confidence = horseConfidence;  //new confidence is between 0 and 1 so we set it as the actual confidence
        }
    }

    public Horse(String horseSymbol, String horseName, double horseConfidence, Map<String, String> attributes, Map<String, String> accessories){
        this(horseSymbol, horseName, horseConfidence);

        this.horseAttributes = attributes;
        this.horseAccessories = accessories;
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
    
    public String getSymbol()
    {
        return this.symbol;              
    }

    public Map<String, String> getAttributeMap(){
        return this.horseAttributes;
    }

    public Map<String, String> getAccessoriesMap(){
        return this.horseAccessories;
    }

    public String getAttribute(String attribute){
        return this.horseAttributes.get(attribute);
    }

    public String getAccessory(String accessory){
        return this.horseAccessories.get(accessory);
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
        if (newConfidence <= 0){
            this.confidence = 0.1;  //Horse's confidence cannot be negative
        }
        else if (newConfidence > 1){
            this.confidence = 1;  //Horse's confidence cannot be greater than 1
        }
        else{
            confidence = newConfidence;  //new confidence is between 0 and 1 so we set it as the actual confidence
        }
    }
    
    public void setSymbol(String newSymbol)
    {
        this.symbol = newSymbol;
    }

    public void setAttribute(String attribute, String value){
        this.horseAttributes.replace(attribute, value);
    }

    public void setAccessory(String accessory, String value){
        this.horseAccessories.replace(accessory, value);
    }

    
}
