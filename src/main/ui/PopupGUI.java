package ui;

import exception.ExerciseAlreadyAddedException;
import model.Event;
import model.EventLog;
import model.Exercise;
import model.Workout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;

// Represents window that pops up from WorkoutGUI
public class PopupGUI extends JFrame implements ActionListener {
    private WorkoutGUI gui;
    private JTable exerciseTable;
    private DefaultTableModel tableModel;
    private JPanel panel;
    private JTextField nameText;
    private JLabel nameLabel;
    private JTextField setsText;
    private JLabel setsLabel;
    private JTextField repsText;
    private JLabel repsLabel;
    private String givenName;
    private String givenSets;
    private String givenReps;
    private JLabel complete;
    private ImageIcon image;
    private int labelX = 150;
    private int labelY = 40;
    private int labelW = 250;
    private int labelH = 25;
    private int textX = 150;
    private int textY = 60;
    private int textW = 200;
    private int textH = 30;
    private Workout workout;


    // MODIFIES: this
    // EFFECTS: constructs the pop up window
    public PopupGUI() {
        super();
        setSize(500, 200);
        setResizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(false);
        panel = new JPanel();
        panel.setVisible(true);
        panel.setLayout(null);
        add(panel);
        complete = new JLabel();
        complete.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets this gui to given gui
    public void setWorkoutGUI(WorkoutGUI gui) {
        this.gui = gui;
    }

    // MODIFIES: this
    // EFFECTS: sets this workout to given workout
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    // MODIFIES: this
    // EFFECTS: If workout contains no exercises, tells the user to add exercises to it.
    //          Otherwise, displays a table containing details of all exercises in workout
    public void displayExercises() {
        if (workout.getExercises().size() == 0) {
            feedback("Try adding an exercise first.");
        } else {
            getContentPane().removeAll();
            repaint();
            add(new JScrollPane(exerciseTable));
            repaint();
            revalidate();
            setVisible(true);
        }
    }

    // Code for this method partly inspired by following thread:
    // https://stackoverflow.com/questions/22371720/how-to-add-row-dynamically-in-jtable
    // MODIFIES: this, tableModel
    // EFFECTS: constructs a table to represent all exercises in workout
    public void makeTable() {
        tableModel = new DefaultTableModel();
        exerciseTable = new JTable(tableModel);
        tableModel.addColumn("Exercise");
        tableModel.addColumn("Sets");
        tableModel.addColumn("Reps");

        for (Exercise e : workout.getExercises()) {
            String name = e.getName();
            int sets = e.getSets();
            int reps = e.getReps();
            tableModel.addRow(new Object[]{name, sets, reps});
        }

    }

    // MODIFIES: this
    // EFFECTS: opens this popup to begin the process of adding a new exercise
    public void addExercisePopup() {
        clearFrame();
        panel.revalidate();
        panel.repaint();
        userDeclaresName();

    }

    // MODIFIES: tableModel, workout
    // EFFECTS: adds exercise to workout, including its table of exercises
    public void addExercise(String name, String sets, String reps) {
        Exercise e = new Exercise(name, Integer.parseInt(sets), Integer.parseInt(reps));

        boolean shouldAdd = true;
        for (Exercise exercise : workout.getExercises()) {
            if (exercise.getName().equals(e.getName())) {
                shouldAdd = false;
            }
        }
        if (shouldAdd) {
            tableModel.addRow(new Object[]{name, sets, reps});
            workout.addExercise(e);
        }
    }

    // MODIFIES: panel, nameLabel, this, nameText
    // EFFECTS: builds s JLabel and JTextField asking user name of new exercise
    public void userDeclaresName() {
        clearFrame();
        panel.add(nameLabel = new JLabel("Name your exercise!"));
        nameLabel.setBounds(labelX, labelY, labelW, labelH);

        nameText = new JTextField();
        nameText.addActionListener(this);
        nameText.setActionCommand("add name");
        nameText.setBounds(textX, textY, textW, textH);
        panel.add(nameText);


    }

    // MODIFIES: this, panel, setsLabel, setsText
    // EFFECTS: builds s JLabel and JTextField asking user how many sets for new exercise
    public void userDeclaresSets() {
        clearFrame();
        panel.add(setsLabel = new JLabel("What number of sets will you do?"));
        setsLabel.setBounds(labelX, labelY, labelW, labelH);

        setsText = new JTextField();
        setsText.addActionListener(this);
        setsText.setActionCommand("add sets");
        setsText.setBounds(textX, textY, textW, textH);
        panel.add(setsText);

        panel.revalidate();
        panel.repaint();

    }

    // MODIFIES: this, panel, repsLabel, repsText
    // EFFECTS: builds s JLabel and JTextField asking user how many reps for new exercise
    public void userDeclaresReps() {
        clearFrame();
        panel.add(repsLabel = new JLabel("What number of reps will you do?"));
        repsLabel.setBounds(labelX, labelY, labelW, labelH);

        repsText = new JTextField();
        repsText.addActionListener(this);
        repsText.setActionCommand("add reps");
        repsText.setBounds(textX, textY, textW, textH);
        panel.add(repsText);

        panel.revalidate();
        panel.repaint();

    }

    // MODIFIES: this, panel, nameLabel, nameText
    // EFFECTS: if there is an exercise in the workout, opens a popup asking for the name
    //          of the exercise the user wants to remove.
    //          if there are no exercises, opens a popup stating there are no exercises to remove.
    public void removeExercisePopUp() {
        clearFrame();
        if (workout.getExercises().isEmpty()) {
            feedback("There's no exercises to remove");
        } else {
            panel.add(nameLabel = new JLabel("Name the exercise!"));
            nameLabel.setBounds(labelX, labelY, labelW, labelH);

            nameText = new JTextField();
            nameText.addActionListener(this);
            nameText.setActionCommand("remove name");
            nameText.setBounds(textX, textY, textW, textH);
            panel.add(nameText);
        }
    }

    // MODIFIES: tableModel, workout
    // EFFECTS: removes exercise from both the table and list of exercises
    public void removeExercise(String name) {
        int rowToRemove;
        for (Exercise e : workout.getExercises()) {
            if (e.getName().equals(name)) {
                rowToRemove = workout.getExercises().indexOf(e);
                tableModel.removeRow(rowToRemove);
            }
        }
        workout.removeExercise(name);

    }

    // MODIFIES: this, panel, workout
    // EFFECTS: adds actions to action commands
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("add name")) {
            givenName = nameText.getText().toLowerCase(Locale.ROOT);
            panel.remove(nameLabel);
            panel.remove(nameText);
            checkWhatToDoWhenGivenName();
        } else if (command.equals("add sets")) {
            givenSets = setsText.getText();
            panel.remove(setsLabel);
            panel.remove(setsText);
            checkWhatToDoWhenGivenSets();
        } else if (command.equals("add reps")) {
            givenReps = repsText.getText();
            panel.remove(repsLabel);
            panel.remove(repsText);
            checkWhatToDoWhenGivenReps();
        } else if (command.equals("remove name")) {
            givenName = nameText.getText();
            removeExercise(givenName);
            removeExerciseVisual();
        }
    }

    // EFFECTS: If sets were given as a number, continues the process of adding an exercise.
    //          Otherwise, tells user to try again
    public void checkWhatToDoWhenGivenSets() {
        try {
            Integer.parseInt(givenSets);
            userDeclaresReps();
        } catch (NumberFormatException numberFormatException) {
            feedback("Try again with a number");
        }
    }

    // EFFECTS: If reps were given as a number, continues the process of adding an exercise.
    //          Otherwise, tells user to try again
    public void checkWhatToDoWhenGivenReps() {
        try {
            Integer.parseInt(givenReps);
            addExerciseVisual();
        } catch (NumberFormatException numberFormatException) {
            feedback("Try again with a number");
        }
    }

    public void checkWhatToDoWhenGivenName() {
        try {
            checkExerciseName(givenName);
            userDeclaresSets();
        } catch (ExerciseAlreadyAddedException exerciseAlreadyAddedException) {
            feedback("Try again with a unique name");
        }
    }

    public void checkExerciseName(String name) throws ExerciseAlreadyAddedException {
        for (Exercise e : workout.getExercises()) {
            if (e.getName().equals(name)) {
                throw new ExerciseAlreadyAddedException("Exercise with this name has already been added");
            }
        }
    }

    // MODIFIES: this, image, complete, panel, workout
    // EFFECTS: adds the given exercise to the workout and gives the user confirmation that is has been added
    public void addExerciseVisual() {
        addExercise(givenName, givenSets, givenReps);
        clearFrame();

        complete = new JLabel("Added!");
        image = new ImageIcon("./images/added.png");
        complete.setBounds(labelX, -1, 200, 200);
        complete.setVisible(true);
        complete.setIcon(image);
        complete.setVerticalTextPosition(JLabel.TOP);
        complete.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(complete);
        revalidate();
        repaint();

    }

    // MODIFIES: this, complete, image, panel
    // EFFECTS: gives confirmation to user that exercise has been removed.
    public void removeExerciseVisual() {
        clearFrame();

        complete = new JLabel("Removed!");
        image = new ImageIcon("./images/removed.png");
        complete.setBounds(labelX, -1, 200, 200);
        complete.setVisible(true);
        complete.setIcon(image);
        complete.setVerticalTextPosition(JLabel.TOP);
        complete.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(complete);
        revalidate();
        repaint();
    }

    // MODIFIES: this, panel
    // EFFECTS: gives user textual feedback on an action they complete
    public void feedback(String s) {
        JLabel label = new JLabel(s);
        label.setVisible(true);
        clearFrame();
        label.setBounds(labelX, labelY, labelW, labelH);
        panel.add(label);
        revalidate();
        repaint();
    }


    // MODIFIES: this, panel
    // EFFECTS: clears all components on this frame and panel
    public void clearFrame() {
        panel.removeAll();
        getContentPane().removeAll();
        repaint();
        revalidate();
        add(panel);
        setVisible(true);
    }
}
