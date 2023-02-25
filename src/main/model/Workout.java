package model;

import exception.ExerciseAlreadyAddedException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;


// Represents a workout, having a title, a list of exercises and a total time it takes.
public class Workout implements Writeable {
    private String title;
    private List<Exercise> exercises;

    // REQUIRES: title has non-zero length
    // EFFECTS: constructs a new workout of the given title and empty list of exercises.
    public Workout(String title) {

        this.title = title;
        this.exercises = new ArrayList<Exercise>();


    }

    // REQUIRES: name must be the name of an existing exercise
    // MODIFIES: this
    // EFFECTS: adds the existing exercise of the given name to the workout
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        EventLog.getInstance().logEvent(new Event("Added " + exercise.getName() + " to " + this.getTitle()));
    }

    // REQUIRES: name must be the name of an existing exercise
    // MODIFIES: this
    // EFFECTS: removes the exercise with the given name from the workout
    public void removeExercise(String name) {
        for (int i = 0; exercises.size() - 1 >= i; i++) {
            if (exercises.get(i).getName().equals(name)) {
                EventLog.getInstance().logEvent(new Event("Removed " + exercises.get(i).getName()
                        + " from " + this.getTitle()));
                exercises.remove(i);
            }
        }
    }


    // EFFECTS: returns title of workout
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns a list of exercises in a workout
    public List<Exercise> getExercises() {
        return exercises;
    }

    // Method taken from WorkRoom Class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    // EFFECTS: returns this workout as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("exercises", exercisesToJson());
        return json;
    }

    // Method taken from WorkRoom Class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: returns exercises in this workout as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e: exercises) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
