package persistence;

import org.json.JSONObject;

// Code taken from Writeable interface in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
