package syed.noteapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import syed.noteapp.utils.NetworkUtil;

public class NewNoteActivity extends AppCompatActivity {
    String newTitle = "";
    String newDesc = "";
    EditText title;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        title = findViewById(R.id.title_create);
        description = findViewById(R.id.desc_create);
        setTitle("Add a note");


    }

    public void saveNote(View view) {
        newTitle = title.getText().toString();
        newDesc = description.getText().toString();
        if (newTitle.equals("")) {
            Toast.makeText(this, "Please fill the title", Toast.LENGTH_SHORT).show();
        } else {


            new SendPostRequest().execute(newTitle, newDesc);


        }

    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url = NetworkUtil.createUrl("");
            String response = "";
            try {
                response = NetworkUtil.sendNewNoteDatatoServer(url, strings[0], strings[1], "POST");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
