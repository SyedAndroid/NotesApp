package syed.noteapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import syed.noteapp.utils.NetworkUtil;

public class UpdateActivity extends AppCompatActivity {
    EditText titleEditText;
    EditText descEdit;
    String title;
    String desc;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        titleEditText = findViewById(R.id.title_update);
        descEdit = findViewById(R.id.desc_update);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("description");
        setTitle("Update");
        titleEditText.setText(title);
        descEdit.setText(desc);

    }

    public void updateNote(View view) {
        title = titleEditText.getText().toString();
        desc = descEdit.getText().toString();
        if (title.equals("")) {
            Toast.makeText(this, "Please fill the title", Toast.LENGTH_SHORT).show();
        } else {


            new UpdatePostRequest().execute(title, desc, "/" + id);


        }
    }

    public void deleteNote(View view) {
        new DeletePostRequest().execute("/" + id);
        finish();
    }

    private class UpdatePostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url = NetworkUtil.createUrl(strings[2]);
            String response = "";
            try {
                response = NetworkUtil.sendNewNoteDatatoServer(url, strings[0], strings[1], "PUT");
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

    private class DeletePostRequest extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            Integer ResponseCode = 0;
            URL url = NetworkUtil.createUrl(strings[0]);
            try {
                ResponseCode = NetworkUtil.deleteDatatoServer(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseCode;
        }
    }
}
