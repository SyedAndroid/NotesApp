package syed.noteapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shoiab on 2017-10-21.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NoteViewHolder> {
    ArrayList<Note> notes;
    Context context;
    Note note;
    private NotesClickListener notesClickListener;

    public RVAdapter(Context context, ArrayList<Note> notes, NotesClickListener notesClickListener) {
        this.notes = notes;
        this.context = context;
        this.notesClickListener = notesClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, final int position) {
        note = notes.get(position);

        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public interface NotesClickListener {
        void notesClickListener(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        TextView title;
        TextView description;
        CardView cardView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_note);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            notesClickListener.notesClickListener(notes.get(pos));
        }
    }


}
