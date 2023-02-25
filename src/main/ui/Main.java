package ui;

import model.Event;
import model.EventLog;

// Class to construct a WorkoutGUI object and run the program
public class Main {


    public static void main(String[] args) {

        new WorkoutGUI();

        // this code builds upon the following thread
        // https://stackoverflow.com/questions/63687/calling-function-when-program-exits-in-java
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getDescription());
                }
            }
        }));
    }
}
