package ui;


import model.Exercise;
import model.WorkoutJournal;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

// Workout journal application
// Code until line 81 is taken from TellerApp.
public class JournalApp {
    private static final String JSON_STORE = "./data/workout.json";
    private WorkoutJournal workoutJournal;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs workout journal and runs application
    public JournalApp() throws FileNotFoundException {
        workoutJournal = new WorkoutJournal("My Workouts");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runJournal() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // code taken from TellerApp
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewWorkout();
        } else if (command.equals("2")) {
            newWorkout();
        } else if (command.equals("3")) {
            addNewExercise();
        } else if (command.equals("4")) {
            removeAnExercise();
        } else if (command.equals("5")) {
            saveWorkoutJournal();
        } else if (command.equals("6")) {
            loadWorkoutJournal();
        } else {
            System.out.println("Oops...try choosing something from the menu!");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> View one of your workouts");
        System.out.println("\t2 -> Add a new workout");
        System.out.println("\t3 -> Add a new exercise to one of your workouts");
        System.out.println("\t4 -> Remove an exercise from one of your workouts");
        System.out.println("\t5 -> Save your workout journal to file");
        System.out.println("\t6 -> Load your workout journal from file");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: prompts user to select an exercise in order to
    //          display the list of exercises in the selected workout to the user.
    private void viewWorkout() {
        if (workoutJournal.getWorkouts().isEmpty()) {
            System.out.println("You have no workouts! Try creating one first.");
        } else {
            System.out.println("What is the name of the workout you would like to view?");
            displayWorkoutMenu();
            Workout selected = selectWorkout(input.next().toLowerCase());
            if (notNullWorkout(selected)) {
                if (selected.getExercises().isEmpty()) {
                    System.out.println("This workout has no exercises. Try adding some and check again.");
                } else {
                    for (Exercise e : selected.getExercises()) {
                        String numSets = String.valueOf(e.getSets());
                        String numReps = String.valueOf(e.getReps());
                        System.out.println("- " + e.getName() + " -> " + numSets + " sets of " + numReps
                                + " reps each");
                    }
                }
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts user to give a name to a new workout
    private void newWorkout() {
        System.out.println("What would you like to name your new workout?");
        workoutJournal.addNewWorkout(input.next().toLowerCase());
        System.out.println("Your new workout has been created!");
    }

    // REQUIRES: user input to "How many sets would you like to do?" must be an integer
    //           and user input to "How many reps would you like to do?" must be an integer
    // MODIFIES: this
    // EFFECTS: prompts user to select a workout to add an exercise to and then
    //          create a new exercise to add to the selected workout
    private void addNewExercise() {
        if (workoutJournal.getWorkouts().isEmpty()) {
            System.out.println("You have no workouts! Try creating new ones first.");
        } else {
            System.out.println("Which workout do you want to add a new exercise to?");
            displayWorkoutMenu();
            Workout selected = selectWorkout(input.next().toLowerCase());
            if (notNullWorkout(selected)) {
                System.out.println("What is the name of the new exercise?");
                String name = input.next().toLowerCase();

                System.out.println("What number of sets would you like to do?");
                int sets = parseInt(input.next());

                System.out.println("What number of reps would you like to do?");
                int reps = parseInt(input.next());

                Exercise newExercise = new Exercise(name, sets, reps);
                selected.addExercise(newExercise);
                System.out.println(name + " was added to " + selected.getTitle() + "!");
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts user to choose a workout to remove an exercise from and then
    //          also select which exercise to remove
    private void removeAnExercise() {
        if (!workoutJournal.getWorkouts().isEmpty()) {
            System.out.println("Which workout do you want to remove an exercise from?");
            displayWorkoutMenu();
            Workout selectedWorkout = selectWorkout(input.next().toLowerCase());
            if (notNullWorkout(selectedWorkout)) {
                if (selectedWorkout.getExercises().isEmpty()) {
                    System.out.println("This workout has no exercises. Try adding some first.");
                } else {
                    System.out.println("Which exercise do you want to remove?");
                    displayExercises(selectedWorkout);
                    String selectedExName = input.next().toLowerCase();
                    if (selectExercise(selectedWorkout, selectedExName) == null) {
                        System.out.println("Oops...try choosing from the menu.");
                    } else {
                        selectedWorkout.removeExercise(selectedExName);
                        System.out.println(selectedExName + " was removed from " + selectedWorkout.getTitle() + "!");
                    }
                }
            }
        } else {
            System.out.println("You have no workouts! Try creating one first.");
        }
    }


    // EFFECTS: returns the workout with the given title, if there is one.
    private Workout selectWorkout(String title) {
        Workout selected = null;
        for (Workout w : workoutJournal.getWorkouts()) {
            if (w.getTitle().equals(title)) {
                selected = w;
            }
        }
        return selected;
    }

    // EFFECTS: returns the exercise with the given name, if there is one in the given workout.
    private Exercise selectExercise(Workout w, String exerciseName) {
        Exercise selected = null;
        for (Exercise e : w.getExercises()) {
            if (e.getName().equals(exerciseName)) {
                selected = e;
            }
        }
        return selected;
    }


    // EFFECTS: displays a list of the titles of all workouts
    private void displayWorkoutMenu() {
        for (Workout w : workoutJournal.getWorkouts()) {
            System.out.println("- " + w.getTitle());
        }
    }

    // EFFECTS: displays a list of exercises in the given workout
    private void displayExercises(Workout w) {
        for (Exercise e : w.getExercises()) {
            System.out.println("- " + e.getName());
        }
    }

    // EFFECTS: returns true if the given workout is null, as well as a statement to tell the user they have entered
    //          a non-existent workout
    private boolean notNullWorkout(Workout w) {
        if (!(w == null)) {
            return true;
        }
        System.out.println("Oops...try choosing from the menu.");
        return false;
    }

    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the workout journal to file
    private void saveWorkoutJournal() {
        try {
            jsonWriter.open();
            jsonWriter.writeWorkoutJournal(workoutJournal);
            jsonWriter.close();
            System.out.println("Saved " + workoutJournal.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads workout journal from file
    private void loadWorkoutJournal() {
        try {
            workoutJournal = jsonReader.readWorkoutJournal();
            System.out.println("Loaded " + workoutJournal.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}


/*
// a potential user story for future versions
// EFFECTS: returns how long this workout takes
    private void checkTime() {
        if (workoutsList.getWorkouts().isEmpty()) {
            System.out.println("You have no workouts! Try creating new ones first.");
        } else {
            System.out.println("Which workout do you want to check the time for?");
            displayWorkoutMenu();
            Workout selectedWorkout = selectWorkout(input.next().toLowerCase());
            if (selectedWorkout.getExercises().isEmpty()) {
                System.out.println("This workout has no exercises. Try adding some and check again.");
            } else {
                String time = String.valueOf(selectedWorkout.getTotalTime());
                System.out.println("This workout takes" + " " + time + " " + "seconds.");
            }
        }
    }

*/
