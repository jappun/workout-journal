package persistence;

import model.Exercise;
import model.Workout;
import model.WorkoutJournal;
import org.json.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Code for this class was taken from JsonReader class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// and then modified to fit this program

// Represents a reader that reads workout journal from JSON data stored in file
public class JsonReader {
    private String source;
    private Workout workout;
    private WorkoutJournal wj;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads workout journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Workout readWorkout() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkout(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses workout from JSON object and returns it
    private Workout parseWorkout(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        workout = new Workout(title);
        addExercises(workout, jsonObject);
        return workout;
    }

    // MODIFIES: w
    // EFFECTS: parses exercises from JSON object and adds them to workout
    private void addExercises(Workout w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addExercise(w, nextThingy);
        }
    }

    // MODIFIES: w
    // EFFECTS: parses exercise from JSON object and adds it to workout
    private void addExercise(Workout w, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        Exercise e = new Exercise(name, sets, reps);
        w.addExercise(e);
    }


// NOTE: the following method can be reused when implementing WorkoutJournal to the GUI

    // EFFECTS: parses workout journal from JSON object and returns it
    private WorkoutJournal parseWorkoutJournal(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        wj = new WorkoutJournal(title);
        addWorkouts(wj, jsonObject);
        return wj;
    }


    // MODIFIES: wj
    // EFFECTS: parses workouts from JSON object and adds them to workout journal
    private void addWorkouts(WorkoutJournal wj, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            Workout existingWorkout = parseWorkout(nextWorkout);
            wj.addWorkout(existingWorkout);
        }
    }


    // EFFECTS: reads workout journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutJournal readWorkoutJournal() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutJournal(jsonObject);
    }

}


