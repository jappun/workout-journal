package persistance;


import com.sun.corba.se.spi.orbutil.threadpool.Work;
import model.Exercise;
import model.Workout;
import model.WorkoutJournal;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests have been modelled after those in JsonWriterTest class in
// // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest {

    @Test
    void testWriterInvalidWorkoutFile() {
        try {
            Workout workout = new Workout("legs");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkout() {
        try {
            Workout workout = new Workout("legs");
            JsonWriter writer = new JsonWriter("./data/testEmptyWorkout.json");
            writer.open();
            writer.writeWorkout(workout);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyWorkout.json");
            workout = reader.readWorkout();
            assertEquals("legs", workout.getTitle());
            assertEquals(0, workout.getExercises().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkout() {
        try {
            Workout workout = new Workout("legs");
            Exercise lunges = new Exercise("lunges", 3, 10);
            Exercise squats = new Exercise("squats", 3, 15);
                    workout.addExercise(lunges);
            workout.addExercise(squats);

            JsonWriter writer = new JsonWriter("./data/testGeneralWorkout.json");
            writer.open();
            writer.writeWorkout(workout);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralWorkout.json");
            workout = reader.readWorkout();
            assertEquals("legs", workout.getTitle());
            List<Exercise> exercises = workout.getExercises();
            assertEquals(2, exercises.size());
            assertEquals(lunges.getName(), exercises.get(0).getName());
            assertEquals(lunges.getSets(), exercises.get(0).getSets());
            assertEquals(lunges.getReps(), exercises.get(0).getReps());
            assertEquals(squats.getName(), exercises.get(1).getName());
            assertEquals(squats.getSets(), exercises.get(1).getSets());
            assertEquals(squats.getReps(), exercises.get(1).getReps());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



    @Test
    void testWriterInvalidWorkoutJournalFile() {
        try {
            WorkoutJournal workoutJournal = new WorkoutJournal("My Workout");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkoutJournal() {
        try {
            WorkoutJournal workoutJournal = new WorkoutJournal("My Workouts");
            JsonWriter writer = new JsonWriter("./data/testEmptyWorkoutJournal.json");
            writer.open();
            writer.writeWorkoutJournal(workoutJournal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyWorkoutJournal.json");
            workoutJournal = reader.readWorkoutJournal();
            assertEquals("My Workouts", workoutJournal.getTitle());
            assertEquals(0, workoutJournal.getWorkouts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutJournal() {
        try {
            WorkoutJournal workoutJournal = new WorkoutJournal("My Workouts");
            Workout core = new Workout("core");
            Workout legs = new Workout("legs");
            workoutJournal.addWorkout(core);
            workoutJournal.addWorkout(legs);


            JsonWriter writer = new JsonWriter("./data/testGeneralWorkoutJournal.json");
            writer.open();
            writer.writeWorkoutJournal(workoutJournal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralWorkoutJournal.json");
            workoutJournal = reader.readWorkoutJournal();
            assertEquals("My Workouts",workoutJournal.getTitle());
            List<Workout> workouts = workoutJournal.getWorkouts();
            assertEquals(2, workouts.size());
            assertEquals("core", workouts.get(0).getTitle());
            assertEquals("legs", workouts.get(1).getTitle());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

