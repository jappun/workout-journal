package model;


// NOTE: the WorkoutJournal is currently not implemented in the GUI, but this code can be reused in future versions.


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Test class for WorkoutJournal class
public class WorkoutJournalTest {
    private WorkoutJournal testWorkoutJournal;


    @BeforeEach
    void setup() {
        testWorkoutJournal = new WorkoutJournal("Jappun's Workouts");
    }

    @Test
    void testMyWorkouts() {
        assertEquals("Jappun's Workouts", testWorkoutJournal.getTitle());
        List<Workout> workouts = testWorkoutJournal.getWorkouts();
        assertEquals(0, workouts.size());
    }

    @Test
    void testAddNewWorkouts() {
        List<Workout> workouts = testWorkoutJournal.getWorkouts() ;
        testWorkoutJournal.addNewWorkout("arms");
        assertEquals(1, workouts.size());
        assertEquals("arms", workouts.get(0).getTitle());

        testWorkoutJournal.addNewWorkout("legs");
        assertEquals(2, workouts.size());
        assertEquals("arms", workouts.get(0).getTitle());
        assertEquals("legs", workouts.get(1).getTitle());

    }
}




