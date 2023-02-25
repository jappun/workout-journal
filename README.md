# Workout Journal

## A digital record for your workouts

There are so many places where workouts are available online (blogs, Youtube channels, etc.)
that I have trouble finding the ones I've enjoyed in this past. After spending
15 minutes just looking for a workout I did a month ago, I've lost
a good chunk of the time I had blocked out to work out.
With this application, I can easily jot down the details of any workout
I liked instead, so they are all readily available to me when I want to work out.

This application will serve as a way for users to keep track of their favourite workouts.
This way, they spend less time searching for workouts they've done in the past
and spend more time actually working out.

Regardless of where they are in their fitness journey, *anyone* can use this app!
- Those already into fitness can
  - keep track of their many workouts
  - come up with new workouts
- Those new to fitness
  - add workouts that they find online
  - build their fitness journey one workout at a time
  

## User Stories*


- As a user, I want to be able to add a new workouts to my list of workouts.
- As a user, I want to be able to select a workout and view the details (name, sets, reps) of all the exercises in it.
- As a user, I want to be able to add a new exercise to my workout.
- As a user, I want to be able to remove an exercise from my workout.

- As a user, I want to be able to save the state of the application to file.
- As a user, I want to be able to load the state of the application from file.



*NOTE: Currently, the GUI does not support having a list of workouts. Instead, it consists of one workout only. 
Future versions of this application will aim to support having multiple workouts.

## Phase 4: Task 2
Added lunges to My Workout

Added crunches to My Workout

Added push ups to My Workout

Removed push ups to My Workout

## Phase 4: Task 3
To reduce coupling, I would refactor so that PopupGUI is no longer associated with Workout. 
I could do this by copying methods in PopupGUI that related to Workout over into WorkoutGUI and replacing PopupGUI's 
method bodies with calls to their corresponding WorkoutGUI method. 
The other refactoring I would do is reduce instances of duplicate code. RemoveExerciseVisual() and AddExerciseVisual() in PopupGUI
are exactly the same, except for that their locally instantiated ImageIcon and JLabel are different. I could have a third method called Visual()
that takes an ImageIcon and JLabel as its arguments instead and have the method bodies of RemoveExerciseVisual() and AddExerciseVisual() make a call
to Visual() instead. In JournalApp, viewWorkout(), removeWorkout() and addWorkout() share lines of code that could be extracted into their own methods.
I think some parts of this program also have low readability. In particular, the else block of removeExercisePopUp() in PopupGUI is hard to follow.
It would be best to extract it as its own method called something like askUserWhichExerciseToRemove(). This descriptive method name
makes it much more clear and easy to read.