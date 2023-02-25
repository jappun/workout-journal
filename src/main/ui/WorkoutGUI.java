package ui;

import model.Exercise;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Application GUI main window
public class WorkoutGUI extends JFrame implements ActionListener {
    private static int HEIGHT = 1000;
    private static int WIDTH = 1000;
    private PopupGUI popupGUI;
    private Color borderColour;
    private WorkoutGUI mainFrame;
    private JPanel menu;
    private JPanel borderR;
    private JPanel borderL;
    private JPanel borderT;
    private JPanel borderB;
    private JButton viewWorkoutButton;
    private JButton addExerciseButton;
    private JButton removeExerciseButton;
    private JButton saveButton;
    private JButton loadButton;
    private static final String JSON_STORE = "./data/workout.json";
    private Workout workout;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this, popupGUI
    // EFFECTS: constructs a menu for user to interact with
    public WorkoutGUI() {
        super("Main Menu");
        setSize(WIDTH, HEIGHT);
        setResizable(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        workout = new Workout("My Workout");
        addMenu();
        popupGUI = new PopupGUI();
        popupGUI.setWorkoutGUI(this);
        popupGUI.setWorkout(this.getWorkout());
        popupGUI.makeTable();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        this.revalidate();
        this.repaint();
        this.pack();

    }

    // MODIFIES: this, menu
    // EFFECTS: creates the application's main menu and adds it to the window
    public void addMenu() {
        menu = new JPanel();
        menu.setPreferredSize(new Dimension(800, 800));
        menu.setBackground(new Color(181, 233, 255));
        menu.setLayout(new GridLayout(5, 1, 10, 30));
        menu.setVisible(true);
        this.add(menu);
        borderColour = new Color(153, 182, 255);
        makeLengthBorders();
        makeWidthBorders();
        addButtons();
        addButtonActions();
    }

    // MODIFIES: menu,
    // EFFECTS: adds buttons to the main menu
    public void addButtons() {
        makeButton(viewWorkoutButton = new JButton("View Your Workout"));
        makeButton(addExerciseButton = new JButton("Add Exercise"));
        makeButton(removeExerciseButton = new JButton("Remove Exercise"));
        makeButton(saveButton = new JButton("Save"));
        makeButton(loadButton = new JButton("Load"));
        menu.revalidate();
        menu.repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds action commands to each buttons
    public void addButtonActions() {
        viewWorkoutButton.addActionListener(this);
        viewWorkoutButton.setActionCommand("view");
        addExerciseButton.addActionListener(this);
        addExerciseButton.setActionCommand("add");
        removeExerciseButton.addActionListener(this);
        removeExerciseButton.setActionCommand("remove");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        loadButton.addActionListener(this);
        loadButton.setActionCommand("load");

    }

    // EFFECTS: assigns an action to each action command
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if (command.equals("save")) {
            saveWorkout();
        } else if (command.equals("load")) {
            loadWorkout();
        } else if (command.equals("add")) {
            popupGUI.addExercisePopup();
        } else if (command.equals("remove")) {
            popupGUI.removeExercisePopUp();
        } else if (command.equals("view")) {
            popupGUI.makeTable();
            popupGUI.displayExercises();
            viewWorkout();
        }
    }

    // MODIFIES: this, borderR, borderL
    // EFFECTS: makes left and right borders for main frame
    public void makeWidthBorders() {
        borderR = new JPanel();
        borderL = new JPanel();

        borderR.setBackground(borderColour);
        borderL.setBackground(borderColour);

        borderR.setPreferredSize(new Dimension(100, HEIGHT));
        borderL.setPreferredSize(new Dimension(100, HEIGHT));

        borderR.setVisible(true);
        borderL.setVisible(true);

        this.add(borderR, BorderLayout.EAST);
        this.add(borderL, BorderLayout.WEST);

    }

    // MODIFIES: this, borderT, borderB
    // EFFECTS: makes top and bottom borders main frame
    public void makeLengthBorders() {
        borderT = new JPanel();
        borderB = new JPanel();

        borderT.setBackground(borderColour);
        borderB.setBackground(borderColour);


        borderT.setPreferredSize(new Dimension(WIDTH, 100));
        borderB.setPreferredSize(new Dimension(WIDTH, 100));


        borderT.setVisible(true);
        borderB.setVisible(true);

        this.add(borderT, BorderLayout.NORTH);
        this.add(borderB, BorderLayout.SOUTH);

    }

    // MODIFIES: menu
    // EFFECTS: constructs a button and adds it to the main menu panel
    public void makeButton(JButton button) {
        button.setVisible(true);
        menu.add(button);

    }


    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the workout to file
    private void saveWorkout() {
        try {
            jsonWriter.open();
            jsonWriter.writeWorkout(workout);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads workout from file
    private void loadWorkout() {
        try {
            workout = jsonReader.readWorkout();
            popupGUI.setWorkout(workout);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: returns workout
    public Workout getWorkout() {
        return workout;
    }

    // EFFECTS: displays table of all exercises in the workout
    public void viewWorkout() {
        if (workout.getExercises().isEmpty()) {
            System.out.println("This workout has no exercises. Try adding some and check again.");
        } else {
            for (Exercise e : workout.getExercises()) {
                String numSets = String.valueOf(e.getSets());
                String numReps = String.valueOf(e.getReps());
                System.out.println("- " + e.getName() + " -> " + numSets + " sets of " + numReps
                        + " reps each");
            }
        }
    }

}
