package persistence;

import model.*;
import org.json.JSONObject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Code for this class was taken from JsonWriter class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// and then modified to fit this program

// Represents a writer that writes JSON representation of entire workout journal to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workout to file
    public void writeWorkout(Workout w) {
        JSONObject json = w.toJson();
        saveToFile(json.toString(TAB));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // NOTE:the following method can be reused when implementing WorkoutJournal to the GUI
    // MODIFIES: this
    // EFFECTS: writes JSON representation of workout journal to file
    public void writeWorkoutJournal(WorkoutJournal wj) {
        JSONObject json = wj.toJson();
        saveToFile(json.toString(TAB));
    }
}


