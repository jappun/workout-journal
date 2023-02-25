package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for Exercise class
class ExerciseTest {
    private Exercise testExercise;

    @BeforeEach
    void setup() {
        testExercise = new Exercise("Push Up", 3, 4);
    }

    @Test
    void testExercise() {
        assertEquals(3, testExercise.getSets());
        assertEquals(4, testExercise.getReps());
        assertEquals("Push Up", testExercise.getName());

        testExercise.setSets(1);
        assertEquals(1, testExercise.getSets());

        testExercise.setReps(1);
        assertEquals(1, testExercise.getReps());
    }
}


/*

// a potential user story for future versions

    @Test
    void testGetTime() {

        int currentTime = 30 * testExercise.getSets() + (testExercise.getSets() * testExercise.getRest());

        testExercise.setReps(5);
        assertEquals(currentTime, testExercise.getTime());

        testExercise.setSets(1);
        currentTime = 30 * testExercise.getSets() + (testExercise.getSets() * testExercise.getRest());
        assertEquals(currentTime, testExercise.getTime());

        testExercise.setReps(6);
        testExercise.setSets(3);
        currentTime = 60 * testExercise.getSets() + (testExercise.getSets() * testExercise.getRest());
        assertEquals(currentTime, testExercise.getTime());


        testExercise.setReps(19);
        assertEquals(currentTime, testExercise.getTime());

        testExercise.setReps(20);
        currentTime = 120 * testExercise.getSets() + (testExercise.getSets() * testExercise.getRest());
        assertEquals(currentTime, testExercise.getTime());

    }

 */
