public class Test {
    
    public static void main(String[] args) {
        // Create Horse objects
        Horse horse1 = new Horse("Horse A", 'A', 0.8);
        Horse horse2 = new Horse("Horse B", 'B', 0.9);

        // Test getName()
        System.out.println("Testing getName():");
        System.out.println(horse1.getName().equals("Horse A") ? "Pass" : "Fail");
        System.out.println(horse2.getName().equals("Horse B") ? "Pass" : "Fail");

        // Test getSymbol()
        System.out.println("Testing getSymbol():");
        System.out.println(horse1.getSymbol() == 'A' ? "Pass" : "Fail");
        System.out.println(horse2.getSymbol() == 'B' ? "Pass" : "Fail");
    }
}
