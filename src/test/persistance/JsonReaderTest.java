package persistance;


import com.sun.corba.se.spi.orbutil.threadpool.Work;
import model.Exercise;
import model.Workout;
import model.WorkoutJournal;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests have been modelled after those in JsonReaderTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest {

    @Test
    void testReaderNonExistentWorkoutFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Workout workout = reader.readWorkout();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkout() {
        JsonReader reader = new JsonReader("./data/testEmptyWorkout.json");
        try {
            Workout workout = reader.readWorkout();
            assertEquals("legs", workout.getTitle());
            assertEquals(0, workout.getExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkout() {
        JsonReader reader = new JsonReader("./data/testGeneralWorkout.json");
        try {
            Workout workout = reader.readWorkout();
            assertEquals("legs", workout.getTitle());
            List<Exercise> exercises = workout.getExercises();
            assertEquals(2, exercises.size());
            assertEquals("lunges", exercises.get(0).getName());
            assertEquals("squats", exercises.get(1).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNonExistentWorkoutJournalFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutJournal workoutJournal = reader.readWorkoutJournal();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutJournal() {
        JsonReader reader = new JsonReader("./data/testEmptyWorkoutJournal.json");
        try {
            WorkoutJournal workoutJournal = reader.readWorkoutJournal();
            assertEquals("My Workouts", workoutJournal.getTitle());
            assertEquals(0, workoutJournal.getWorkouts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutJournal() {
        JsonReader reader = new JsonReader("./data/testGeneralWorkoutJournal.json");
        try {
            WorkoutJournal workoutJournal = reader.readWorkoutJournal();
            assertEquals("My Workouts", workoutJournal.getTitle());
            List<Workout> workouts = workoutJournal.getWorkouts();
            assertEquals(2, workouts.size());
            assertEquals("core", workouts.get(0).getTitle());
            assertEquals("legs", workouts.get(1).getTitle());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}

