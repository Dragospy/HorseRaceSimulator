
public class Main {
    public static void main(String[] args) {
        Race currentRace = new Race(15);

        Horse horse1 = new Horse("Horse A", 'A', 0.8);
        Horse horse2 = new Horse("Horse B", 'B', 0.6);
        Horse horse3 = new Horse("Horse C", 'C', 0.7);
        currentRace.addHorse(horse1, 1);
        currentRace.addHorse(horse2, 2);
        currentRace.addHorse(horse3, 3);

        currentRace.startRace();
    }
}
