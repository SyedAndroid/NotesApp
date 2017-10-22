package syed.noteapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import syed.noteapp.utils.JSONUtil;
import syed.noteapp.utils.NetworkUtil;

/**
 * Created by shoiab on 2017-10-21.
 */

public class NotesLoader extends AsyncTaskLoader<ArrayList<Note>> {
    public NotesLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Note> loadInBackground() {
        URL url = NetworkUtil.createUrl("");
        ArrayList<Note> notes = new ArrayList<>();

        try {
            String response = NetworkUtil.getResponseFromHttpUrl(url);
            notes = JSONUtil.parseJSONString(response);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return notes;
    }
}
