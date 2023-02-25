package model;


import org.json.JSONObject;
import persistence.Writeable;

// Represents an exercise having a name, a number of sets and a number of repetitions.
public class Exercise implements Writeable {
    private String name;
    private int sets;
    private int reps;


    // REQUIRES: name has non-zero length, sets > 0 and reps > 0
    // EFFECTS: constructs a new exercise with given name, sets and reps.
    public Exercise(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }


    // REQUIRES: newSets > 0
    // MODIFIES: this
    // EFFECTS: sets number of sets to newSets
    public void setSets(int newSets) {

        sets = newSets;

    }

    // REQUIRES: newReps > 0
    // MODIFIES: this
    // EFFECTS: sets number of repetitions to newReps
    public void setReps(int newReps) {

        reps = newReps;

    }


    // EFFECTS: returns number of sets for an exercise
    public int getSets() {

        return sets;

    }

    // EFFECTS: returns number of repetitions for an exercise
    public int getReps() {

        return reps;
    }

    // EFFECTS: returns name of an exercise
    public String getName() {

        return name;
    }


    // Method taken from Thingy class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sets", sets);
        json.put("reps", reps);
        return json;
    }

}