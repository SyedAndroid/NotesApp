package syed.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Note>>, RVAdapter.NotesClickListener {
    RecyclerView recyclerView;
    ArrayList<Note> notes;
    RVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycle_view_source);
        notes = new ArrayList<>();
        LoaderManager loaderManager = getSupportLoaderManager();

        loaderManager.initLoader(0, null, this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public Loader<ArrayList<Note>> onCreateLoader(int id, Bundle args) {
        return new NotesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Note>> loader, ArrayList<Note> data) {

        rvAdapter = new RVAdapter(this, data, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(rvAdapter);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Note>> loader) {

    }

    @Override
    public void notesClickListener(Note note) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("description", note.getDescription());
        startActivity(intent);
    }
}
