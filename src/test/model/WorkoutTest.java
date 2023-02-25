package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Workout class
public class WorkoutTest {
    private Workout testWorkout;
    private Exercise pushUps;
    private Exercise armCurls;

    @BeforeEach
    void setup() {
        testWorkout = new Workout("Arm Workout");
        pushUps = new Exercise("Push Ups", 3, 10);
        armCurls = new Exercise("Arm Curls", 1, 5);
    }


    @Test
    void testWorkout() {
        assertEquals("Arm Workout", testWorkout.getTitle());
        List<Exercise> exercises = testWorkout.getExercises();
        assertEquals(0, exercises.size());
    }

    @Test
    void testAddExercise() {
        testWorkout.addExercise(pushUps);
        assertEquals(pushUps, testWorkout.getExercises().get(0));
        assertEquals(1, testWorkout.getExercises().size());

        testWorkout.addExercise(armCurls);
        assertEquals(pushUps, testWorkout.getExercises().get(0));
        assertEquals(armCurls, testWorkout.getExercises().get(1));
        assertEquals(2, testWorkout.getExercises().size());

    }

    @Test
    void testRemoveExercise() {
        testWorkout.addExercise(pushUps);
        testWorkout.addExercise(armCurls);

        testWorkout.removeExercise("Push Ups");
        assertEquals(1, testWorkout.getExercises().size());

        testWorkout.removeExercise("Arm Curls");
        assertEquals(0, testWorkout.getExercises().size());


    }

    @Test
    void testRemoveExerciseButExerciseNotInWorkout() {
        testWorkout.addExercise(pushUps);
        testWorkout.addExercise(armCurls);

        testWorkout.removeExercise("Lunges");

        assertEquals(2, testWorkout.getExercises().size());


    }

    @Test
    void testRemoveExerciseOnEmptyWorkout() {
        testWorkout.removeExercise("Lunges");

        assertEquals(0, testWorkout.getExercises().size());
    }
}

/*

    // a potential user story for future versions

    @Test
    void testGetTotalTime() {
        testWorkout.addExercise(armCurls);
        assertEquals(armCurls.getTime(), testWorkout.getTotalTime());
    }

    @Test
    void testGetTotalTimeMultipleExercises() {
        testWorkout.addExercise(armCurls);
                testWorkout.addExercise(pushUps);
        assertEquals(armCurls.getTime() + pushUps.getTime(), testWorkout.getTotalTime());
    }
}

 */

/*
// REQUIRES: name has non-zero length, sets > 0 and reps > 0
// MODIFIES: this
// EFFECTS: adds an existing exercise to the workout
public void addNewExercise(String name, int sets, int reps) {
    exercises.add(new Exercise(name, sets, reps));
    }
 */