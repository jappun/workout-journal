package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;



// NOTE: this class is currently not implemented in the GUI, but this code can be reused in future versions.

// Represents a journal of workouts, having a title and list of workouts.
public class WorkoutJournal implements Writeable {
    private String title;
    private static List<Workout> workouts;

    // REQUIRES: title has a non-zero length
    // EFFECTS: constructs a list of workouts, with a title and empty list of workouts
    public WorkoutJournal(String title) {
        this.title = title;
        this.workouts = new ArrayList<Workout>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new workout with given title to the workout journal
    public void addNewWorkout(String title) {
        workouts.add(new Workout(title));
    }

    // MODIFIES: this
    // EFFECTS: add the given workout to this workout journal
    public void addWorkout(Workout w) {
        workouts.add(w);
    }

    // EFFECTS: returns title of the workout journal
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns the workouts in the workout journal
    public List<Workout> getWorkouts() {
        return workouts;
    }

    // Method taken from WorkRoom Class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    // EFFECTS: returns this workout journal as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("workouts", workoutsToJson());
        return json;
    }

    // Method taken from WorkRoom Class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns workouts in this workout journal as a JSON array
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout w : workouts) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

}




