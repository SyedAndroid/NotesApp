package syed.noteapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import syed.noteapp.Note;

/**
 * Created by shoiab on 2017-10-21.
 */

public class JSONUtil {

    public static ArrayList<Note> parseJSONString(String JSONString) {

        ArrayList<Note> notes = new ArrayList<>();
        try {
            JSONArray resultArray = new JSONArray(JSONString);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject noteJSON = resultArray.getJSONObject(i);

                int id = noteJSON.getInt("id");
                String title = noteJSON.getString("title");
                String desc = noteJSON.getString("description");

                notes.add(new Note(id, title, desc));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notes;
    }
}
