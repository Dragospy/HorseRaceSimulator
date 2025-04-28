<h1> Horse Race Simulator </h1>
<p>
- This program represents a Horse Racing Simulator <br>
- It contains 2 versions: A text version and a GUI version
</p>

<h1>How to Run</h1>

First check you have all of the dependenices:

To check Java was correctly installed, type javac -version

This should come up with something long the lines of "javac 21.0.4"

<h2>Part 1 (TEXT VERSION)</h2>

<p>
First, go inside the Main.java file, and tweak it to your liking: <br>

- To add a horse, do the following: <br>
    - Type Horse horseName = new Horse(Symbol, Name, Confidence); 
    - Fill it out resembling this: Horse horse3 = new Horse("C", "Horse C", 0.7);
    - and then currentRace.addHorse(horseName); 
    - Fill it out resembling this: currentRace.addHorse(horse3);
- To Modify the race length, modify the number inside of Race(), by default it is 15, but it can be anything (I don't recommend higher than 50)

Type the following commands in the terminal, from the main directory (not inside either of the Part folders):
<br>
-----Only needed on first run------<br>
javac Part1/Horse.java <br>
javac Part1/Race.java <br>
javac Part1/Main.java <br>
<br>
javac -d . Part1/Horse.java <br>
javac -d . Part1/Race.java <br>
javac -d . Part1/Main.java <br>
-----Only needed on first run------ <br>
<br>
Then run this to start: <br>
java Part1/Main.java <br>
</p>

<h2>Part 2 (GUI VERSION)</h2>

<p>
The guy version required no manual tweaking, it is all done using the GUI

Type the following commands in the terminal, from the main directory (not inside either of the Part folders):
<br>
-----Only needed on first run------<br>
javac Part1/Horse.java<br>
javac Part1/Race.java<br>
javac Part1/Main.java<br>
<br>
javac -d . Part1/Horse.java<br>
javac -d . Part1/Race.java<br>
javac -d . Part1/Main.java<br>
<br>
javac Part2/optionLoader.java<br>
javac Part2/databaseHandler.java<br>
javac Part2/passedFunction.java<br>
javac Part2/valueContainer.java<br>
javac Part2/helperMethods.java<br>
javac Part2/raceTrack.java<br>
javac Part2/customizationPanel.java<br>
javac Part2/statisticsPanel.java<br>
javac Part2/guiMain.java<br>

javac -d . Part2/optionLoader.java<br>
javac -d . Part2/databaseHandler.java<br>
javac -d . Part2/passedFunction.java<br>
javac -d . Part2/valueContainer.java<br>
javac -d . Part2/helperMethods.java<br>
javac -d . Part2/raceTrack.java<br>
javac -d . Part2/customizationPanel.java<br>
javac -d . Part2/statisticsPanel.java<br>
javac -d . Part2/guiMain.java<br>
-----Only needed on first run------<br>
<br>
Then run this to start:
java Part2/guiMain.java

</p>

<h1>Dependencies</h1>

- Java 21

<h1>Usage Instructions</h1>

The text version:
- To use this, once tweaked to our linking, we simply run the commands as mentioned.
- It is very basic, and nothing much happens

The GUI version:
- To use this, first run the commands as mentioned.
- The GUI has the following pages:
    - Track Customisation
        - Allows the user to customise lane count, race length, track conditions and what horse goes on what lane
    - Horse Customisation
        - Allows the user to change a horses symbol, attributes and accessories
    - Race Stats
        - Displays the different data collected from the races:
            - Race History, that allows per horse filtering
            - Horse Data, displays the overall horse stats, such as win rate, average speed, etc
            - Track Data, displays the different data about the different track conditions, such as highest speed, worst time, best time, etc...
    - Race Track
        - Lets the user start a race and watch it.
