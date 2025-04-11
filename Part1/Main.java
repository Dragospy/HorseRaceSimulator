package Part1;

public class Main {
    public static void main(String[] args) {
        Race currentRace = new Race(15, true);

        Horse horse1 = new Horse('A', "Horse A",0.8);
        Horse horse2 = new Horse('B', "Horse B", 0.6);
        Horse horse3 = new Horse('C', "Horse C", 0.7);
        currentRace.addHorse(horse1);
        currentRace.addHorse(horse2);
        currentRace.addHorse(horse3);
       


        currentRace.startRace();
    }
}
